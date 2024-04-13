package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Familyusers;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Mapper
public interface FamilyusersMapper {

    //添加家庭成员
    @Insert("insert into `familyusers` (`family_id`,`user_id`,`user_name`) values(#{familyID},#{userID},#{userName});")
    @Transactional
    void insertRegister(Familyusers familyusers);

    //删除家庭成员，根据家庭id和被删除的用户id查找
    @Delete("delete from `familyusers` where `family_id` = #{familyID} and `user_id` = #{userID};")
    @Transactional
    void deleteFamilyusers(int familyID,int userID);

    //查看本人在多少家庭中，根据用户id查找
    @Select("select fu.`family_user_id` as familyUserID,fu.`family_id` as familyID,fu.`user_id` as userID,fu.`user_name` as userName,f.`family_name` as familyName from `familyusers` fu join `familys` f on fu.`family_id` = f.`family_id` where fu.`user_id` = #{userId};")
    @Transactional
    List<Familyusers> selectFamilyusersByUserID(int userID);

    //查看家庭中的所有成员,根据家庭id
    @Select("select fu.`family_user_id` as familyUserID,fu.`family_id` as familyID,fu.`user_id` as userID,fu.`user_name` as userName,f.`family_name` as familyName,f.`house_holder_name` as houseHolderName from `familyusers` fu join `familys` f on fu.`family_id` = f.`family_id` where fu.`family_id` = #{familyId};")
    @Transactional
    List<Familyusers> selectFamilyusersByFamilyID(int familyID);

    //查找同一家庭下、相同userid的人数
    @Select("select count(*) from `familyusers` where `family_id` = #{familyID} and `user_id` = #{userID};")
    @Transactional
    int selectFamilyIDUserIDSame(int familyID,int userID);
}
