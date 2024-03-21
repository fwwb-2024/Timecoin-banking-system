package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.multipart.MultipartFile;
import yswy.timesystem.backend.Entity.Tasksmulti;
import yswy.timesystem.backend.Mapper.TaskhistorysMapper;
import yswy.timesystem.backend.Mapper.UsersMapper;
import yswy.timesystem.backend.Util.DailyTaskUtil;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Tasks;
import yswy.timesystem.backend.Mapper.TasksMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 23:07
 * @Description: com.example.backend.controller
 * @version: 1.0
 */


@RestController
@RequestMapping
public class TasksController {
    @Resource
    private TasksMapper tasksMapper;
    @Resource
    private TaskhistorysMapper taskhistorysMapper;
    @Resource
    private UsersMapper usersMapper;

    private static String TASK_PHOTO_PATH = "C:\\Users\\Administrator\\Desktop\\fwwb\\clone\\Timecoin-banking-system\\src\\main\\resources\\static\\userphoto\\";
    private static String TASK_PHOTO_STATIC_PATH="static\\userphoto\\";
    private static String TASK_STATIC="http://10.195.28.44:9090/";

    private static String USER_STATIC="http://10.195.28.44:9090/";

    @Operation(summary = "管理员查看任务列表接口", description = "返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/tasks/taskCenter/tasksAdmin")//查看任务列表
    public List<Tasks> taskCenterTasksAdmin(@RequestParam int offSet,@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskIDAscAdmin(offSet);
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscAdmin(offSet);
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescAdmin(offSet);
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscAdmin(offSet);
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescAdmin(offSet);
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeAscAdmin(offSet);
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeDescAdmin(offSet);
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeAscAdmin(offSet);
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeDescAdmin(offSet);
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyAscAdmin(offSet);
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyDescAdmin(offSet);
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskIDAscAdmin(offSet);
        }
        // 遍历列表并更新每个Tasks对象的userPhoto字段
        for (Tasks task : tasksList) {
            if (task.getUserPhoto() != null && !task.getUserPhoto().isEmpty()) {
                task.setUserPhoto(USER_STATIC + task.getUserPhoto());
            }
        }
        return tasksList;
    }

    @Operation(summary = "管理员搜索任务名，查看任务列表接口", description = "搜索任务名，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskName", description = "任务名", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskNameAdmin")//搜索任务名，查看任务列表
    public List<Tasks> taskCenterTasksForTaskNameAdmin(@RequestParam String taskName,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskIDAdmin(taskName,offSet);
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskNameAdmin(taskName,offSet);
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskNameAdmin(taskName,offSet);
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEmployerAdmin(taskName,offSet);
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEmployerAdmin(taskName,offSet);
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskBeginTimeAdmin(taskName,offSet);
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskBeginTimeAdmin(taskName,offSet);
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEndTimeAdmin(taskName,offSet);
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEndTimeAdmin(taskName,offSet);
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskTimeCoinBountyAdmin(taskName,offSet);
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskTimeCoinBountyAdmin(taskName,offSet);
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskIDAdmin(taskName,offSet);
        }
        // 遍历列表并更新每个Tasks对象的userPhoto字段
        for (Tasks task : tasksList) {
            if (task.getUserPhoto() != null && !task.getUserPhoto().isEmpty()) {
                task.setUserPhoto(USER_STATIC + task.getUserPhoto());
            }
        }
        return tasksList;
    }

    @Operation(summary = "管理员搜索任务名，查看任务列表接口", description = "搜索雇主，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskEmployer", description = "雇主", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskEmployerAdmin")//搜索雇主，查看任务列表
    public List<Tasks> taskCenterTasksAdmin(@RequestParam String taskEmployer,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskIDAdmin(taskEmployer,offSet);
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskNameAdmin(taskEmployer,offSet);
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskNameAdmin(taskEmployer,offSet);
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEmployerAdmin(taskEmployer,offSet);
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEmployerAdmin(taskEmployer,offSet);
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskBeginTimeAdmin(taskEmployer,offSet);
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskBeginTimeAdmin(taskEmployer,offSet);
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEndTimeAdmin(taskEmployer,offSet);
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEndTimeAdmin(taskEmployer,offSet);
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskTimeCoinBountyAdmin(taskEmployer,offSet);
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskTimeCoinBountyAdmin(taskEmployer,offSet);
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskIDAdmin(taskEmployer,offSet);
        }
        // 遍历列表并更新每个Tasks对象的userPhoto字段
        for (Tasks task : tasksList) {
            if (task.getUserPhoto() != null && !task.getUserPhoto().isEmpty()) {
                task.setUserPhoto(USER_STATIC + task.getUserPhoto());
            }
        }
        return tasksList;
    }

    @Operation(summary = "用户查看任务列表接口", description = "只看未完成，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/tasks/taskCenter/tasks")//查看任务列表
    public List<Tasks> taskCenterTasks(@RequestParam int offSet,@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception{


        TokenUtil.tokenServiceTwo(request,responce);

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskIDAsc(offSet);
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAsc(offSet);
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDesc(offSet);
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAsc(offSet);
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDesc(offSet);
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeAsc(offSet);
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeDesc(offSet);
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeAsc(offSet);
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeDesc(offSet);
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyAsc(offSet);
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyDesc(offSet);
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskIDAsc(offSet);
        }
        // 遍历列表并更新每个Tasks对象的userPhoto字段
        for (Tasks task : tasksList) {
            if (task.getUserPhoto() != null && !task.getUserPhoto().isEmpty()) {
                task.setUserPhoto(USER_STATIC + task.getUserPhoto());
            }
        }
        return tasksList;
    }

    @Operation(summary = "用户搜索任务名，查看任务列表接口", description = "只看未完成搜索任务名，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskName", description = "任务名", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskName")//搜索任务名，查看任务列表
    public List<Tasks> taskCenterTasksForTaskName(@RequestParam String taskName,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskID(taskName,offSet);
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskName(taskName,offSet);
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskName(taskName,offSet);
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEmployer(taskName,offSet);
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEmployer(taskName,offSet);
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskBeginTime(taskName,offSet);
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskBeginTime(taskName,offSet);
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEndTime(taskName,offSet);
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEndTime(taskName,offSet);
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskTimeCoinBounty(taskName,offSet);
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskTimeCoinBounty(taskName,offSet);
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskID(taskName,offSet);
        }
        // 遍历列表并更新每个Tasks对象的userPhoto字段
        for (Tasks task : tasksList) {
            if (task.getUserPhoto() != null && !task.getUserPhoto().isEmpty()) {
                task.setUserPhoto(USER_STATIC + task.getUserPhoto());
            }
        }
        return tasksList;
    }

    @Operation(summary = "用户搜索任务名，查看任务列表接口", description = "只看未完成，搜索雇主，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskEmployer", description = "雇主", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskEmployer")//搜索雇主，查看任务列表
    public List<Tasks> taskCenterTasks(@RequestParam String taskEmployer,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskID(taskEmployer,offSet);
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskName(taskEmployer,offSet);
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskName(taskEmployer,offSet);
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEmployer(taskEmployer,offSet);
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEmployer(taskEmployer,offSet);
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskBeginTime(taskEmployer,offSet);
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskBeginTime(taskEmployer,offSet);
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEndTime(taskEmployer,offSet);
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEndTime(taskEmployer,offSet);
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskTimeCoinBounty(taskEmployer,offSet);
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskTimeCoinBounty(taskEmployer,offSet);
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskID(taskEmployer,offSet);
        }
        // 遍历列表并更新每个Tasks对象的userPhoto字段
        for (Tasks task : tasksList) {
            if (task.getUserPhoto() != null && !task.getUserPhoto().isEmpty()) {
                task.setUserPhoto(USER_STATIC + task.getUserPhoto());
            }
        }
        return tasksList;
    }

    @Operation(summary = "新建任务接口", description = "返回201，\"新建成功\"\"余额不足\"")
    @Parameter(name = "task", description = "taskID,taskName`,`taskEmployer`,`taskAddress`,`taskDetail`,`taskBrief`,`taskBeginTime`,`taskEndTime`,`taskTimeCoinBounty taskEmployerID", example = "对象")
    @PostMapping("/tasks/taskCenter/register")//新建任务
    public String registerTask(@RequestBody Tasks tasks, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int a=usersMapper.selectForUserTimeCoinByUserID(tasks.getTaskEmployerID());
        int b=Integer.parseInt(tasks.getTaskTimeCoinBounty());
        if(a < b){
            return "余额不足";
        }
        tasksMapper.insertRegister(tasks);
        int taskID=tasksMapper.getMaxTaskId();
        tasksMapper.insertRegisterTaskHistory(taskID,usersMapper.selectForUserIDByUserName(tasks.getTaskEmployer()));
        a=a-b;
        usersMapper.updateUserTimeCoinByID(tasks.getTaskEmployerID(),a);
        return "新建成功";
    }

    @Operation(summary = "修改任务接口", description = "返回201，\"余额不足\"\"修改成功\"")
    @Parameter(name = "task", description = "taskID,taskName`,`taskEmployer`,`taskAddress`,`taskDetail`,`taskBrief`,`taskBeginTime`,`taskEndTime`,`taskTimeCoinBounty taskEmployerID", example = "对象")
    @PostMapping("/tasks/taskCenter/changeTask")//修改任务,id查找
    public String taskCenterChangeTask(@RequestBody Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int a=usersMapper.selectForUserTimeCoinByUserID(tasks.getTaskEmployerID());
        int b=tasksMapper.selectTaskTimeCoinBountyByTaskID(tasks.getTaskID());
        int c=Integer.parseInt(tasks.getTaskTimeCoinBounty());
        if(a+b < c){
            return "余额不足";
        }
        tasksMapper.updateTasksByTaskID(tasks);
        a=a+b-c;
        usersMapper.updateUserTimeCoinByID(tasks.getTaskEmployerID(),a);
        return "修改成功";
    }

    @Operation(summary = "删除任务接口", description = "返回201，\"删除成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @Parameter(name = "taskEmployerID", description = "id", example = "123")
    @Parameter(name = "taskTimeCoinBounty", description = "mouney", example = "123")
    @GetMapping("/tasks/taskCenter/deleteTasks")//删除任务,id查找
    public String taskCenterDeleteTask(@RequestParam int taskID,@RequestParam int taskEmployerID,@RequestParam int taskTimeCoinBounty,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int a=usersMapper.selectForUserTimeCoinByUserID(taskEmployerID);
        int b=taskTimeCoinBounty;
        a=a+b;
        tasksMapper.deleteTasksByTaskID(taskID);
        usersMapper.updateUserTimeCoinByID(taskEmployerID,a);
        return "删除成功";
    }

    @Operation(summary = "查看任务详情接口", description = "返回201，task详情")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/findTask")//查看任务详情
    public Object taskCenterFindTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        return tasksMapper.selectTasksByTaskID(taskID);
    }

    @Operation(summary = "审核任务接口", description = "返回201，\"任务已被其他管理员审核通过\"\"审核成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/checkTask")//审核任务
    public String taskCenterCheckTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int a=tasksMapper.selectTaskStatusByTaskID(taskID);
        int b=tasksMapper.selectTaskHistorysStatusByTaskID(taskID);
        if(a==1&&b==1){
            tasksMapper.updateTaskStatusTwoByTaskID(taskID);
            tasksMapper.updateTaskHistoryStatusTwoByTaskID(taskID);
            return "审核成功";
        }
        else{
            return "任务已被其他管理员审核通过";
        }
    }

    @Operation(summary = "审核不通过任务接口", description = "返回201，\"批改完成\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @Parameter(name = "taskStatusRemark", description = "string", example = "string")
    @GetMapping("/tasks/taskCenter/checkTaskNot")//审核不通过任务
    public String taskCenterCheckTaskNot(@RequestParam int taskID,@RequestParam String taskStatusRemark,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        //TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusRemarkByTaskID(taskID,taskStatusRemark);
        return  "批改完成";
    }

    @Operation(summary = "接取任务接口", description = "返回201，\"任务已被其他志愿者接取\"\"接取成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @Parameter(name = "userID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/accessTask")//接取任务
    public String taskCenterAccessTask(@RequestParam int taskID,@RequestParam int userID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);
        int a=tasksMapper.selectTaskStatusByTaskID(taskID);
        int b=tasksMapper.selectTaskHistorysStatusByTaskID(taskID);
        if(a==2&&b==2) {
            tasksMapper.updateTaskStatusThreeByTaskID(taskID);
            tasksMapper.updateTaskHistoryStatusThreeByTaskID(taskID);
            tasksMapper.updateTaskStatusThreeUpdateUserIDByTaskID(taskID, userID);
            return "接取成功";
        }
        else {
            return "任务已被其他志愿者接取";
        }
    }

    @Operation(summary = "志愿者完成任务接口", description = "返回201，\"完成成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/userSuccessTask")//志愿者完成任务
    public String taskCenterUserSuccessTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusFourByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusFourByTaskID(taskID);
        return "完成成功";
    }

    @Operation(summary = "志愿者取消任务接口", description = "返回201，\"取消成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/userRefuseTask")//志愿者取消任务
    public String taskCenterUserRefuseTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusTwoByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusTwoByTaskID(taskID);
        tasksMapper.updateTaskStatusTwoUpdateUserIDByTaskID(taskID);
        return "取消成功";
    }

    @Operation(summary = "雇主完成任务接口", description = "返回201，\"完成成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/taskEmployerSuccessTask")//雇主完成任务
    public String taskCenterTaskEmployerSuccessTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusFiveByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusFiveByTaskID(taskID);
        return "完成成功";
    }

    @Operation(summary = "雇主不通过和管理员完结不通过任务接口", description = "返回201，\"批改成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/successTaskNot")//志愿者完成任务
    public String taskCenterSuccessTaskNot(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusThreeByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusThreeByTaskID(taskID);
        return "批改成功";
    }

    @Operation(summary = "管理员审核任务完结接口", description = "返回201，\"审核成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/adminSuccessTask")//管理员审核任务
    public String taskCenterAdminSuccessTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusSixByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusSixByTaskID(taskID);
        int timeCoinBounty=tasksMapper.selectTaskTimeCoinBountyByTaskID(taskID);
        int userID=taskhistorysMapper.selectUserIDByTaskID(taskID);
        int a=usersMapper.selectForUserTimeCoinByUserID(userID);
        a=a+timeCoinBounty;
        usersMapper.updateUserTimeCoinByID(userID,a);
        return "审核成功";
    }

    @Operation(summary = "插入图片接口", description = "返回201，\"文件为空\"\"文件类型错误\"\"文件上传失败\"文件路径")
    @Parameter(name = "file", description = "文件", example = "图片")
    @PostMapping("/tasks/taskCenter/uploadimage")//上传任务图片
    public String taskCenterUploadImage(@RequestParam MultipartFile file,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        // 设置文件大小限制，例如1MB
        long maxFileSize = 1024 * 1024; // 1MB in bytes
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 检查文件大小
        if (file.getSize() > maxFileSize) {
            return "文件大小超过限制";
        }
        // 检查文件类型
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            return "文件类型错误";
        }

        // 构造图片的文件名，使用taskID作为文件名的前缀
        String fileNamePrefix= UUID.randomUUID().toString().substring(0,8).replaceAll("-","").toLowerCase();// 移除UUID中的连字符并转换为小写 ,取前八位字符
        String fileName =fileNamePrefix;
        // 构造完整的文件路径
        //Path filePath = Paths.get(TASK_PHOTO_PATH + fileName + "." + contentType.substring(contentType.lastIndexOf("/") + 1));

        // 创建目录（如果目录不存在）
        File dir = new File(TASK_PHOTO_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 保存文件
            file.transferTo(new File(TASK_PHOTO_PATH + fileName + "." + contentType.substring(contentType.lastIndexOf("/") + 1)));
            // 构建文件路径字符串
            String taskPhoto = TASK_PHOTO_STATIC_PATH + fileName + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
            taskPhoto = taskPhoto.replace('\\', '/');
            return TASK_STATIC + taskPhoto;
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }

    @Operation(summary = "管理员查看任务发布总量接口", description = "返回201，根据季度月星期长度的数组")
    @Parameter(name = "chooses", description = "int", example = "1星期,2月,3季度")
    @GetMapping("/admin/taskCenter/findCreatedTask")//管理员审核任务
    public List<Tasksmulti> taskCenterFindCreatedTask(@RequestParam int chooses)throws Exception {


//        TokenUtil.tokenServiceTwo(request,responce);

        if(chooses==1){
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskCounts(7);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
            return result;
        } else if (chooses==2) {
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskCounts(30);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,30);
            return result;
        }else if (chooses==3){
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskCounts(90);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,90);
            return result;
        }

        List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskCounts(7);
        List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
        return result;
    }

    @Operation(summary = "管理员查看任务完成总量接口", description = "返回201，根据季度月星期长度的数组")
    @Parameter(name = "chooses", description = "int", example = "1星期,2月,3季度")
    @GetMapping("/admin/taskCenter/findSuccessedTask")//管理员审核任务
    public List<Tasksmulti> taskCenterFindSuccessedTask(@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        //TokenUtil.tokenServiceTwo(request,responce);

        if(chooses==1){
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskSuccessCounts(7);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
            return result;
        } else if (chooses==2) {
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskSuccessCounts(30);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,30);
            return result;
        }else if (chooses==3){
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskSuccessCounts(90);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,90);
            return result;
        }

        List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskSuccessCounts(7);
        List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
        return result;
    }
}
