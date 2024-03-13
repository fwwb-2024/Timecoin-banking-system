package com.example.backend.Controller;

import com.example.backend.Util.TokenUtil;
import com.example.backend.Entity.Tasks;
import com.example.backend.Entity.Users;
import com.example.backend.Mapper.TasksMapper;
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

    @GetMapping("/tasks/taskcenter/tasks")//查看任务列表
    public List<Tasks> taskCenterTasks(@RequestBody int offSet, int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception{


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

    @GetMapping("/tasks/taskcenter/tasksfortaskname")//搜索任务名，查看任务列表
    public List<Tasks> taskCenterTasksForTaskName(@RequestBody Tasks tasks,int offSet,int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

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

    @GetMapping("/tasks/taskcenter/tasksfortaskemployer")//搜索雇主，查看任务列表
    public List<Tasks> taskCenterTasks(@RequestBody Tasks tasks,int offSet,int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

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

    @PostMapping("/tasks/taskcenter/register")//新建任务
    public String registerTask(@RequestBody Users users, Tasks tasks, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.insertRegister(tasks);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @PostMapping("/tasks/taskcenter/changetask")//修改任务,id查找
    public String taskCenterChangeTask(@RequestBody Users users,Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.updateTasksByTaskID(tasks);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @DeleteMapping("/tasks/taskcenter/deletetasks")//删除任务,id查找
    public String taskCenterDeleteTask(@RequestBody Users users,Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        tasksMapper.deleteTasksByTaskID(tasks);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @GetMapping("/tasks/taskcenter/findtask")//查看任务详情
    public Object taskCenterFindTask(@RequestBody Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        return tasksMapper.selectTasksByTaskID(tasks);
    }
}
