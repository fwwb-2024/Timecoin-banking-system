package com.example.backend.Mapper;

import com.example.backend.Entity.Taskhistorys;
import com.example.backend.Entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Mapper
public interface TaskhistorysMapper {

    //查看任务历史，用户id查找，选10个，在offset后面的10个
    @Select("select * from `tasks` where `user_id` = #{userID} limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectTaskHistoryByUserID(Users users,int offSet);
}
