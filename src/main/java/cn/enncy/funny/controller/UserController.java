package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author enncy
 * @since 2021-08-16
 */
@RestController
@ResponseHandler
@RequestMapping(value = "/user" )
@Api(tags = "用户操作")
public class UserController extends ServiceController<User> {
    public UserController(IService<User> service) {
        super(service);
    }



}

