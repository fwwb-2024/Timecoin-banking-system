package com.example.backend.mapper;


import com.example.backend.entity.Familys;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
public interface FamilysMapper {

    //创建家庭
    @Insert("insert into `familys` (`house_holder`,`family_name`) values(#{houseHolder},#{familyName});")
    @Transactional
    void insertRegister(Familys familys);

    //删除家庭
    @Delete("delete from `familys` where `family_id` = #{familyId};")
    @Transactional
    void deleteFamilys(Familys familys);

    //修改家庭名称，id查找
    @Update("update `familys` set `family_name` = #{familyName} where `family_id` = #{familyID};")
    @Transactional
    void updateFamilyNameByFamilyID(Familys familys);

    //修改一家之主，id查找
    @Update("update `familys` set `house_holder` = #{houseHolder} where `family_id` = #{familyID};")
    @Transactional
    void updateHouseHolderByFamilyID(Familys familys);

}
