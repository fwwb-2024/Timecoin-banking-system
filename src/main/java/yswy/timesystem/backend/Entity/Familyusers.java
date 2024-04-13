package yswy.timesystem.backend.Entity;

import lombok.Data;

@Data
public class Familyusers {
    private Integer familyUserID;
    private Integer familyID;
    private Integer userID;
    private String userName;
    private String familyName;
    private Integer houseHolder;
    private String houseHolderName;
}
