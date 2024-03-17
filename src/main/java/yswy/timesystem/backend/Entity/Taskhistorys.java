package yswy.timesystem.backend.Entity;

import lombok.Data;

@Data
public class Taskhistorys {
    private Integer taskHistoryID;
    private Integer userID;
    private Integer taskID;
    private Integer taskHistoryStatus;
    private Integer taskEmployerID;
}
