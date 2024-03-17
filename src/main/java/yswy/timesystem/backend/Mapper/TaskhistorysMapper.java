package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Taskhistorys;
import yswy.timesystem.backend.Entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Mapper
public interface TaskhistorysMapper {

    //查看任务历史，用户id查找，选10个，在offset后面的10个
    @Select("select `task_history_id` as taskHistoryID,`user_id` as userID,`task_id` as taskID,`task_history_status` as taskHistoryStatus,`task_employer_id` as taskEmployerID from `taskhistorys` where `user_id` = #{userID} limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectAllTaskHistoryByUserID(int userID, int offSet);

    //查看任务历史，雇主查找，选10个，在offset后面10个
    @Select("select `task_history_id` as taskHistoryID,`user_id` as userID,`task_id` as taskID,`task_history_status` as taskHistoryStatus,`task_employer_id` as taskEmployerID from `taskhistorys` where `task_employer_id` = #{taskEmployerID} limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectAllTaskHistoryByTaskEmployerID(int taskEmployerID, int offSet);

    //查看当前任务，用户id查找，选10个，在offset后面的10个
    @Select("select `task_history_id` as taskHistoryID,`user_id` as userID,`task_id` as taskID,`task_history_status` as taskHistoryStatus,`task_employer_id` as taskEmployerID from `taskhistorys` where `task_history_status` in (2,3,4,5) and `user_id` = #{userID} limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectTaskHistoryByUserID(int userID, int offSet);

    //查看当前任务，雇主查找，选10个，在offset后面10个
    @Select("select `task_history_id` as taskHistoryID,`user_id` as userID,`task_id` as taskID,`task_history_status` as taskHistoryStatus,`task_employer_id` as taskEmployerID from `taskhistorys` where `task_history_status` in (1,2,3,4,5) and `task_employer_id` = #{taskEmployerID} limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectTaskHistoryByTaskEmployerID(int taskEmployerID, int offSet);
}
