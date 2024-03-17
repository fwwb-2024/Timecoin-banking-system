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

    @Operation(summary = "查看任务历史接口", description = "返回201，一串taskhistorys对象")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "offSet", description = "第几页，0,10,20", example = "0")
    @GetMapping("/taskhistory/taskCenter/findTaskhistory")//查看任务历史，用户id,查找
    public List<Taskhistorys> taskCenterFindTaskHistory(@RequestParam int userID, @RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        return taskhistorysMapper.selectTaskHistoryByUserID(userID,offSet);
    }
}
