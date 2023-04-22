package com.yxq.staservice.schedule;

import com.yxq.staservice.service.StatisticsDailyService;
import com.yxq.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService service;
    //每隔五秒中执行一次这个方法
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1() {
//        System.out.println("..............执行了");
//    }

    //在每天凌晨一点，把前一天数据进行数据查询
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        service.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
