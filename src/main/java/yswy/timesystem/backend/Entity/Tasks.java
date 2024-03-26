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
    private Integer taskVisitedNumber;
    private Integer taskStatus;
    private Integer taskTimeCoinBounty;
    private String taskStatusRemark;
    private Integer taskEmployerID;
    private String userPhoto;
    private Integer counts;
    private Integer taskLable;
}

