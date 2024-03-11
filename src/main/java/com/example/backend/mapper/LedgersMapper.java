package com.example.backend.mapper;

import com.example.backend.entity.Ledgers;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
public interface LedgersMapper {

    //查看账单，根据用户id查找
    @Select("select * from `familyusers` where `user_id` = #{userId} limit 10 offset #{offSet};")
    @Transactional
    List<Ledgers> selectLedgersByUserID(Ledgers ledgers,int offSet);
}
