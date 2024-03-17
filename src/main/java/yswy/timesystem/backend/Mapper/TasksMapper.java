package yswy.timesystem.backend.Mapper;

import yswy.timesystem.backend.Entity.Tasks;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Mapper
public interface TasksMapper {

    //新建任务
    @Insert("insert into `tasks` (`task_name`,`task_employer`,`task_address`,`task_detail`,`task_brief`,`task_begin_time`,`task_end_time`,`task_time_coin_bounty`) values(#{taskName},#{taskEmployer},#{taskAddress},#{taskDetail},#{taskBrief},#{taskBeginTime},#{taskEndTime},#{taskTimeCoinBounty});")
    @Transactional
    void insertRegister(Tasks tasks);

    //删除任务
    @Delete("delete from `tasks` where `task_id` = #{taskId};")
    @Transactional
    void deleteTasksByTaskID(int taskID);

    //修改任务,id查找
    @Update("update `tasks` set `task_name`= #{taskName}, `task_employer`= # {taskEmployer}, `task_address`=#{taskAddress},`task_detail`=#{taskDetail},`task_brief`=#{taskBrief},`task_begin_time`=#{taskBeginTime},`task_end_time`=#{taskEndTime},`task_time_coin_bounty`=#{taskTimeCoinBounty} where `task_id` = #{taskID};")
    @Transactional
    void updateTasksByTaskID(Tasks tasks);

    //全局查找，根据任务id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskIDAscAdmin(int offSet);

    //全局查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscAdmin(int offSet);

    //全局查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescAdmin(int offSet);

    //全局查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscAdmin(int offSet);

    //全局查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescAdmin(int offSet);

    //全局查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskBeginTimeAscAdmin(int offSet);

    //全局查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskBeginTimeDescAdmin(int offSet);

    //全局查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEndTimeAscAdmin(int offSet);

    //全局查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEndTimeDescAdmin(int offSet);

    //全局查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskTimeCoinBountyAscAdmin(int offSet);

    //全局查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskTimeCoinBountyDescAdmin(int offSet);

    //根据任务名查找，根据id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskIDAdmin(String taskName,int offSet);

    //根据任务名查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskNameAdmin(String taskName,int offSet);

    //根据任务名查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskNameAdmin(String taskName,int offSet);

    //根据任务名查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskEmployerAdmin(String taskName,int offSet);

    //根据任务名查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskEmployerAdmin(String taskName,int offSet);

    //根据任务名查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskBeginTimeAdmin(String taskName,int offSet);

    //根据任务名查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskBeginTimeAdmin(String taskName,int offSet);

    //根据任务名查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskEndTimeAdmin(String taskName,int offSet);

    //根据任务名查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskEndTimeAdmin(String taskName,int offSet);

    //根据任务名查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskTimeCoinBountyAdmin(String taskName,int offSet);

    //根据任务名查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_name like concat('%', #{taskName}, '%') order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskTimeCoinBountyAdmin(String taskName,int offSet);

    //根据雇主查找，根据id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskIDAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskNameAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskNameAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskEmployerAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskEmployerAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskBeginTimeAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskBeginTimeAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskEndTimeAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskEndTimeAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskTimeCoinBountyAdmin(String taskEmployer,int offSet);

    //根据雇主查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where task_employer like concat('%', #{taskEmployer}, '%') order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskTimeCoinBountyAdmin(String taskEmployer,int offSet);


    //全局查找，根据任务id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskIDAsc(int offSet);

    //全局查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAsc(int offSet);

    //全局查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDesc(int offSet);

    //全局查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAsc(int offSet);

    //全局查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDesc(int offSet);

    //全局查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskBeginTimeAsc(int offSet);

    //全局查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskBeginTimeDesc(int offSet);

    //全局查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEndTimeAsc(int offSet);

    //全局查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEndTimeDesc(int offSet);

    //全局查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskTimeCoinBountyAsc(int offSet);

    //全局查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskTimeCoinBountyDesc(int offSet);

    //根据任务名查找，根据id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskID(String taskName,int offSet);

    //根据任务名查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskName(String taskName,int offSet);

    //根据任务名查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskName(String taskName,int offSet);

    //根据任务名查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskEmployer(String taskName,int offSet);

    //根据任务名查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskEmployer(String taskName,int offSet);

    //根据任务名查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskBeginTime(String taskName,int offSet);

    //根据任务名查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskBeginTime(String taskName,int offSet);

    //根据任务名查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskEndTime(String taskName,int offSet);

    //根据任务名查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskEndTime(String taskName,int offSet);

    //根据任务名查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameAscTaskTimeCoinBounty(String taskName,int offSet);

    //根据任务名查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_name like concat('%', #{taskName}, '%') order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskNameDescTaskTimeCoinBounty(String taskName,int offSet);

    //根据雇主查找，根据id顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_id asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskID(String taskEmployer,int offSet);

    //根据雇主查找，根据任务名顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_name asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskName(String taskEmployer,int offSet);

    //根据雇主查找，根据任务名逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_name desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskName(String taskEmployer,int offSet);

    //根据雇主查找，根据雇主顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_employer asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskEmployer(String taskEmployer,int offSet);

    //根据雇主查找，根据雇主逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_employer desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskEmployer(String taskEmployer,int offSet);

    //根据雇主查找，根据开始时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_begin_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskBeginTime(String taskEmployer,int offSet);

    //根据雇主查找，根据开始时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_begin_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskBeginTime(String taskEmployer,int offSet);

    //根据雇主查找，根据结束时间顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_end_time asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskEndTime(String taskEmployer,int offSet);

    //根据雇主查找，根据结束时间逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_end_time desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskEndTime(String taskEmployer,int offSet);

    //根据雇主查找，根据时间币悬赏顺序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_time_coin_bounty asc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerAscTaskTimeCoinBounty(String taskEmployer,int offSet);

    //根据雇主查找，根据时间币悬赏逆序排序,一次查10个，查第offSet后面的10个
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_brief` as taskBrief,`task_status` as taskStatus from `tasks` where `task_status`=2 and task_employer like concat('%', #{taskEmployer}, '%') order by task_time_coin_bounty desc limit 10 offset #{offSet};")
    @Transactional
    List<Tasks> selectAbstractTasksByTaskEmployerDescTaskTimeCoinBounty(String taskEmployer,int offSet);


    //查看任务详情，id查找
    @Select("select `task_id` as taskID,`task_name` as taskName,`task_employer` as taskEmployer,`task_address` as taskAddress,`task_detail` as taskDetail,`task_brief` as taskBrief,`task_begin_time` as taskBeginTime,`task_end_time` as taskEndTime,`task_time_coin_bounty` as taskTimeCoinBounty,`task_visited_number` as taskVisitedNumber,`task_status` as taskStatus from `tasks` where `task_id` = #{taskID};")
    @Transactional
    Tasks selectTasksByTaskID(int taskID);

}
