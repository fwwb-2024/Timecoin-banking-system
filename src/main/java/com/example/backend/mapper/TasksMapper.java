package com.example.backend.mapper;

import com.example.backend.entity.Tasks;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
public interface TasksMapper {

    //新建任务
    @Insert("insert into `tasks` (`task_name`,`task_employer`,`task_address`,`task_detail`,`task_brief`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`) values(#{taskName},#{taskEmployer},#{taskAddress},#{taskDetail},#{taskBrief},#{taskBeginTime},#{taskEndTime},#{taskTimeCoinBounty});")
    @Transactional
    void insertRegister(Tasks tasks);

    //删除任务
    @Delete("delete from `tasks` where `task_id` = #{taskId};")
    @Transactional
    void deleteTasksByTaskID(Tasks tasks);

    //修改任务,id查找
    @Update("update `tasks` set `task_name`= #{taskName}, `task_employer`= # {taskEmployer}, `task_address`=#{taskAddress},`task_detail`=#{taskDetail},`task_brief`=#{taskBrief},`task_begin_time`=#{taskBeginTime},`task_end_time`=#{taskEndTime},`task_time_coin_bounty`=#{taskTimeCoinBounty} where `task_id` = #{taskID};")
    @Transactional
    void updateTasksByTaskID(Tasks tasks);

    //全局查找，根据任务id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskIDAsc(int offSet);

    //全局查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAsc(int offSet);

    //全局查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDesc(int offSet);

    //全局查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAsc(int offSet);

    //全局查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDesc(int offSet);

    //全局查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskBeginTimeAsc(int offSet);

    //全局查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskBeginTimeDesc(int offSet);

    //全局查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEndTimeAsc(int offSet);

    //全局查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEndTimeDesc(int offSet);

    //全局查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskTimeCoinBountyAsc(int offSet);

    //全局查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskTimeCoinBountyDesc(int offSet);

    //根据任务名查找，根据id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskID(Tasks tasks,int offSet);

    //根据任务名查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskName(Tasks tasks,int offSet);

    //根据任务名查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskName(Tasks tasks,int offSet);

    //根据任务名查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskEmployer(Tasks tasks,int offSet);

    //根据任务名查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskEmployer(Tasks tasks,int offSet);

    //根据任务名查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskBeginTime(Tasks tasks,int offSet);

    //根据任务名查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskBeginTime(Tasks tasks,int offSet);

    //根据任务名查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskEndTime(Tasks tasks,int offSet);

    //根据任务名查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskEndTime(Tasks tasks,int offSet);

    //根据任务名查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskTimeCoinBounty(Tasks tasks,int offSet);

    //根据任务名查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskTimeCoinBounty(Tasks tasks,int offSet);

    //根据雇主查找，根据id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskID(Tasks tasks,int offSet);

    //根据雇主查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskName(Tasks tasks,int offSet);

    //根据雇主查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskName(Tasks tasks,int offSet);

    //根据雇主查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskEmployer(Tasks tasks,int offSet);

    //根据雇主查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskEmployer(Tasks tasks,int offSet);

    //根据雇主查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskBeginTime(Tasks tasks,int offSet);

    //根据雇主查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskBeginTime(Tasks tasks,int offSet);

    //根据雇主查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskEndTime(Tasks tasks,int offSet);

    //根据雇主查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskEndTime(Tasks tasks,int offSet);

    //根据雇主查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskTimeCoinBounty(Tasks tasks,int offSet);

    //根据雇主查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id`,`task_name`,`task_employer`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`,`task_brief` from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskTimeCoinBounty(Tasks tasks,int offSet);

    //查看任务详情，id查找
    @Select("select * from `tasks` where `task_id` = #{taskID};")
    @Transactional
    Tasks selectTasksByTaskID(Tasks tasks);

}
