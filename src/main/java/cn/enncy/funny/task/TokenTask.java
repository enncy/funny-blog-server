package cn.enncy.funny.task;


import cn.enncy.funny.aspect.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 23:10 2021/12/16
 *
 * @author enncy
 */

@Component
@Slf4j
public class TokenTask {

    public static final List<String> TOKENS = new ArrayList<>();

    @Scheduled(cron = "0 */1 * * * ?")
    public void deleteTokenTask(){
        log.debug("[TASK : deleteTokenTask]  tokens size : "+ TokenTask.TOKENS.size());
        TokenTask.TOKENS.clear();
    }

}
