package cn.enncy.funny.utils;


import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Random;

/**
 * //TODO
 * <br/>Created in 23:15 2021/8/18
 *
 * @author: enncy
 */

@Component
@Slf4j
public class MailUtils {

    @Value(value = "${spring.mail.username}")
    private String mailSender;


    private static final String nickName = "趣博客";

    @Autowired
    private JavaMailSender javaMailSender;

    public static String createVerifyCode(int length){
        StringBuilder code = new StringBuilder();
        while ( code.length() < length ) {
            code.append(new Random().nextInt(10));
        }
        return code.toString();
    }


    public void send(String receiver, String title, String content) throws EmailException {
        VelocityContext context = new VelocityContext();
        context.put("content", content);
        String render = TemplateEngine.render(context, "templates/mail/index.html");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(nickName + "<" + mailSender + ">");
            helper.setTo(receiver);
            helper.setSubject(nickName + " —— " + title);
            helper.setText(render, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("邮件发送失败,请重新尝试");
        }
    }


    /**
     * 发送注册邮件
     */
    public void sendRegisterEmail(User user, String verifiedUrl) throws EmailException {
        VelocityContext context = new VelocityContext();
        context.put("name", user.getAccount());
        context.put("url", verifiedUrl);
        String render = TemplateEngine.render(context, "templates/mail/register.html");

        this.send(user.getEmail(),"账号注册" ,render);
    }


    public void sendEmailVerified(String receiver, String userName, String verifyCode) throws EmailException {
        VelocityContext context = new VelocityContext();
        context.put("name", userName);
        context.put("code", verifyCode);
        String render = TemplateEngine.render(context, "templates/mail/verified.html");

        this.send(receiver,"邮箱验证", render);
    }
}

