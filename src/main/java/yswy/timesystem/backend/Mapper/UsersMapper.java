package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Tasks;
import yswy.timesystem.backend.Entity.Tasksmulti;
import yswy.timesystem.backend.Entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface UsersMapper {

    //注册新用户(只有账号、密码、手机号)
    @Insert("insert into `users` (`user_name`,`user_password`,`user_phone_number`,`user_regist_time`) values(#{userName},#{userPassword},#{userPhoneNumber},#{userRegistTime});")
    @Transactional
    void insertRegister(Users users);

    //注销用户，根据id
    @Delete("delete from `users` where `user_id` = #{userId}")
    @Transactional
    void deleteUsers(int userID);

    //上传或修改头像，根据id
    @Update("update `users` set `user_photo` = #{userPhoto} where `user_id` = #{userID};")
    @Transactional
    void updateUserPhotoByID(Users users);

    //修改用户个人信息，不包括密码、时间币余额、用户状态反馈、照片、状态，根据用户id查找
    @Update("update `users` set `user_name` = #{userName},`user_phone_number` = #{userPhoneNumber},`user_email` = #{userEmail},`user_id_number` = #{userIDNumber},`user_id_name` = #{userIDName},`user_age` = #{userAge},`user_sex` = #{userSex} where `user_id` = #{userID};")
    @Transactional
    void updateUsersByID(Users users);

    //修改用户密码，根据用户id查找
    @Update("update `users` set `user_password` = #{userPassword} where `user_id` = #{userID};")
    @Transactional
    void updateUserPasswordByID(int userID,String userPassword);

    //修改时间币，根据用户id查找
    @Update("update `users` set `user_time_coin` = #{userTimeCoin} where `user_id` = #{userID};")
    @Transactional
    void updateUserTimeCoinByID(int userID,int userTimeCoin);

    //上传或更新头像
    @Update("update `users` set `user_photo` = #{userPhoto} where `user_id` = #{userID};")
    @Transactional
    void updateTheUserPhotoByID(int userID,String userPhoto);

    //用户申请，状态变为未审核
    @Update("update `users` set `user_status` = 1,`user_status_remark` = #{userStatusRemark} where `user_id` = #{userID};")
    @Transactional
    void updateUserStatusOneByUserID(int userID,String userStatusRemark);

    //管理员审核用户状态为正常
    @Update("update `users` set `user_status` = 2,`user_status_remark` = #{userStatusRemark} where `user_id` = #{userID};")
    @Transactional
    void updateUserStatusTwoByUserID(int userID,String userStatusRemark);

    //管理员审核用户状态为未通过
    @Update("update `users` set `user_status` = 3,`user_status_remark` = #{userStatusRemark} where `user_id` = #{userID};")
    @Transactional
    void updateUserStatusThreeByUserID(int userID,String userStatusRemark);

    //管理员冻结用户状态
    @Update("update `users` set `user_status` = 4,`user_status_remark` = #{userStatusRemark} where `user_id` = #{userID};")
    @Transactional
    void updateUserStatusFourByUserID(int userID,String userStatusRemark);

    //管理员设置用户状态为官方号
    @Update("update `users` set `user_status` = 5,`user_status_remark` = #{userStatusRemark} where `user_id` = #{userID};")
    @Transactional
    void updateUserStatusFiveByUserID(int userID,String userStatusRemark);

    //查找用户id，通过用户名查找
    @Select("select `user_id` from `users` where `user_name` = #{userName};")
    @Transactional
    int selectForUserIDByUserName(String userName);

    //查找用户名，通过id查找
    @Select("select `user_name` from `users` where `user_id` = #{userID};")
    @Transactional
    String selectForUserNameByUserID(int userID);

    //查找用户名，通过电话查找
    @Select("select `user_name` from `users` where `user_phone_number` = #{userPhoneNumber};")
    @Transactional
    String selectForUserNameByUserPhoneNumber(String userPhoneNumber);

    //查找用户名，通过身份证号查找
    @Select("select `user_name` from `users` where `user_id_number` = #{userIDNumber};")
    @Transactional
    String selectForUserNameByUserIDNumber(String userIDNumber);

    //查找用户电话，通过id查找
    @Select("select `user_phone_number` from `users` where `user_id` = #{userID};")
    @Transactional
    String selectForUserPhoneNumberByUserID(int userID);

    //查找用户身份证号，通过id查找
    @Select("select `user_id_number` from `users` where `user_id` = #{userID};")
    @Transactional
    String selectForUserIDNumberByUserID(int userID);

    //查找用户时间币余额，通过用户id查找
    @Select("select `user_time_coin` from `users` where `user_id` = #{userID};")
    @Transactional
    int selectForUserTimeCoinByUserID(int userID);

    //查找同一用户名的人数
    @Select("select count(*) from `users` where `user_name` = #{userName};")
    @Transactional
    int selectUserNameSame(String userName);

    //查找同一电话号的人数
    @Select("select count(*) from `users` where `user_phone_number` = #{userPhoneNumber};")
    @Transactional
    int selectUserPhoneNumberSame(String userPhoneNumber);

    //查找同一身份证号的人数
    @Select("select count(*) from `users` where `user_id_number` = #{userIDNumber};")
    @Transactional
    int selectUserIDNumberSame(String userIDNumber);

    //查找用户状态，通过用户id查找
    @Select("select `user_status` from `users` where `user_id` = #{userID};")
    @Transactional
    int selectForUserStatusByUserID(int userID);

    //查找用户时间币余额，通过用户名查找
    @Select("select `user_time_coin` from `users` where `user_name` = #{userName};")
    @Transactional
    int selectForUserTimeCoinByUserName(String userName);

    //查找同一用户名、密码的人数
    @Select("select count(*) from `users` where `user_name` = #{userName} and `user_password` = #{userPassword};")
    @Transactional
    int selectUserNameUserPasswordSame(String userName,String userPassword);

    //查找同一电话号、密码的人，返回用户名
    @Select("select `user_name` from `users` where `user_phone_number` = #{userPhoneNumber} and `user_password` = #{userPassword};")
    @Transactional
    String selectForUserNameByUserPhoneNumberUserPasswordSame(String userPhoneNumber,String userPassword);

    //查找同一身份证号、密码的人，返回用户名
    @Select("select `user_name` from `users` where `user_id_number` = #{userIDNumber} and `user_password` = #{userPassword};")
    @Transactional
    String selectForUserNameByUserIDNumberUserPasswordSame(String userIDNumber,String userPassword);

    //查找用户个人信息，不包含密码、时间币余额、用户状态反馈，根据用户名查找
    @Select("select `user_id` as userID,`user_name` as userName,`user_photo` as userPhoto,`user_status` as userStatus,`user_phone_number` as userPhoneNumber,`user_email` as userEmail,`user_id_number` as userIDNumber,`user_id_name` as userIDName,`user_age` as userAge,`user_sex` as userSex from `users` where `user_name` = #{userName};")
    @Transactional
    Users selectForUsersByUserName(String userName);

    //查找未审核用户列表，根据id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `user_id` as userID,`user_name` as userName,`user_phone_number` as userPhoneNumber,`user_email` as userEmail,`user_id_number` as userIDNumber,`user_id_name` as userIDName,`user_age` as userAge,`user_status_remark` as userStatusRemark from `users` where `user_status` = #{userStatus} order by user_id desc limit 10 offset #{offSet};")
    @Transactional
    List<Users> selectUnAccessByUserIDAscAdmin(int offSet, int userStatus);

    //查找最近用户注册量
    @Select("select date(user_regist_time) as taskBeginTime, count(*) as counts " +
            "from users " +
            "where date(user_regist_time) >= date_sub(curdate(), interval #{days} day) " +
            "group by date(user_regist_time) " +
            "order by date(user_regist_time) asc")
    List<Tasksmulti> getRecentRegisterUsers(int days);


}
