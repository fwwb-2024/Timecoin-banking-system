package com.example.backend.Mapper;

import com.example.backend.Entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface UsersMapper {

    //注册新用户(只有账号、密码、手机号)
    @Insert("insert into `users` (`user_name`,`user_password`,`user_phone_number`) values(#{userName},#{userPassword},#{userPhoneNumber});")
    @Transactional
    void insertRegister(Users users);

    //注销用户，根据id
    @Delete("delete from `users` where `user_id` = #{userId}")
    @Transactional
    void deleteUsers(Users users);

    //修改用户个人信息，不包括密码、时间币余额、用户状态反馈，根据用户id查找
    @Update("update `users` set `user_name` = #{userName},`user_photo` = #{userPhoto},`user_status` = #{userStatus},`user_phone_number` = #{userPhoneNumber},`user_email` = #{userEmail},`user_id_number` = #{userIDNumber},`user_id_name` = #{userIDName},`user_age` = #{userAge},`user_sex` = #{userSex} where `user_id` = #{userID};")
    @Transactional
    void updateUsersByID(Users users);

    //修改用户密码，根据用户id查找
    @Update("update `users` set `user_password` = #{userPassword} where `user_id` = #{userID};")
    @Transactional
    void updateUserPasswordByID(Users users);

    //查找用户id，通过用户名查找
    @Select("select `user_id` from `users` where `user_name` = #{userName};")
    @Transactional
    int selectForUserIDByUserName(String userName);

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

    //查找用户时间币余额，通过用户名查找
    @Select("select `user_time_coin` from `users` where `user_name` = #{userName};")
    @Transactional
    Users selectForUserTimeCoinByUserName(String userName);

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
    @Select("select `user_id`,`user_name`,`user_photo`,`user_status`,`user_phone_number`,`user_email`,`user_id_number`,`user_id_name`,`user_age`,`user_sex` from `users` where `user_name` = #{userName};")
    @Transactional
    Users selectForUsersByUserName(String userName);




}
