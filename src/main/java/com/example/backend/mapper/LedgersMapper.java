package com.example.backend.mapper;

import com.example.backend.entity.Ledgers;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Mapper
public interface LedgersMapper {

    //查看账单，根据用户id查找
    @Select("select * from `familyusers` where `user_id` = #{userId} limit 10 offset #{offSet};")
    @Transactional
    List<Ledgers> selectLedgersByUserID(Ledgers ledgers,int offSet);
}
