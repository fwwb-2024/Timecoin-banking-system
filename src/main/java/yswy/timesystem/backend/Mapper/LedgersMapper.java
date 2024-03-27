package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Ledgers;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import yswy.timesystem.backend.Entity.Tasks;

import java.util.List;
@Mapper
public interface LedgersMapper {

    //新建账单
    @Insert("insert into `ledgers` (`user_id`,`task_id`,`ledger_time_coin`) values(#{userID},#{taskID},#{ledgerTimeCoin});")
    @Transactional
    void insertRegister(Ledgers ledgers);

    //删除任务
    @Delete("delete from `ledgers` where `task_id` = #{taskId};")
    @Transactional
    void deleteLedgersByTaskID(int taskID);

    //查看账单，根据用户id查找
    @Select("select l.`ledger_id` as ledgerID,l.`user_id` as userID,l.`task_id` as taskID,l.`ledger_time_coin` as ledgerTimeCoin,t.`task_name` as taskName,t.`task_brief` as taskBrief from `ledgers` l join `tasks` t on l.`task_id`=t.`task_id` where `user_id` = #{userID} limit 10 offset #{offSet};")
    @Transactional
    List<Ledgers> selectLedgersByUserID(int userID, int offSet);
}
