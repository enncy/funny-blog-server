package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.dto.UserRegisterDto;
import cn.enncy.funny.dto.UserResetPasswordDto;
import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.service.UserService;
import cn.enncy.funny.utils.EmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@RequestMapping(value = "/user")
@Api(tags = "用户操作")
public class UserController extends ServiceController<User> {

    private final UserService userService;
    private final HttpSession session;
    private final HttpServletResponse response;
    private final EmailUtils mailUtil;
    private final EmailController emailController;

    public UserController(UserService userService, HttpSession session, HttpServletResponse response, EmailUtils mailUtil, EmailController emailController) {
        super(userService);
        this.userService = userService;
        this.session = session;
        this.response = response;
        this.mailUtil = mailUtil;
        this.emailController = emailController;
    }

    @ApiOperation("状态检测")
    @GetMapping("/check/status")
    public User checkStatus() throws ServiceException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ServiceException("未登录！");
        } else {
            return user;
        }
    }

    @ApiOperation("邮箱检验")
    @GetMapping("/check/email")
    public String checkEmail(@RequestParam("email") String email) throws ServiceException {
        if (EmailUtils.hostNotFound(email)) {
            throw new ServiceException("邮箱格式错误或者邮箱不正确");
        } else if (service.lambdaQuery().eq(User::getEmail, email).one() != null) {
            throw new ServiceException("邮箱已经被占用");
        }
        return "邮箱可用";
    }

    @ApiOperation("账号检验")
    @GetMapping("/check/account")
    public String checkAccount(@RequestParam("account") String account) throws ServiceException {
        if (service.lambdaQuery().eq(User::getAccount, account).one() != null) {
            throw new ServiceException("账号已经被占用");
        }
        return "账号可用";
    }


    @ApiOperation("账号登录")
    @GetMapping("/login/by/account")
    public User loginByAccount(@RequestParam("account") String account, @RequestParam("password") String password) throws ServiceException {
        User user = service.lambdaQuery().eq(User::getAccount, account).one();
        if (user == null || !user.getPassword().equals(password)) {
            throw new ServiceException("账号或密码错误！");
        }
        session.setAttribute("user", user);
        return user;
    }

    @ApiOperation("邮箱登录")
    @GetMapping("/login/by/email")
    public User loginByEmail(@RequestParam("email") String email, @RequestParam("code") String code) throws ServiceException {
        User user = service.lambdaQuery().eq(User::getEmail, email).one();
        if (user == null) {
            throw new ServiceException("此邮箱未注册过！");
        }

        if (emailController.check(code)) {
            session.setAttribute("user", user);
            return user;
        } else {
            return null;
        }
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public boolean logout(){
        session.setAttribute("user",null);
        return true;
    }


    @Override
    @Roles(Role.USER)
    public boolean update(User user) throws ServiceException {

        super.update(user);
        return true;
    }

    @Override
    @Roles(Role.USER)
    public boolean removeById(Long id) throws ServiceException {

        super.removeById(id);
        return false;
    }


    @PostMapping("/register")
    @ApiOperation("注册")
    public String emailRegister(@RequestBody UserRegisterDto userRegisterDto) throws Exception {
        if (!userRegisterDto.getConfirmPassword().equals(userRegisterDto.getPassword())) {
            throw new ServiceException("2次密码不一致");
        }
        // 检验验证码
        emailController.check(userRegisterDto.getCode());

        User user = new User();
        user.setAccount(userRegisterDto.getAccount());
        user.setEmail(userRegisterDto.getEmail());
        user.setAccount(userRegisterDto.getPassword());
        user.setNickName("无昵称");
        user.setRole(Role.USER.value);
        service.save(user);
        session.setAttribute("user",user);
        return "注册成功";
    }

    @PostMapping("/reset/password")
    @ApiOperation("重置密码")
    public String resetPassword(@RequestBody UserResetPasswordDto resetPasswordDto) throws ServiceException {
        User user = userService.lambdaQuery().eq(User::getEmail, resetPasswordDto.getEmail()).one();

        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            return "2次密码不一致";
        }
        if (user == null) {
            return "此邮箱未注册过";
        }
        // 检验验证码
        emailController.check(resetPasswordDto.getCode());

        user.setPassword(resetPasswordDto.getPassword());
        userService.updateById(user);
        return "重置成功";
    }

}

