package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Taskhistorys;
import yswy.timesystem.backend.Entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Mapper
public interface TaskhistorysMapper {

    //查看用户id，taskid查找
    @Select("select `user_id` as userID from `taskhistorys` where `task_id` = #{taskID};")
    @Transactional
    int selectUserIDByTaskID(int taskID);

    //查看任务历史，用户id查找，选10个，在offset后面的10个
    @Select("select th.`user_id` as userID,th.`task_id` as taskID,t.`task_employer` as taskEmployerName,t.`task_name` as taskName,t.`task_begin_time` as taskBeginTime,t.`task_end_time` as taskEndTime,t.`task_time_coin_bounty` as taskTimeCoinBounty,t.`task_brief` as taskBrief,th.`task_history_status` as taskHistoryStatus,`task_employer_id` as taskEmployerID,u.`user_photo` as userPhoto from `taskhistorys` th join `tasks` t on th.`task_id`=t.`task_id` join `users` u on th.`task_employer_id`=u.`user_id` where th.`user_id` = #{userID} order by th.`task_history_id` desc limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectAllTaskHistoryByUserID(int userID, int offSet);

    //查看任务历史，雇主查找，选10个，在offset后面10个
    @Select("select th.`user_id` as userID,th.`task_id` as taskID,t.`task_employer` as taskEmployerName,t.`task_name` as taskName,t.`task_begin_time` as taskBeginTime,t.`task_end_time` as taskEndTime,t.`task_time_coin_bounty` as taskTimeCoinBounty,t.`task_brief` as taskBrief,th.`task_history_status` as taskHistoryStatus,`task_employer_id` as taskEmployerID,u.`user_photo` as userPhoto from `taskhistorys` th join `tasks` t on th.`task_id`=t.`task_id`  join `users` u on th.`task_employer_id`=u.`user_id`where th.`task_employer_id` = #{taskEmployerID} order by th.`task_history_id` desc limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectAllTaskHistoryByTaskEmployerID(int taskEmployerID, int offSet);

    //查看当前任务，用户id查找，选10个，在offset后面的10个
    @Select("select th.`user_id` as userID,th.`task_id` as taskID,t.`task_employer` as taskEmployerName,t.`task_name` as taskName,t.`task_begin_time` as taskBeginTime,t.`task_end_time` as taskEndTime,t.`task_time_coin_bounty` as taskTimeCoinBounty,t.`task_brief` as taskBrief,t.`task_status` as taskStatus,u.`user_photo` as userPhoto,t.`task_status_remark` as taskStatusRemark from `taskhistorys` th join `tasks` t on th.`task_id`=t.`task_id` join `users` u on th.`task_employer_id`=u.`user_id` where t.`task_status` in (2,3,4,5,7) and th.`user_id` = #{userID} order by th.`task_history_id` desc limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectTaskHistoryByUserID(int userID, int offSet);

    //查看当前任务，雇主查找，选10个，在offset后面10个
    @Select("select th.`user_id` as userID,th.`task_id` as taskID,t.`task_employer` as taskEmployerName,t.`task_name` as taskName,t.`task_begin_time` as taskBeginTime,t.`task_end_time` as taskEndTime,t.`task_time_coin_bounty` as taskTimeCoinBounty,t.`task_brief` as taskBrief,t.`task_status` as taskStatus,u.`user_photo` as userPhoto,t.`task_status_remark` as taskStatusRemark from `taskhistorys` th join `tasks` t on th.`task_id`=t.`task_id` join `users` u on th.`task_employer_id`=u.`user_id` where t.`task_status` in (1,2,3,4,5,7) and th.`task_employer_id` = #{taskEmployerID} order by th.`task_history_id` desc limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectTaskHistoryByTaskEmployerID(int taskEmployerID, int offSet);




}
