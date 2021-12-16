package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.bean.EmailValidator;
import cn.enncy.funny.utils.EmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * //TODO
 * <br/>Created in 18:19 2021/8/19
 *
 * @author: enncy
 */

@RestController
@ResponseHandler
@RequestMapping(value = "/email")
@Api(tags = "邮件检验及发送")
public class EmailController {

    /**
     * 验证器属性名
     */
    public static final String VALIDATOR_NAME = "email.validator";

    /**
     * 已验证的验证码属性名
     */
    public static final String VERIFIED_CODE = "verified.code";

    /**
     * 默认的超时时间
     */
    public static final long DEFAULT_LIMIT = 3 * 60 * 1000;

    /**
     * 可重新发送的时间
     */
    public static final long RESEND_LIMIT = 60 * 1000;

    private final HttpSession session;

    private final EmailUtils mailUtil;

    public EmailController(HttpSession session, EmailUtils mailUtil) {
        this.session = session;
        this.mailUtil = mailUtil;
    }

    @GetMapping("/check")
    @ApiOperation("验证码验证")
    public boolean check(@RequestParam("code") String code) throws ServiceException {

        EmailValidator validator = (EmailValidator) session.getAttribute(VALIDATOR_NAME);

        Object verifiedCode = session.getAttribute(VERIFIED_CODE);
        System.out.println(verifiedCode);
        // 如果验证过的码存，并且和当前的码一样，则报错
        if (verifiedCode != null && String.valueOf(verifiedCode).equals(code)) {
            throw new ServiceException("此验证码已经被使用过了！");
        }

        if (validator == null) {
            throw new ServiceException("未发送验证码！请先发送验证码再验证");
        }
        if (validator.isOutOfTime(DEFAULT_LIMIT)) {
            throw new ServiceException("验证码超时！请重新发送验证码！");
        }
        if (validator.getCode().equals(code)) {
            // 添加已经验证的验证码，同时把验证器置空，防止重复验证
            session.setAttribute(VERIFIED_CODE, code);
            session.setAttribute(VALIDATOR_NAME, null);
            return true;
        } else {
            throw new ServiceException("验证码错误！");
        }

    }


    @GetMapping("/send/verify")
    @ApiOperation("发送验证码")
    public String sendVerifyCode(@RequestParam("email") String email) throws ServiceException {
        // 是否存在验证码
        if (session.getAttribute(VALIDATOR_NAME) != null) {
            EmailValidator validator = (EmailValidator) session.getAttribute(VALIDATOR_NAME);
            // 如果没有超过时间，则不发送新的验证码
            if (!validator.isOutOfTime(RESEND_LIMIT)) {
                throw new ServiceException("验证码已经发送，请在" + RESEND_LIMIT / (60 * 1000) + "分钟后重新发送!");
            }
        }

        EmailValidator validator = new EmailValidator();
        String verifyCode = EmailUtils.createVerifyCode(6);
        validator.setCode(verifyCode);

        session.setAttribute(VALIDATOR_NAME, validator);
        mailUtil.sendEmailVerified(email, email, verifyCode);
        return "验证码发送成功,请在 " + (EmailValidator.DEFAULT_LIMIT_TIME / (60 * 1000)) + " 分钟内完成邮箱验证!";

    }


}
