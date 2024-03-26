package yswy.timesystem.backend.Entity;

import lombok.Data;

@Data
public class Taskhistorys {
    private Integer taskHistoryID;
    private Integer userID;
    private Integer taskID;
    private Integer taskHistoryStatus;
    private Integer taskEmployerID;
    private String taskName;
    private String taskBeginTime;
    private String taskEndTime;
    private Integer taskTimeCoinBounty;
    private String taskBrief;
    private Integer taskStatus;
    private String taskEmployerName;
    private String userPhoto;
    private String taskStatusRemark;
    private String taskLable;
}
