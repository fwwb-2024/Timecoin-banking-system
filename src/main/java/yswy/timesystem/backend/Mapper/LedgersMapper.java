package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Ledgers;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Mapper
public interface LedgersMapper {

    //查看账单，根据用户id查找
    @Select("select `ledger_id` as ledgerID,`user_id` as userID,`task_id` as taskID,`ledger_time_coin` as ledgerTimeCoin from `ledgers` where `user_id` = #{userId} limit 10 offset #{offSet};")
    @Transactional
    List<Ledgers> selectLedgersByUserID(int userID, int offSet);
}
