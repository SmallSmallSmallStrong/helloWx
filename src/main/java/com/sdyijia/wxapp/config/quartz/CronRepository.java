package com.sdyijia.wxapp.config.quartz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CronRepository extends JpaRepository<Cron,Long> {

}
