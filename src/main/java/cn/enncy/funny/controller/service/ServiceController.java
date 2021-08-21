package cn.enncy.funny.controller.service;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.exceptions.RequestException;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.pojo.BaseEntity;
import cn.enncy.funny.dto.BaseDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 20:34 2021/8/16
 *
 * @author: enncy
 */

@ResponseHandler
public class ServiceController<T extends BaseEntity<T>> {


    protected IService<T> service;

    public ServiceController(IService<T> service) {
        this.service = service;
    }


    @ApiOperation("根据id查询实体")
    @GetMapping("/get/one")
    @Roles(value = {Role.ROOT})
    public T selectById(@RequestParam("id") Long id) throws ServiceException {
        checkExist(id);
        return service.getById(id).filter();
    }

    @ApiOperation("获取全部信息")
    @GetMapping("/get/all")
    @Roles(value = {Role.ROOT})
    public List<T> selectAll(){
        return service.list().stream().map(T::filter).collect(Collectors.toList());
    }

    @ApiOperation("根据数量查询")
    @GetMapping("/get/page")
    @Roles(value = {Role.ROOT})
    public List<T> selectPage(@RequestParam("current") int current, @RequestParam("size") int size){
        return service.page(new Page<>(current,size)).getRecords().stream().map(T::filter).collect(Collectors.toList());
    }

    @ApiOperation("保存信息")
    @PostMapping("/insert")
    @Roles(value = {Role.ROOT})
    public boolean save(@RequestBody T target) throws ServiceException {
        return service.save(target);
    }

    @ApiOperation("根据id更新")
    @PostMapping("/update")
    @Roles(value = {Role.USER})
    public boolean updateById(@RequestBody  BaseDto<T> dto) throws Exception {
        checkExist(dto.getId());
        dto.target.setId(dto.getId());
        return service.updateById(dto.target);
    }

    @ApiOperation("根据id删除")
    @GetMapping("/delete")
    @Roles(value = {Role.USER})
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
