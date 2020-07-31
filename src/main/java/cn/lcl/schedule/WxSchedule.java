package cn.lcl.schedule;

import cn.lcl.service.WxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@Slf4j
public class WxSchedule {

    @Autowired
    private WxService wxService;

    @Scheduled(initialDelay = 1000, fixedDelay = 7000 * 1000)
    private void configureTasks() {
        log.debug("执行静态定时任务时间: " + LocalDateTime.now());
        try {
            wxService.getWxToken();
        } catch (Exception e) {
            e.printStackTrace();
            wxService.getWxToken();
        }
    }
}
