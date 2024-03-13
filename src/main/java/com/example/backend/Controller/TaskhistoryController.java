package com.example.backend.Controller;

import com.example.backend.Util.TokenUtil;
import com.example.backend.Entity.Taskhistorys;
import com.example.backend.Entity.Users;
import com.example.backend.Mapper.TaskhistorysMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/taskhistory/taskcenter/findtaskhisory")//查看任务历史，用户id,查找
    public List<Taskhistorys> taskCenterFindTaskHistory(@RequestBody Users users, int offSet, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        return taskhistorysMapper.selectTaskHistoryByUserID(users,offSet);
    }
}
