package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Tasks;
import yswy.timesystem.backend.Entity.Users;
import yswy.timesystem.backend.Mapper.TasksMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Operation(summary = "管理员查看任务列表接口", description = "返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/tasks/taskCenter/tasksAdmin")//查看任务列表
    public List<Tasks> taskCenterTasksAdmin(@RequestParam int offSet,@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception{


        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskIDAscAdmin(offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskNameAscAdmin(offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskNameDescAdmin(offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscAdmin(offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescAdmin(offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskBeginTimeAscAdmin(offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskBeginTimeDescAdmin(offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskEndTimeAscAdmin(offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskEndTimeDescAdmin(offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskTimeCoinBountyAscAdmin(offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskTimeCoinBountyDescAdmin(offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskIDAscAdmin(offSet);
        }
    }

    @Operation(summary = "管理员搜索任务名，查看任务列表接口", description = "搜索任务名，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskName", description = "任务名", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskNameAdmin")//搜索任务名，查看任务列表
    public List<Tasks> taskCenterTasksForTaskNameAdmin(@RequestParam String taskName,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskIDAdmin(taskName,offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskNameAdmin(taskName,offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskNameAdmin(taskName,offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskEmployerAdmin(taskName,offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskEmployerAdmin(taskName,offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskBeginTimeAdmin(taskName,offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskBeginTimeAdmin(taskName,offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskEndTimeAdmin(taskName,offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskEndTimeAdmin(taskName,offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskTimeCoinBountyAdmin(taskName,offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskTimeCoinBountyAdmin(taskName,offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskIDAdmin(taskName,offSet);
        }
    }

    @Operation(summary = "管理员搜索任务名，查看任务列表接口", description = "搜索雇主，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskEmployer", description = "雇主", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskEmployerAdmin")//搜索雇主，查看任务列表
    public List<Tasks> taskCenterTasksAdmin(@RequestParam String taskEmployer,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskIDAdmin(taskEmployer,offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskNameAdmin(taskEmployer,offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskNameAdmin(taskEmployer,offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEmployerAdmin(taskEmployer,offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEmployerAdmin(taskEmployer,offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskBeginTimeAdmin(taskEmployer,offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskBeginTimeAdmin(taskEmployer,offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEndTimeAdmin(taskEmployer,offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEndTimeAdmin(taskEmployer,offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskTimeCoinBountyAdmin(taskEmployer,offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskTimeCoinBountyAdmin(taskEmployer,offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskIDAdmin(taskEmployer,offSet);
        }
    }

    @Operation(summary = "用户查看任务列表接口", description = "只看未完成，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/tasks/taskCenter/tasks")//查看任务列表
    public List<Tasks> taskCenterTasks(@RequestParam int offSet,@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception{


        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskIDAsc(offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskNameAsc(offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskNameDesc(offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskEmployerAsc(offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskEmployerDesc(offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskBeginTimeAsc(offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskBeginTimeDesc(offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskEndTimeAsc(offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskEndTimeDesc(offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskTimeCoinBountyAsc(offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskTimeCoinBountyDesc(offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskIDAsc(offSet);
        }
    }

    @Operation(summary = "用户搜索任务名，查看任务列表接口", description = "只看未完成搜索任务名，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskName", description = "任务名", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskName")//搜索任务名，查看任务列表
    public List<Tasks> taskCenterTasksForTaskName(@RequestParam String taskName,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskID(taskName,offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskName(taskName,offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskName(taskName,offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskEmployer(taskName,offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskEmployer(taskName,offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskBeginTime(taskName,offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskBeginTime(taskName,offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskEndTime(taskName,offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskEndTime(taskName,offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskTimeCoinBounty(taskName,offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskTimeCoinBounty(taskName,offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskID(taskName,offSet);
        }
    }

    @Operation(summary = "用户搜索任务名，查看任务列表接口", description = "只看未完成，搜索雇主，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @Parameter(name = "taskEmployer", description = "雇主", example = "string")
    @GetMapping("/tasks/taskCenter/tasksForTaskEmployer")//搜索雇主，查看任务列表
    public List<Tasks> taskCenterTasks(@RequestParam String taskEmployer,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskID(taskEmployer,offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskName(taskEmployer,offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskName(taskEmployer,offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEmployer(taskEmployer,offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEmployer(taskEmployer,offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskBeginTime(taskEmployer,offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskBeginTime(taskEmployer,offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEndTime(taskEmployer,offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEndTime(taskEmployer,offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskTimeCoinBounty(taskEmployer,offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskTimeCoinBounty(taskEmployer,offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskID(taskEmployer,offSet);
        }
    }

    @Operation(summary = "新建任务接口", description = "返回201，\"新建成功\"")
    @Parameter(name = "task", description = "所有", example = "对象")
    @PostMapping("/tasks/taskCenter/register")//新建任务
    public String registerTask(@RequestBody Tasks tasks, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.insertRegister(tasks);
        return "新建成功";
    }

    @Operation(summary = "修改任务接口", description = "返回201，\"修改成功\"")
    @Parameter(name = "task", description = "所有", example = "对象")
    @PostMapping("/tasks/taskCenter/changeTask")//修改任务,id查找
    public String taskCenterChangeTask(@RequestBody Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTasksByTaskID(tasks);
        return "修改成功";
    }

    @Operation(summary = "删除任务接口", description = "返回201，\"删除成功\"")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/deleteTasks")//删除任务,id查找
    public String taskCenterDeleteTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.deleteTasksByTaskID(taskID);
        return "删除成功";
    }

    @Operation(summary = "查看任务详情接口", description = "返回201，task详情")
    @Parameter(name = "taskID", description = "id", example = "123")
    @GetMapping("/tasks/taskCenter/findTask")//查看任务详情
    public Object taskCenterFindTask(@RequestParam int taskID,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        return tasksMapper.selectTasksByTaskID(taskID);
    }
}
