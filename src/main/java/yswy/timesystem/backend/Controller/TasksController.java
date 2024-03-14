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

    @Operation(summary = "查看任务列表接口", description = "返回201，一串tasks对象")
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

    @Operation(summary = "搜索任务名，查看任务列表接口", description = "搜索任务名，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/tasks/taskCenter/tasksForTaskName")//搜索任务名，查看任务列表
    public List<Tasks> taskCenterTasksForTaskName(@RequestBody Tasks tasks,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskID(tasks,offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskName(tasks,offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskName(tasks,offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskEmployer(tasks,offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskEmployer(tasks,offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskBeginTime(tasks,offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskBeginTime(tasks,offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskEndTime(tasks,offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskEndTime(tasks,offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskTimeCoinBounty(tasks,offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskNameDescTaskTimeCoinBounty(tasks,offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskNameAscTaskID(tasks,offSet);
        }
    }

    @Operation(summary = "搜索任务名，查看任务列表接口", description = "搜索雇主，返回201，一串tasks对象")
    @Parameter(name = "chooses", description = "选择哪种排序方式，从1到11，id顺序，用户名顺，用户名逆，雇主顺，雇主逆，开始结束时间悬赏", example = "1")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/tasks/taskCenter/tasksForTaskEmployer")//搜索雇主，查看任务列表
    public List<Tasks> taskCenterTasks(@RequestBody Tasks tasks,@RequestParam int offSet,@RequestParam int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        switch (chooses) {
            case 1:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskID(tasks,offSet);
            case 2:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskName(tasks,offSet);
            case 3:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskName(tasks,offSet);
            case 4:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEmployer(tasks,offSet);
            case 5:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEmployer(tasks,offSet);
            case 6:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskBeginTime(tasks,offSet);
            case 7:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskBeginTime(tasks,offSet);
            case 8:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskEndTime(tasks,offSet);
            case 9:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskEndTime(tasks,offSet);
            case 10:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskTimeCoinBounty(tasks,offSet);
            case 11:
                return tasksMapper.selectAbstractTasksByTaskEmployerDescTaskTimeCoinBounty(tasks,offSet);
            default:
                return tasksMapper.selectAbstractTasksByTaskEmployerAscTaskID(tasks,offSet);
        }
    }

    @Operation(summary = "新建任务接口", description = "返回201，新token")
    @PostMapping("/tasks/taskCenter/register")//新建任务
    public String registerTask(@RequestBody Users users,@RequestBody Tasks tasks, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.insertRegister(tasks);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "修改任务接口", description = "返回201，新token")
    @PostMapping("/tasks/taskCenter/changeTask")//修改任务,id查找
    public String taskCenterChangeTask(@RequestBody Users users,@RequestBody Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTasksByTaskID(tasks);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "删除任务接口", description = "返回201，新token")
    @DeleteMapping("/tasks/taskCenter/deleteTasks")//删除任务,id查找
    public String taskCenterDeleteTask(@RequestBody Users users,@RequestBody Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.deleteTasksByTaskID(tasks);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "查看任务详情接口", description = "返回201，新token（藏在task对象里）")
    @GetMapping("/tasks/taskCenter/findTask")//查看任务详情
    public Object taskCenterFindTask(@RequestBody Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        return tasksMapper.selectTasksByTaskID(tasks);
    }
}
