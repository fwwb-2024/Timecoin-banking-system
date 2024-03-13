package com.example.backend.Mapper;

import com.example.backend.Entity.Familyusers;
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
    @Delete("delete from `familyusers` where `family_id` = #{familyId} and `user_id` = #{userID};")
    @Transactional
    void deleteFamilyusers(Familyusers familyusers);

    //查看本人在多少家庭中，根据用户id查找
    @Select("select * from `familyusers` where `user_id` = #{userId};")
    @Transactional
    List<Familyusers> selectFamilyusersByUserID(Familyusers familyusers);

    //查看家庭中的所有成员,根据家庭id
    @Select(("select * from `familyusers` where `family_id` = #{familyId};"))
    @Transactional
    List<Familyusers> selectFamilyusersByFamilyID(Familyusers familyusers);
}
