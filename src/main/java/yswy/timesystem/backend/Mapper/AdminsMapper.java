package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Admins;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface AdminsMapper {

    //注册新用户(只有账号、密码)
    @Insert("insert into `admins` (`admin_name`,`admin_password`) values(#{adminName},#{adminPassword});")
    @Transactional
    void insertRegister(Admins admins);

    //删除管理员，根据id
    @Delete("delete from `admins` where `admin_id` = #{adminId};")
    @Transactional
    void deleteAdmins(int adminID);

    //修改管理员个人信息，不包括密码、权限，根据id查找
    @Update("update `admins` set `admin_name` = #{adminName} where `admin_id` = #{adminID};")
    @Transactional
    void updateAdminsByID(Admins admins);

    //修改管理员密码，根据id查找
    @Update("update `admins` set `admin_password` = #{adminPassword} where `admin_id` = #{adminID};")
    @Transactional
    void updateAdminPasswordByID(Admins admins);

    //查找管理员id,通过用户名
    @Select("select `admin_id` from `admins` where `admin_name` = #{adminName};")
    @Transactional
    int selectForAdminIDByAdminName(String adminName);

    //查找同一用户名的人数
    @Select("select count(*) from `admins` where `admin_name` = #{adminName};")
    @Transactional
    int selectAdminNameSame(String adminName);

    //查找同一用户名、密码的人数
    @Select("select count(*) from `admins` where `admin_name` = #{adminName} and `admin_password` = #{adminPassword};")
    @Transactional
    int selectAdminNameAdminPasswordSame(String adminName,String adminPassword);

    //查找管理员个人信息,不包括密码,通过用户名
    @Select("select `admin_id` as adminID,`admin_name` as adminName,`admin_previliege` as adminPreviliege from `admins` where `admin_name` = #{adminName};")
    @Transactional
    Admins selectForAdminsByAdminName(String adminName);
}
