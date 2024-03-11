package com.example.backend.mapper;

import com.example.backend.entity.Taskhistorys;
import com.example.backend.entity.Users;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
public interface TaskhistorysMapper {

    //查看任务历史，用户id查找，选10个，在offset后面的10个
    @Select("select * from `tasks` where `user_id` = #{userID} limit 10 offset #{offSet};")
    @Transactional
    List<Taskhistorys> selectTaskHistoryByUserID(Users users,int offSet);
}
