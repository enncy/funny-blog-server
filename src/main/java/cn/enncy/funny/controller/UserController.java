package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.service.UserService;
import cn.enncy.funny.utils.EmailUtils;
import cn.enncy.funny.utils.SecurityUtils;
import cn.enncy.funny.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    private UserService userService;

    private final EmailUtils mailUtil;

    public UserController(IService<User> service, UserService userService, EmailUtils mailUtil) {
        super(service);
        this.userService = userService;
        this.mailUtil = mailUtil;
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
        return login(user, password);
    }

    @ApiOperation("账号登录")
    @GetMapping("/login/by/email")
    public User loginByEmail(@RequestParam("email") String email, @RequestParam("code") String code) throws ServiceException {
        User user = service.lambdaQuery().eq(User::getEmail, email).eq(User::getPassword, code).one();
        return login(user, code);
    }

    public User login(User user, String password) throws ServiceException {
        if (user == null || !user.getPassword().equals(password)) {
            throw new ServiceException("账号或密码错误！");
        }
        session.setAttribute("user", user);
        return user;
    }


    @Override
    @Roles(Role.USER)
    public boolean update(BaseDto<User> dto) throws ServiceException {
        checkUserRoles(dto.getId());
        super.update(dto);
        return true;
    }

    @Override
    @Roles(Role.USER)
    public boolean removeById(Long id) throws ServiceException {
        checkUserRoles(id);
        super.removeById(id);
        return false;
    }

    @GetMapping("/register/check")
    @ApiOperation("注册验证")
    public String registerCheck(@RequestParam("token") String md5, @RequestParam("info") String base64) throws Exception {
        String formatBase64 = base64.replaceAll(" ", "+");
        // 在传输的过程中 + 号会被转换成空格
        String decode = SecurityUtils.BASE64.decode(formatBase64);
        // 对解析的 AES 字符串编码进行转换，转成 byte 数组
        String[] split = decode.replace("[", "").replace("]", "").split(",");
        byte[] bytes = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            bytes[i] = Byte.parseByte(split[i].trim());
        }
        // 对 AES 进行解码
        User user = JSONObject.parseObject(SecurityUtils.AES.decrypt(bytes), User.class);
        User one = userService.lambdaQuery().eq(User::getAccount, user.getAccount()).one();
        if (one != null) {
            throw new ServiceException("账号已经存在");
        }
        service.save(user);
        return "注册成功";
    }

    @PostMapping("/register")
    @ApiOperation("发送注册验证邮箱")
    public String emailRegister(@RequestBody User user, @RequestParam("confirmPassword") String confirmPassword, HttpServletRequest request) throws Exception {
        if (confirmPassword.equals(user.getPassword())) {

            String userInfo = JSONObject.toJSONString(user);

            // 对信息进行 AES 加密
            byte[] encrypt = SecurityUtils.AES.encrypt(userInfo.getBytes());
            String base64 = SecurityUtils.BASE64.encode(Arrays.toString(encrypt));
            long l = System.currentTimeMillis();
            //创建 qs map
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("info", base64);
            // MD5 加密
            String md5 = SecurityUtils.MD5.encryption(JSON.toJSONString(map), l);
            map.put("time", l);
            map.put("token", md5);
            // 发送MD5加密验证和 用户加密信息
            String path = StringUtils.getRequestBaseUrl(request) + "/user/register/check?" + StringUtils.createQueryString(map);
            mailUtil.sendRegisterEmail(user, path);
        } else {
            throw new ServiceException("2次密码不一致");
        }
        return "发送注册邮箱成功，请尽快前往您的邮箱验证";
    }

}

