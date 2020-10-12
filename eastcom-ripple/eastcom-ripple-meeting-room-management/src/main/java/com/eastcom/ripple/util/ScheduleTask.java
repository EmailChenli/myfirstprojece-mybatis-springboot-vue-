package com.eastcom.ripple.util;

import com.eastcom.ripple.util.job.MeetingRecordAutoDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@EnableScheduling   // 1.开启定时任务
//@EnableAsync        // 2.开启多线程
public class ScheduleTask {

    @Autowired
    private MeetingRecordAutoDo meetingRecordAutoDo;
    //@Async
    //@Scheduled(cron = "*/10 * * * * ?")     //每10秒执行
    @Scheduled(cron = "0 0,30 * * * ?")  //在整点和30分执行
    public void first(){
        log.info("定时任务开始 : " + new Date());

        meetingRecordAutoDo.doChangeBookingStatus();
        meetingRecordAutoDo.doDelete();
        meetingRecordAutoDo.doSave();
    }

}
