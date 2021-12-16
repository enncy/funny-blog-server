package cn.enncy.funny;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("cn.enncy.funny.mapper")
public class FunnyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunnyApplication.class, args);

    }

}
