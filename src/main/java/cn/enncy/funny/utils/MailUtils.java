package cn.enncy.funny.utils;


import cn.enncy.funny.exceptions.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
    public void sendRegisterEmail(String receiver, String userName, String verifiedUrl) throws EmailException {
        VelocityContext context = new VelocityContext();
        context.put("name", userName);
        context.put("url", verifiedUrl);
        String render = TemplateEngine.render(context, "templates/mail/register.html");

        this.send(receiver,"账号注册" ,render);
    }


    public void sendEmailVerified(String receiver, String userName, String verifyCode) throws EmailException {
        VelocityContext context = new VelocityContext();
        context.put("name", userName);
        context.put("code", verifyCode);
        String render = TemplateEngine.render(context, "templates/mail/verified.html");

        this.send(receiver,"邮箱验证", render);
    }
}

