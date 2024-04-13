package yswy.timesystem.backend.Entity;

import lombok.Data;

@Data
public class Ledgers {
    private Integer ledgerID;
    private Integer userID;
    private Integer taskID;
    private int ledgerTimeCoin;
    private String taskName;
    private String taskBrief;
}
