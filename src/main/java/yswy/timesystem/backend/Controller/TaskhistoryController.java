package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Taskhistorys;
import yswy.timesystem.backend.Entity.Users;
import yswy.timesystem.backend.Mapper.TaskhistorysMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 23:05
 * @Description: com.example.backend.controller
 * @version: 1.0
 */
@RestController
@RequestMapping
public class TaskhistoryController {
    @Resource
    private TaskhistorysMapper taskhistorysMapper;

    private static String USER_STATIC="http://10.195.28.44:9090/";

    @Operation(summary = "志愿者查看任务历史接口", description = "返回201，一串taskhistorys对象")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "offSet", description = "第几页，0,10,20", example = "0")
    @GetMapping("/taskhistory/taskCenter/findAllTaskhistoryUser")//查看任务历史，用户id,查找
    public List<Taskhistorys> taskCenterFindTaskHistoryUser(@RequestParam int userID, @RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        List<Taskhistorys> taskhistorysList =taskhistorysMapper.selectAllTaskHistoryByUserID(userID,offSet);
        // 遍历列表并更新每个Taskhistorys对象的userPhoto字段
        for (Taskhistorys taskhistory : taskhistorysList) {
            if (taskhistory.getUserPhoto() != null && !taskhistory.getUserPhoto().isEmpty()) {
                taskhistory.setUserPhoto(USER_STATIC + taskhistory.getUserPhoto());
            }
        }
        return taskhistorysList;
    }

    @Operation(summary = "雇主查看任务历史接口", description = "返回201，一串taskhistorys对象")
    @Parameter(name = "taskEmployerID", description = "id", example = "123")
    @Parameter(name = "offSet", description = "第几页，0,10,20", example = "0")
    @GetMapping("/taskhistory/taskCenter/findAllTaskhistoryTaskEmployer")//查看任务历史，用户id,查找
    public List<Taskhistorys> taskCenterFindTaskHistoryTaskEmployer(@RequestParam int taskEmployerID, @RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        List<Taskhistorys> taskhistorysList =taskhistorysMapper.selectAllTaskHistoryByTaskEmployerID(taskEmployerID,offSet);
        // 遍历列表并更新每个Taskhistorys对象的userPhoto字段
        for (Taskhistorys taskhistory : taskhistorysList) {
            if (taskhistory.getUserPhoto() != null && !taskhistory.getUserPhoto().isEmpty()) {
                taskhistory.setUserPhoto(USER_STATIC + taskhistory.getUserPhoto());
            }
        }
        return taskhistorysList;
    }

    @Operation(summary = "志愿者查看当前任务接口", description = "返回201，一串taskhistorys对象")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "offSet", description = "第几页，0,10,20", example = "0")
    @GetMapping("/taskhistory/taskCenter/findTaskhistoryUser")//查看当前任务，用户id,查找
    public List<Taskhistorys> taskCenterFindNowTaskHistoryUser(@RequestParam int userID, @RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        List<Taskhistorys> taskhistorysList =taskhistorysMapper.selectTaskHistoryByUserID(userID,offSet);
        // 遍历列表并更新每个Taskhistorys对象的userPhoto字段
        for (Taskhistorys taskhistory : taskhistorysList) {
            if (taskhistory.getUserPhoto() != null && !taskhistory.getUserPhoto().isEmpty()) {
                taskhistory.setUserPhoto(USER_STATIC + taskhistory.getUserPhoto());
            }
        }
        return taskhistorysList;
    }

    @Operation(summary = "雇主查看当前任务接口", description = "返回201，一串taskhistorys对象")
    @Parameter(name = "taskEmployerID", description = "id", example = "123")
    @Parameter(name = "offSet", description = "第几页，0,10,20", example = "0")
    @GetMapping("/taskhistory/taskCenter/findTaskhistoryTaskEmployer")//查看当前任务，用户id,查找
    public List<Taskhistorys> taskCenterFindNowTaskHistoryTaskEmployer(@RequestParam int taskEmployerID, @RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        List<Taskhistorys> taskhistorysList =taskhistorysMapper.selectTaskHistoryByTaskEmployerID(taskEmployerID,offSet);
        // 遍历列表并更新每个Taskhistorys对象的userPhoto字段
        for (Taskhistorys taskhistory : taskhistorysList) {
            if (taskhistory.getUserPhoto() != null && !taskhistory.getUserPhoto().isEmpty()) {
                taskhistory.setUserPhoto(USER_STATIC + taskhistory.getUserPhoto());
            }
        }
        return  taskhistorysList;
    }


}
