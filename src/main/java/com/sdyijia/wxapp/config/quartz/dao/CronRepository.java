package com.sdyijia.wxapp.config.quartz.dao;

import com.sdyijia.wxapp.config.quartz.bean.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CronRepository extends JpaRepository<ScheduleJob,Long> {

}
