package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.OpenApi;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 15:28 2021/12/19
 *
 * @author enncy
 */

@RestController
public class CommonController {

    HttpServletRequest request;

    public CommonController(HttpServletRequest request) {
        this.request = request;
    }

    @OpenApi
    @RequestMapping("/")
    public Object api() {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "welcome to enncy api");
        map.put("version", "v1");
        map.put("time", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
        map.put("author", "enncy");
        return map;
    }
}
