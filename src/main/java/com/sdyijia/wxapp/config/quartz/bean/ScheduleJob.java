package com.sdyijia.wxapp.config.quartz.bean;


import javax.persistence.*;

@Entity
public class ScheduleJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 任务id */
    private Long id;
    /**
     * 任务名称
     */
    @Column(unique = true)
    private String jobName;
    /**
     * 任务分组
     */
    @Column(unique = true)
    private String jobGroup;
    /**
     * 任务状态 0禁用 1启用 2删除
     */
    private Integer jobStatus;
    /**
     * 任务运行时间表达式
     */
    private String cronExpression;
    /**
     * 任务描述
     */
    private String note;

    public ScheduleJob() {
    }

    public ScheduleJob(String jobName, String jobGroup, Integer jobStatus, String cronExpression) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobStatus = jobStatus;
        this.cronExpression = cronExpression;
    }

    public ScheduleJob(String jobName, String jobGroup, Integer jobStatus, String cronExpression, String desc) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobStatus = jobStatus;
        this.cronExpression = cronExpression;
        this.note = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}