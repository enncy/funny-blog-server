package cn.enncy.funny.controller.service;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.service.UserService;
import cn.enncy.funny.utils.MailUtils;
import cn.enncy.funny.utils.SecurityUtils;
import cn.enncy.funny.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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

    private final MailUtils mailUtil;

    public UserController(IService<User> service, UserService userService, MailUtils mailUtil) {
        super(service);
        this.userService = userService;
        this.mailUtil = mailUtil;
    }

    @ApiOperation("邮箱检验")
    @GetMapping("/check/email")
    public String checkEmail(@RequestParam("email") String email) throws ServiceException {
        if(service.lambdaQuery().eq(User::getEmail, email).one()!=null){
            throw new ServiceException("邮箱已经被占用");
        }
        return "邮箱可用";
    }

    @ApiOperation("邮箱检验")
    @GetMapping("/check/account")
    public String checkAccount(@RequestParam("account") String account) throws ServiceException {
        if(service.lambdaQuery().eq(User::getAccount, account).one()!=null){
            throw new ServiceException("账号已经被占用");
        }
        return "账号可用";
    }


    @ApiOperation("账号登录")
    @GetMapping("/login")
    public boolean login(@RequestParam("account")String account,@RequestParam("password") String password ,HttpServletRequest request) throws ServiceException {
        User user = service.lambdaQuery().eq(User::getAccount,account).one();
        if(user==null || !user.getPassword().equals(password)){
            throw new ServiceException("账号或密码错误！");
        }
        request.getSession().setAttribute("user", user);
        return true;
    }


    @GetMapping("/register/check")
    @ApiOperation("注册验证")
    public String registerCheck(@RequestParam("token") String md5, @RequestParam("info") String base64) throws Exception {
        String formatBase64 = base64.replaceAll(" ", "+");
        // 在传输的过程中 + 号会被转换成空格
        String encryption = SecurityUtils.MD5.encryption(formatBase64);
        // 如果加密后和 token 相同，说明参数正确
        if (encryption.equals(md5)) {
            String decode = SecurityUtils.BASE64.decode(formatBase64);
            // 对解析的 AES 字符串编码进行转换，转成 byte 数组
            String[] split = decode.replace("[", "").replace("]", "").split(",");
            byte[] bytes = new byte[split.length];
            for (int i = 0; i < split.length; i++) {
                bytes[i] = Byte.parseByte(split[i].trim());
            }
            // 对 AES 进行解码
            User user =  JSONObject.parseObject(SecurityUtils.AES.decrypt(bytes),User.class);
            service.save(user);
            return "注册成功";
        } else {

            throw new ServiceException("注册失败，参数不正确！");
        }

    }

    @PostMapping("/register")
    @ApiOperation("发送注册验证邮箱")
    public boolean emailRegister(@RequestBody User user, HttpServletRequest request) throws Exception {
        String userInfo = JSONObject.toJSONString(user);
        System.out.println(userInfo);
        // 对信息进行 AES 加密
        byte[] encrypt = SecurityUtils.AES.encrypt(userInfo.getBytes());
        String base64 = SecurityUtils.BASE64.encode(Arrays.toString(encrypt));
        // MD5 加密
        String md5 = SecurityUtils.MD5.encryption(base64);
        // 发送MD5加密验证和 用户加密信息
        String path = StringUtils.getRquestBaseUrl(request) + "/user/register/check?token=" + md5 + "&info=" + base64;
        mailUtil.sendRegisterEmail(user, path);
        return true;
    }

}

