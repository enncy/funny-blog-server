package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.BlogOperation;
import cn.enncy.funny.exceptions.ServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户操作文章信息表 前端控制器
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@RestController
@RequestMapping("/operation/blog")
@Api(tags = "文章操作记录")
@ResponseHandler
public class BlogOperationController extends ServiceController<BlogOperation> {

    public BlogOperationController(IService<BlogOperation> service) {
        super(service);
    }

    @Override
    @Roles(Role.VISITOR)
    public BlogOperation selectById(@RequestParam("id") Long id) throws ServiceException {
        return super.selectById(id);
    }

    @Override
    @Roles(Role.VISITOR)
    public List<BlogOperation> selectAll() {
        return super.selectAll();
    }

    @Override
    @Roles(Role.VISITOR)
    public List<BlogOperation> selectPage(@RequestParam("curren") int current,@RequestParam("size")  int size) {
        return super.selectPage(current, size);
    }

    @Override
    @Roles(Role.USER)
    public boolean save(@RequestBody BlogOperation target) throws ServiceException {
        return super.save(target);
    }

    @Override
    @Roles(Role.USER)
    public boolean update(@RequestBody BaseDto<BlogOperation> dto) throws ServiceException {
        return super.update(dto);
    }

    @Override
    @Roles(Role.USER)
    public boolean removeById(@RequestParam("id") Long id) throws ServiceException {
        return super.removeById(id);
    }

    @Override
    @Roles(Role.VISITOR)
    public int count() {
        return super.count();
    }
}

