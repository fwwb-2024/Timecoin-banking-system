package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Taskhistorys;
import yswy.timesystem.backend.Entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Mapper
public interface TaskhistorysMapper {

    //查看任务历史，用户id查找，选10个，在offset后面的10个
    @Select("select `task_history_id` as taskHistoryID,`user_id` as userID,`task_id` as taskID,`task_history_status` as taskHistoryStatus from `taskhistorys` where `user_id` = #{userID} limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectTaskHistoryByUserID(int userID, int offSet);
}
