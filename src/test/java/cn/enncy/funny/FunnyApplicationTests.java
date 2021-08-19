package cn.enncy.funny;

import cn.enncy.funny.config.HttpErrorStateConverter;
import cn.enncy.funny.exceptions.EmailException;
import cn.enncy.funny.utils.MailUtils;
import cn.enncy.funny.utils.TemplateEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

@SpringBootTest
class FunnyApplicationTests {

    @Autowired
    private MailUtils mailUtil;

    //接收人
    private static final String receiver = "877526278@qq.com";

    /**
     * 发送文本邮件
     */
    @Test
    public void sendSimpleMail() throws EmailException {
        mailUtil.sendRegisterEmail(receiver,"言小溪222","https://www.zhihu.com/question/20556280");
        //mailUtil.sendEmailVerified(receiver,"言小溪222","123456");
        System.out.println("发送成功");
    }

}

class MyTest {

    @Test
    public void mail() throws IOException {


    }

}