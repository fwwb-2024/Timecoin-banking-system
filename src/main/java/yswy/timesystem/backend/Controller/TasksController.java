package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.multipart.MultipartFile;
import yswy.timesystem.backend.Entity.*;
import yswy.timesystem.backend.Mapper.LedgersMapper;
import yswy.timesystem.backend.Mapper.TaskhistorysMapper;
import yswy.timesystem.backend.Mapper.UsersMapper;
import yswy.timesystem.backend.Service.FabricServiceImpl;
import yswy.timesystem.backend.Util.DailyTaskUtil;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Mapper.TasksMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




@RestController
@RequestMapping
public class TasksController {
    @Resource
    private TasksMapper tasksMapper;
    @Resource
    private TaskhistorysMapper taskhistorysMapper;
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private LedgersMapper ledgersMapper;

    @Resource
    private FabricServiceImpl fabricService;

    private static String TASK_PHOTO_PATH = "/root/static/userphoto/";
    private static String TASK_PHOTO_STATIC_PATH="root/static/userphoto/";
    private static String TASK_STATIC="http://123.249.5.46:9090/";

    private static String USER_STATIC="http://123.249.5.46:9090/";

    @Operation(summary = "管理员查看任务列表接口", description = "返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/tasks/taskCenter/tasksAdmin")//查看任务列表
    public List<Tasks> taskCenterTasksAdmin(@RequestParam int offSet,@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        int taskStatus=1;
        if(chooses>20){
            taskStatus=5;
            chooses=chooses-20;
        }

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskIDAscAdmin(offSet,taskStatus);
                break;
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscAdmin(offSet,taskStatus);
                break;
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescAdmin(offSet,taskStatus);
                break;
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscAdmin(offSet,taskStatus);
                break;
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescAdmin(offSet,taskStatus);
                break;
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeAscAdmin(offSet,taskStatus);
                break;
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeDescAdmin(offSet,taskStatus);
                break;
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeAscAdmin(offSet,taskStatus);
                break;
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeDescAdmin(offSet,taskStatus);
                break;
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyAscAdmin(offSet,taskStatus);
                break;
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyDescAdmin(offSet,taskStatus);
                break;
            case 12:
                tasksList = tasksMapper.selectAbstractTasksByTaskVisitedNumberAscAdmin(offSet,taskStatus);
                break;
            case 13:
                tasksList = tasksMapper.selectAbstractTasksByTaskVisitedNumberDescAdmin(offSet,taskStatus);
                break;
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskIDAscAdmin(offSet,taskStatus);
                break;
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

        int taskStatus=1;
        if(chooses>20){
            taskStatus=5;
            chooses=chooses-20;
        }

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskIDAdmin(taskName,offSet,taskStatus);
                break;
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskNameAdmin(taskName,offSet,taskStatus);
                break;
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskNameAdmin(taskName,offSet,taskStatus);
                break;
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEmployerAdmin(taskName,offSet,taskStatus);
                break;
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEmployerAdmin(taskName,offSet,taskStatus);
                break;
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskBeginTimeAdmin(taskName,offSet,taskStatus);
                break;
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskBeginTimeAdmin(taskName,offSet,taskStatus);
                break;
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEndTimeAdmin(taskName,offSet,taskStatus);
                break;
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEndTimeAdmin(taskName,offSet,taskStatus);
                break;
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskTimeCoinBountyAdmin(taskName,offSet,taskStatus);
                break;
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskTimeCoinBountyAdmin(taskName,offSet,taskStatus);
                break;
            case 12:
                tasksList=tasksMapper.selectAbstractTasksByTaskNameAscTaskVisitedNumberAdmin(taskName,offSet,taskStatus);
                break;
            case 13:
                tasksList=tasksMapper.selectAbstractTasksByTaskNameDescTaskVisitedNumberAdmin(taskName,offSet,taskStatus);
                break;
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskIDAdmin(taskName,offSet,taskStatus);
                break;
        }
        // 遍历列表并更新每个Tasks对象的userPhoto字段
        for (Tasks task : tasksList) {
            if (task.getUserPhoto() != null && !task.getUserPhoto().isEmpty()) {
                task.setUserPhoto(USER_STATIC + task.getUserPhoto());
            }
        }
        return tasksList;
    }

    @Operation(summary = "管理员搜索雇主，查看任务列表接口", description = "搜索雇主，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskEmployer", description = "雇主", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskEmployerAdmin")//搜索雇主，查看任务列表
    public List<Tasks> taskCenterTasksAdmin(@RequestParam String taskEmployer,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        int taskStatus=1;
        if(chooses>20){
            taskStatus=5;
            chooses=chooses-20;
        }

        List<Tasks> tasksList;
        switch (chooses) {
            case 1:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskIDAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskNameAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskNameAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEmployerAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEmployerAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskBeginTimeAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskBeginTimeAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEndTimeAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEndTimeAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskTimeCoinBountyAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskTimeCoinBountyAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 12:
                tasksList=tasksMapper.selectAbstractTasksByTaskEmployerAscTaskVisitedNumberAdmin(taskEmployer,offSet,taskStatus);
                break;
            case 13:
                tasksList=tasksMapper.selectAbstractTasksByTaskEmployerDescTaskVisitedNumberAdmin(taskEmployer,offSet,taskStatus);
                break;
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskIDAdmin(taskEmployer,offSet,taskStatus);
                break;
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
                break;
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAsc(offSet);
                break;
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDesc(offSet);
                break;
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAsc(offSet);
                break;
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDesc(offSet);
                break;
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeAsc(offSet);
                break;
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskBeginTimeDesc(offSet);
                break;
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeAsc(offSet);
                break;
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEndTimeDesc(offSet);
                break;
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyAsc(offSet);
                break;
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskTimeCoinBountyDesc(offSet);
                break;
            case 12:
                tasksList = tasksMapper.selectAbstractTasksByTaskVisitedNumberAsc(offSet);
                break;
            case 13:
                tasksList = tasksMapper.selectAbstractTasksByTaskVisitedNumberDesc(offSet);
                break;
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskIDAsc(offSet);
                break;
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
                break;
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskName(taskName,offSet);
                break;
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskName(taskName,offSet);
                break;
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEmployer(taskName,offSet);
                break;
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEmployer(taskName,offSet);
                break;
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskBeginTime(taskName,offSet);
                break;
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskBeginTime(taskName,offSet);
                break;
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskEndTime(taskName,offSet);
                break;
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskEndTime(taskName,offSet);
                break;
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskTimeCoinBounty(taskName,offSet);
                break;
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskTimeCoinBounty(taskName,offSet);
                break;
            case 12:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskVisitedNumber(taskName,offSet);
                break;
            case 13:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameDescTaskVisitedNumber(taskName,offSet);
                break;
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskNameAscTaskID(taskName,offSet);
                break;
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
                break;
            case 2:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskName(taskEmployer,offSet);
                break;
            case 3:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskName(taskEmployer,offSet);
                break;
            case 4:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEmployer(taskEmployer,offSet);
                break;
            case 5:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEmployer(taskEmployer,offSet);
                break;
            case 6:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskBeginTime(taskEmployer,offSet);
                break;
            case 7:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskBeginTime(taskEmployer,offSet);
                break;
            case 8:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEndTime(taskEmployer,offSet);
                break;
            case 9:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEndTime(taskEmployer,offSet);
                break;
            case 10:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskTimeCoinBounty(taskEmployer,offSet);
                break;
            case 11:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskTimeCoinBounty(taskEmployer,offSet);
                break;
            case 12:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskVisitedNumber(taskEmployer,offSet);
                break;
            case 13:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerDescTaskVisitedNumber(taskEmployer,offSet);
                break;
            default:
                tasksList = tasksMapper.selectAbstractTasksByTaskEmployerAscTaskID(taskEmployer,offSet);
                break;
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
    @Parameter(name = "task", description = "taskEmployerFamilyUserID taskName`,`taskEmployer`,`taskAddress`,`taskDetail`,`taskBrief`,`taskBeginTime`,`taskEndTime`,`taskTimeCoinBounty taskEmployerID taskLable taskPhoto数组", example = "对象")
    @PostMapping("/tasks/taskCenter/register")//新建任务
    public String registerTask(@RequestBody Tasks tasks, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        //TokenUtil.tokenServiceTwo(request,responce);

        int a=usersMapper.selectForUserTimeCoinByUserID(tasks.getTaskEmployerFamilyUserID());
        int b=tasks.getTaskTimeCoinBounty();
        if(a < b){
            return "余额不足";
        }

        if(tasks.getTaskPhoto()==null)
        {}
        else
            tasks.setTaskPhotoPath(String.join(" ",tasks.getTaskPhoto()));
        tasksMapper.insertRegister(tasks);
        int taskID=tasksMapper.getMaxTaskId();
        tasksMapper.insertRegisterTaskHistory(taskID,usersMapper.selectForUserIDByUserName(tasks.getTaskEmployer()));
        a=a-b;
        usersMapper.updateUserTimeCoinByID(tasks.getTaskEmployerFamilyUserID(),a);
        if(tasks.getTaskTimeCoinBounty()<20){
            tasksMapper.updateTaskStatusTwoByTaskID(taskID);
            tasksMapper.updateTaskHistoryStatusTwoByTaskID(taskID);
            tasksMapper.updateTaskStatusRemarkByTaskID(taskID,null);
            Ledgers ledgers=new Ledgers();
            ledgers.setTaskID(taskID);
            ledgers.setUserID(usersMapper.selectForUserIDByUserName(tasksMapper.selectTaskEmployerByTaskID(taskID)));
            ledgers.setLedgerTimeCoin(0-tasksMapper.selectTaskTimeCoinBountyByTaskID(taskID));
            ledgersMapper.insertRegister(ledgers);
        }
        return "新建成功";
    }

    @Operation(summary = "修改任务接口", description = "返回201，\"余额不足\"\"修改成功\"")
    @Parameter(name = "task", description = "taskID,taskName`,`taskEmployer`,`taskAddress`,`taskDetail`,`taskBrief`,`taskBeginTime`,`taskEndTime`,`taskTimeCoinBounty taskEmployerID taskLable taskPhoto数组", example = "对象")
    @PostMapping("/tasks/taskCenter/changeTask")//修改任务,id查找
    public String taskCenterChangeTask(@RequestBody Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int a=usersMapper.selectForUserTimeCoinByUserID(tasks.getTaskEmployerID());
        int b=tasksMapper.selectTaskTimeCoinBountyByTaskID(tasks.getTaskID());
        int c=tasks.getTaskTimeCoinBounty();
        if(a+b < c){
            return "余额不足";
        }
        if(tasks.getTaskPhoto()==null){}
        else
            tasks.setTaskPhotoPath(String.join(" ",tasks.getTaskPhoto()));
        tasksMapper.updateTasksByTaskID(tasks);

        a=a+b-c;
        usersMapper.updateUserTimeCoinByID(tasks.getTaskEmployerID(),a);
        tasksMapper.updateTaskStatusRemarkByTaskID(tasks.getTaskID(),null);
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
        ledgersMapper.deleteLedgersByTaskID(taskID);
        return "删除成功";
    }

    @Operation(summary = "查看任务详情接口", description = "返回201，task详情")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/findTask")//查看任务详情
    public Object taskCenterFindTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);


        Tasks tasks=new Tasks();
        tasks=tasksMapper.selectTasksByTaskID(taskID);
        tasks.setTaskVisitedNumber(1+tasks.getTaskVisitedNumber());
        tasksMapper.updateTaskVisitedNumberByTaskID(taskID,tasks.getTaskVisitedNumber());
        if(tasks.getTaskPhotoPath()==null)
        {}
        else
            tasks.setTaskPhoto(tasks.getTaskPhotoPath().split("\\s+"));
        return tasks;
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
            tasksMapper.updateTaskStatusRemarkByTaskID(taskID,null);
            Ledgers ledgers=new Ledgers();
            ledgers.setTaskID(taskID);
            ledgers.setUserID(usersMapper.selectForUserIDByUserName(tasksMapper.selectTaskEmployerByTaskID(taskID)));
            ledgers.setLedgerTimeCoin(0-tasksMapper.selectTaskTimeCoinBountyByTaskID(taskID));
            ledgersMapper.insertRegister(ledgers);
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

        TokenUtil.tokenServiceTwo(request,responce);

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

    @Operation(summary = "志愿者开始进行任务接口", description = "返回201，\"进行成功\"")
    @Parameter(name = "taskID taskDoingText taskDoingPhoto", description = "对象", example = "对象")
    @PostMapping("/tasks/taskCenter/userBeginTask")//志愿者进行任务
    public String taskCenterUserBeginTask(@RequestBody Tasks tasks, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusRemarkByTaskID(tasks.getTaskID(), "");
        tasksMapper.updateTaskStatusSevenByTaskID(tasks);
        tasksMapper.updateTaskHistoryStatusSevenByTaskID(tasks.getTaskID());
        return "进行成功";
    }

    @Operation(summary = "志愿者完成任务接口", description = "返回201，\"完成成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/userSuccessTask")//志愿者完成任务
    public String taskCenterUserSuccessTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusRemarkByTaskID(taskID,null);
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
        tasksMapper.updateTaskStatusRemarkByTaskID(taskID,null);
        Tasks tasks=new Tasks();
        tasks.setTaskID(taskID);
        tasks.setTaskDoingPhoto("");
        tasks.setTaskDoingText("");
        tasksMapper.updateTaskDoingsByTaskID(tasks);
        return "取消成功";
    }

    @Operation(summary = "雇主完成任务接口", description = "返回201，\"完成成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/taskEmployerSuccessTask")//雇主完成任务
    public String taskCenterTaskEmployerSuccessTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Tasks tasks=new Tasks();
        tasks.setTaskID(taskID);
        tasks.setTaskDoingPhoto("");
        tasks.setTaskDoingText("");
        tasksMapper.updateTaskDoingsByTaskID(tasks);

        tasksMapper.updateTaskStatusFiveByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusFiveByTaskID(taskID);
        return "完成成功";
    }

    @Operation(summary = "雇主不通过和管理员完结不通过任务接口", description = "返回201，\"批改成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @Parameter(name = "taskStatusRemark", description = "string", example = "string")
    @GetMapping("/tasks/taskCenter/successTaskNot")//志愿者完成任务
    public String taskCenterSuccessTaskNot(@RequestParam int taskID,@RequestParam String taskStatusRemark,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTaskStatusRemarkByTaskID(taskID,taskStatusRemark);
        tasksMapper.updateTaskStatusThreeByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusThreeByTaskID(taskID);
        Tasks tasks=new Tasks();
        tasks.setTaskID(taskID);
        tasks.setTaskDoingPhoto("");
        tasks.setTaskDoingText("");
        tasksMapper.updateTaskDoingsByTaskID(tasks);
        return "批改成功";
    }

    @Operation(summary = "管理员审核任务完结接口", description = "返回201，\"审核成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/adminSuccessTask")//管理员审核任务
    public String taskCenterAdminSuccessTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Tasks tasks=new Tasks();
        tasks.setTaskID(taskID);
        tasks.setTaskDoingPhoto("");
        tasks.setTaskDoingText("");
        tasksMapper.updateTaskDoingsByTaskID(tasks);

        tasksMapper.updateTaskStatusSixByTaskID(taskID);
        tasksMapper.updateTaskHistoryStatusSixByTaskID(taskID);
        int timeCoinBounty=tasksMapper.selectTaskTimeCoinBountyByTaskID(taskID);
        int userID=taskhistorysMapper.selectUserIDByTaskID(taskID);
        int a=usersMapper.selectForUserTimeCoinByUserID(userID);
        a=a+timeCoinBounty;
        usersMapper.updateUserTimeCoinByID(userID,a);
        Ledgers ledgers=new Ledgers();
        ledgers.setTaskID(taskID);
        ledgers.setUserID(userID);
        ledgers.setLedgerTimeCoin(timeCoinBounty);
        ledgersMapper.insertRegister(ledgers);
        String s=fabricService.transfer(String.valueOf(usersMapper.selectForUserIDByUserName(tasksMapper.selectTaskEmployerByTaskID(taskID))),String.valueOf(userID),timeCoinBounty);
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
    public List<Tasksmulti> taskCenterFindCreatedTask(@RequestParam int chooses,HttpServletRequest request, HttpServletResponse responce)throws Exception {


        TokenUtil.tokenServiceTwo(request,responce);

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
    @GetMapping("/admin/taskCenter/findSuccessedTask")//
    public List<Tasksmulti> taskCenterFindSuccessedTask(@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

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

    @Operation(summary = "管理员查看时间币流水总量接口", description = "返回201，根据季度月星期长度的数组")
    @Parameter(name = "chooses", description = "int", example = "1星期,2月,3季度")
    @GetMapping("/admin/taskCenter/findTaskTimeCoinBounty")//
    public List<Tasksmulti> taskCenterFindTaskTimeCoinBounty(@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        if(chooses==1){
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskTimeCoinBounty(7);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
            return result;
        } else if (chooses==2) {
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskTimeCoinBounty(30);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,30);
            return result;
        }else if (chooses==3){
            List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskTimeCoinBounty(90);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,90);
            return result;
        }

        List<Tasksmulti> taskCounts=tasksMapper.getRecentTaskTimeCoinBounty(7);
        List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
        return result;
    }

    @Operation(summary = "管理员查看各任务类别总量接口", description = "返回201，类别长度的数组对象")
    @GetMapping("/admin/taskCenter/findTaskLableTaskCounts")//
    public List<TaskLableTaskCounts> taskCenterFindTaskLableTaskCounts(HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);


        List<TaskLableTaskCounts> result = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            TaskLableTaskCounts counts = tasksMapper.getTaskLableTaskCounts(i);
            result.add(counts);
        }
        return result;
    }

    @Operation(summary = "管理员查看任务总量接口", description = "返回201,int")
    @GetMapping("/admin/taskCenter/findAllTaskNumber")//
    public int taskCenterFindAllTaskNumber(HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);


        int result=tasksMapper.getAllTaskNumber();
        return result;
    }

    @Operation(summary = "管理员查看时间币流水总量接口", description = "返回201,int")
    @GetMapping("/admin/taskCenter/findAllTaskTimeCoinNumber")//
    public int taskCenterFindAllTaskTimeCoinNumber(HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);


        int result=tasksMapper.getAllTaskTimeCoinNumber();
        return result;
    }

}
