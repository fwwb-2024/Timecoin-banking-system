package com.example.backend.entity;

import lombok.Data;

@Data
public class Tasks {
    private Integer taskID;
    private String taskName;
    private Integer taskEmployer;
    private String taskAddress;
    private String taskDetail;
    private String taskBrief;
    private String taskBeginTime;
    private String taskEndTime;
    private String taskVisitedNumber;
    private Integer taskStatus;
    private String taskTimeCoinBounty;
    private String taskStatusRemark;
}
