package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.entity.BaseEntity;
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
    @Roles(value = {Role.ROOT})
    public T selectById(@RequestParam("id") Long id) throws ServiceException {
        checkExist(id);
        return service.getById(id);
    }

    @ApiOperation("获取全部信息")
    @GetMapping("/get/all")
    @Roles(value = {Role.ROOT})
    public List<T> selectAll(){
        return service.list();
    }

    @ApiOperation("根据数量查询")
    @GetMapping("/list")
    @Roles(value = {Role.ROOT})
    public List<T> list(@RequestParam("page") int page, @RequestParam("size") int size){
        return service.page(new Page<>(page,size)).getRecords();
    }

    @ApiOperation("保存信息")
    @PostMapping("/insert")
    @Roles(value = {Role.ROOT})
    public boolean save(@RequestBody T target) throws ServiceException {
        return service.save(target);
    }

    @ApiOperation("根据id更新")
    @PostMapping("/update")
    @Roles(value = {Role.ROOT})
    public boolean update(@RequestBody T target) throws ServiceException {
        return service.updateById(target);
    }

    @ApiOperation("根据id删除")
    @GetMapping("/delete")
    @Roles(value = {Role.ROOT})
    public boolean removeById(@RequestParam("id") Long id) throws ServiceException {
        checkExist(id);
        return service.removeById(id);
    }

    @ApiOperation("查询数量")
    @GetMapping("/count")
    @Roles(value = {Role.ROOT})
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
