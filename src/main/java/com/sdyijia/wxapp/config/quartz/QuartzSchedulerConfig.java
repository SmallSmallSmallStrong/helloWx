package com.sdyijia.wxapp.config.quartz;

import com.sdyijia.wxapp.config.quartz.dao.CronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;

@Configuration
//@Component
@EnableScheduling // 2.开启静态定时任务
public class QuartzSchedulerConfig implements SchedulingConfigurer {

    //3.添加静态定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void configureTasks() {
//        System.err.println("执行定时任务1: " + LocalDateTime.now());
//    }

    @Autowired
    CronRepository cronRepository;


    /**
     * 动态执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        Trigger trigger1 = new Trigger()
//                .withIdentity("trigger1", "group1")
//                .startNow()
//                .build();
//        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
//                () -> System.out.println("执行定时任务2: " + LocalDateTime.now().toLocalTime()),
//                null
                //2.设置执行周期(Trigger)
//                triggerContext -> {
//                    //2.1 从数据库获取执行周期
//                    Cron cron = cronRepository.getOne(1l);
//                    //2.2 合法性校验.
//                    if (cron != null) {
//                        // Omitted Code ..
//                    }
//                    //2.3 返回执行周期(Date)
//                    return new CronTrigger(cron.getValue()).nextExecutionTime(triggerContext);
//                }

//        );


    }
}
