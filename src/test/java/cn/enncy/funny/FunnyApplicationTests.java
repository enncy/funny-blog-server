package cn.enncy.funny;

import cn.enncy.funny.exceptions.EmailException;
import cn.enncy.funny.utils.EmailUtils;
import cn.enncy.funny.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class FunnyApplicationTests {

    @Autowired
    private EmailUtils mailUtil;

    //接收人
    private static final String receiver = "877526278@qq.com";

    /**
     * 发送文本邮件
     */
    @Test
    public void sendSimpleMail() throws EmailException {

    }

    @Test
    public void testUrl() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("www.baidu.com");
        System.out.println(ia2.getHostAddress());
    }

}

@Slf4j
class MyTest {
    private final String BASE_PACKAGE = "cn.enncy.funny.entity";
    private final String RESOURCE_PATTERN = "/**/*.class";


    @Test
    public void main() throws Exception {
        byte[] encrypt = SecurityUtils.AES.encrypt("123456".getBytes(StandardCharsets.UTF_8));
        byte[] decrypt = SecurityUtils.AES.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(new String(encrypt).getBytes());
        System.out.println(new String(decrypt).getBytes());

    }

    @Test
    public void tes() throws Exception {

    }

    @Test
    public void testUrl() throws UnknownHostException {
        InetAddress ia2 = InetAddress.getByName("aslfhwlaflaw.com");
        System.out.println(ia2.getHostAddress());
    }

}
