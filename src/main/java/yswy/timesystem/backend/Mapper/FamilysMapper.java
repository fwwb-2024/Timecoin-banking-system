package yswy.timesystem.backend.Mapper;


import yswy.timesystem.backend.Entity.Familys;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface FamilysMapper {

    //创建家庭
    @Insert("insert into `familys` (`house_holder`,`family_name`,`house_holder_name`) values(#{houseHolder},#{familyName}#{houseHolderName});")
    @Transactional
    void insertRegister(Familys familys);

    //删除家庭
    @Delete("delete from `familys` where `family_id` = #{familyId};")
    @Transactional
    void deleteFamilys(int familyID);

    //修改家庭名称，id查找
    @Update("update `familys` set `family_name` = #{familyName} where `family_id` = #{familyID};")
    @Transactional
    void updateFamilyNameByFamilyID(Familys familys);

    //修改一家之主，id查找
    @Update("update `familys` set `house_holder` = #{houseHolder},`house_holder_name` = #{houseHolderName} where `family_id` = #{familyID};")
    @Transactional
    void updateHouseHolderByFamilyID(Familys familys);

    @Select("select family_id from `familys` where `family_name` = #{familyName} and `house_holder` = #{houseHolder};")
    @Transactional
    int selectFamilyIDByFamilyNameAndHouseHolder(Familys familys);
}
