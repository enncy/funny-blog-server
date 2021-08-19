package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.exceptions.RequestException;
import cn.enncy.funny.entity.BaseEntity;
import cn.enncy.funny.dto.BaseDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    public ServiceController(IService<T> service) {
        this.service = service;
    }

    @ApiOperation("根据id查询实体")
    @GetMapping("/get/one")
    public T selectById(@RequestParam("id") int id){
        return service.getById(id);
    }

    @ApiOperation("获取全部信息")
    @GetMapping("/get/all")
    public List<T> selectAll(){
        return service.list();
    }

    @ApiOperation("根据数量查询")
    @GetMapping("/get/page")
    public List<T> selectPage(@RequestParam("current") int current, @RequestParam("size") int size){
        return service.page(new Page<>(current,size)).getRecords();
    }

    @ApiOperation("保存信息")
    @PostMapping("/insert")
    public boolean save(@RequestBody T target)  {
        return service.save(target);
    }

    @ApiOperation("根据id更新")
    @PostMapping("/update")
    public boolean updateById(@RequestBody  BaseDto<T> dto) throws Exception {
        T t = service.getById(dto.getId());
        if(t!=null){
            dto.target.setId(dto.getId());
            return service.updateById(dto.target);
        }else{
            throw new RequestException("此用户不存在!");
        }

    }

    @ApiOperation("根据id删除")
    @GetMapping("/delete")
    public boolean removeById(@RequestParam("id") int id){
        return service.removeById(id);
    }

    @ApiOperation("查询数量")
    @GetMapping("/count")
    public int count(){
        return service.count();
    }

}
