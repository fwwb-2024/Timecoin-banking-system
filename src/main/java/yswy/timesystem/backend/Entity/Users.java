package yswy.timesystem.backend.Entity;

import lombok.Data;

@Data
public class Users {
    private Integer userID;
    private String userName;
    private String userPassword;
    private Byte userPhoto;
    private Integer userStatus;
    private String userPhoneNumber;
    private String userEmail;
    private String userIDNumber;
    private String userIDName;
    private String userAge;
    private Integer userTimeCoin;
    private String userStatusRemark;
    private String userSex;
    private String token;
}
