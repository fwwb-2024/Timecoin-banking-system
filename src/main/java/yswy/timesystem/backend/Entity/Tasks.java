package yswy.timesystem.backend.Entity;

import lombok.Data;

@Data
public class Tasks {
    private Integer taskID;
    private String taskName;
    private String taskEmployer;
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
