package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.UserInfo;
import cn.enncy.funny.exceptions.ServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息详情表 前端控制器
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@RestController
@RequestMapping("/info/user")
@ResponseHandler
@Api(tags = "用户信息")
public class UserInfoController extends ServiceController<UserInfo> {

    public UserInfoController(IService<UserInfo> service) {
        super(service);
    }

    @Override
    @Roles(Role.VISITOR)
    public UserInfo selectById(@RequestParam("id") Long id) throws ServiceException {
        return super.selectById(id);
    }

    @Override
    @Roles(Role.VISITOR)
    public List<UserInfo> selectAll() {
        return super.selectAll();
    }

    @Override
    @Roles(Role.VISITOR)
    public List<UserInfo> list(@RequestParam("current") int current, @RequestParam("size") int size) {
        return super.list(current, size);
    }

    @Override
    @Roles(Role.USER)
    public boolean save(@RequestBody UserInfo target) throws ServiceException {
        return super.save(target);
    }

    @Override
    @Roles(Role.USER)
    public boolean update(@RequestBody UserInfo target) throws ServiceException {
        return super.update(target);
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

