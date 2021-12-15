package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.Blog;
import cn.enncy.funny.exceptions.ServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@RestController
@RequestMapping("/blog")
@Api(tags = "文章信息")
@ResponseHandler
public class BlogController extends ServiceController<Blog> {

    public BlogController(IService<Blog> service) {
        super(service);
    }

    @Override
    @Roles(Role.VISITOR)
    public Blog selectById(@RequestParam("id") Long id) throws ServiceException {
        return super.selectById(id);
    }

    @Override
    @Roles(Role.VISITOR)
    public List<Blog> selectAll() {
        return super.selectAll();
    }

    @Override
    @Roles(Role.VISITOR)
    public List<Blog> selectPage(@RequestParam("current") int current, @RequestParam("size") int size) {
        return super.selectPage(current, size);
    }

    @Override
    @Roles(Role.USER)
    public boolean save(@RequestBody Blog target) throws ServiceException {
        checkUserRoles(target.getUserId());
        return super.save(target);
    }

    @Override
    @Roles(Role.USER)
    public boolean update(@RequestBody BaseDto<Blog> dto) throws ServiceException {
        checkUserRoles(dto.getId());
        return super.update(dto);
    }

    @Override
    @Roles(Role.ROOT)
    public boolean removeById(@RequestParam("id") Long id) throws ServiceException {
        return super.removeById(id);
    }

    @ApiOperation("删除")
    @GetMapping("/remove/by")
    @Roles(Role.USER)
    public boolean removeByUserId(@RequestParam("userId") Long userId) throws ServiceException {
        checkUserRoles(userId);
        return service.lambdaUpdate().eq(Blog::getUserId, userId).remove();
    }

    @Override
    @Roles(Role.VISITOR)
    public int count() {
        return super.count();
    }
}

