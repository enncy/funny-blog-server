package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.entity.BaseEntity;
import cn.enncy.funny.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 20:34 2021/8/16
 *
 * @author: enncy
 */

@ResponseHandler
public class ServiceController<T extends BaseEntity> {


    protected IService<T> service;

    @Autowired
    public HttpSession session;

    public ServiceController(IService<T> service) {
        this.service = service;
    }

    @ApiOperation("根据id查询实体")
    @GetMapping("/get/one")
    public T selectById(@RequestParam("id") Long id) throws ServiceException {
        checkExist(id);
        return service.getById(id);
    }

    @ApiOperation("根据条件搜索")
    @GetMapping("/search")
    public List<T>  search(@RequestParam(required = false) Map<String,Object> map){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            queryWrapper.like(StringUtils.humpToUnderline(entry.getKey()), entry.getValue()).or();
        }
        return service.getBaseMapper().selectList(queryWrapper);
    }

    @ApiOperation("根据数量查询")
    @GetMapping("/list")
    public IPage<T> list(@RequestParam(required = false) Map<String,Object> map){

        int page = Integer.parseInt(map.remove("page").toString());
        int size = Integer.parseInt(map.remove("size").toString());

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            queryWrapper.like(entry.getKey(), entry.getValue()).or();

        }

        return service.getBaseMapper().selectPage(new Page<>(page, size), queryWrapper);
    }

    @ApiOperation("保存信息")
    @PostMapping("/insert")
    public boolean save(@RequestBody T target) throws ServiceException {
        return service.save(target);
    }

    @ApiOperation("根据id更新")
    @PostMapping("/update")
    public boolean update(@RequestBody T target) throws ServiceException {
        return service.updateById(target);
    }

    @ApiOperation("根据id删除")
    @GetMapping("/delete")
    public boolean removeById(@RequestParam("id") Long id) throws ServiceException {
        checkExist(id);
        return service.removeById(id);
    }

    @ApiOperation("查询数量")
    @GetMapping("/count")
    public int count(){
        return service.count();
    }


    public T checkExist(Long id) throws ServiceException {
        T byId = service.getById(id);
        if(byId==null){
            throw new ServiceException("此id不存在");
        }
        return byId;
    }

}
