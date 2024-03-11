package com.example.backend.controller;

import com.example.backend.entity.*;
import com.example.backend.mapper.AdminsMapper;
import com.example.backend.mapper.FamilysMapper;
import com.example.backend.mapper.FamilyusersMapper;
import com.example.backend.mapper.LedgersMapper;
import com.example.backend.mapper.TaskhistorysMapper;
import com.example.backend.mapper.TasksMapper;
import com.example.backend.mapper.UsersMapper;

import com.example.backend.Util.TokenUtil;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping

public class UserController {

    @Resource
    AdminsMapper adminsMapper;
    @Resource
    FamilysMapper familysMapper;
    @Resource
    FamilyusersMapper familyusersMapper;
    @Resource
    LedgersMapper ledgersMapper;
    @Resource
    TaskhistorysMapper taskhistorysMapper;
    @Resource
    TasksMapper tasksMapper;
    @Resource
    UsersMapper usersMapper;


//接口

    @PostMapping("/admin/register")//管理员注册接口，由高级管理员注册
    public String registerAdmin(@RequestBody Admins admins,String adminNameTwo,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int count=3;
        count=adminsMapper.selectAdminNameSame(admins.getAdminName());//检查是否有相同用户名
        if(count!=0){
            return "管理员已存在";
        }

        adminsMapper.insertRegister(admins);
        return TokenUtil.tokenServiceOne(adminNameTwo);
    }

    @PostMapping("/admin/login/byadminname")//管理员登录接口
    public Object loginByAdminName(@RequestBody Admins admins) throws Exception {
        int count=0;
        count=adminsMapper.selectAdminNameSame(admins.getAdminName());//检查是否有相同用户名
        if(count==0){
            return "该管理员不存在";
        }
        count=0;
        count=adminsMapper.selectAdminNameAdminPasswordSame(admins.getAdminName(),admins.getAdminPassword());//检查密码
        if(count==0){
            return  "密码错误";
        }
        Admins adminReturn=new Admins();
        adminReturn.setAdminName(admins.getAdminName());
        adminReturn.setAdminID(adminsMapper.selectForAdminIDByAdminName(adminReturn.getAdminName()));
        adminReturn.setToken(TokenUtil.tokenServiceOne(adminReturn.getAdminName()));
        return adminReturn;
    }

    @GetMapping("/admin/admincenter/findadmindata")//管理员个人中心查看管理员数据，通过管理员名获得
    public Object adminCenterFindAdminData(@RequestBody Admins admins,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Admins adminReturn=adminsMapper.selectForAdminsByAdminName(admins.getAdminName());
        adminReturn.setToken(TokenUtil.tokenServiceOne(adminReturn.getAdminName()));
        return adminReturn;
    }

    @PostMapping("/admin/admincenter/changeadmindata")//管理员修改个人信息，根据管理员id查找
    public String adminCenterChangeAdminData(@RequestBody Admins admins,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int count=3;
        count=adminsMapper.selectAdminNameSame(admins.getAdminName());//检查是否有相同用户名
        if(count!=0){
            return "用户已存在";
        }

        adminsMapper.updateAdminsByID(admins);
        return TokenUtil.tokenServiceOne(admins.getAdminName());
    }

    @PostMapping("/admin/admincenter/changeadminpassword")//管理员修改密码，根据id查找
    public String adminCenterChangeAdminPassword(@RequestBody Admins admins,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        adminsMapper.updateAdminPasswordByID(admins);
        return TokenUtil.tokenServiceOne(admins.getAdminName());
    }

    @DeleteMapping("/admin/admincenter/deleteadmins")//注销管理员，根据被注销的管理员的id查找
    public void adminCenterDeleteAdmins(@RequestBody Admins admins,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        adminsMapper.deleteAdmins(admins);
    }

    @PostMapping("/family/familycenter/createfamily")//创建家庭
    public String registerFamily(@RequestBody Familys familys,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.insertRegister(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @PostMapping("/family/familycenter/changefamilyname")//修改家庭名称，id查找
    public String familyCenterChangeFamilyName(@RequestBody Familys familys,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateFamilyNameByFamilyID(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @PostMapping("/family/familycenter/changehouseholder")//修改家庭主人，id查找
    public String familyCenterChangeHouseHolder(@RequestBody Familys familys,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateHouseHolderByFamilyID(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @DeleteMapping("/family/familycenter/deletefamilys")//删除家庭,根据id
    public String familyCenterDeleteFamilys(@RequestBody Familys familys,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.deleteFamilys(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @PostMapping("/familyusers/familycenter/createfamilyuser")//添加家庭成员
    public String registerFamilyuser(@RequestBody Familyusers familyusers,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familyusersMapper.insertRegister(familyusers);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @DeleteMapping("/familyusers/familycenter/deletefamilyusers")//删除家庭成员
    public String familyCenterDeleteFamilyusers(@RequestBody Familyusers familyusers,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familyusersMapper.deleteFamilyusers(familyusers);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @GetMapping("/familyusers/familycenter/findfamilys")//查看所在家庭
    public List<Familyusers> familyCenterFindFamilys(@RequestBody Familyusers familyusers,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByUserID(familyusers);
    }

    @GetMapping("/familyusers/familycenter/findusers")//查看家庭所有成员
    public List<Familyusers> familyCenterFindUsers(@RequestBody Familyusers familyusers,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByFamilyID(familyusers);
    }

    @GetMapping("/ledgers/usercenter/findledgers")//查看账单，用户id查找
    public List<Ledgers> familyCenterFindUsers(@RequestBody Ledgers ledgers,int offSet,HttpServletRequest request,HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return ledgersMapper.selectLedgersByUserID(ledgers,offSet);
    }

    @GetMapping("/taskhistory/taskcenter/findtaskhisory")//查看任务历史，用户id,查找
    public List<Taskhistorys> taskCenterFindTaskHistory(@RequestBody Users users,int offSet,HttpServletRequest request,HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        return taskhistorysMapper.selectTaskHistoryByUserID(users,offSet);
    }

    @GetMapping("/tasks/taskcenter/tasks")//查看任务列表
    public List<Tasks> taskCenterTasks(@RequestBody int offSet,int chooses,HttpServletRequest request,HttpServletResponse responce)throws Exception{

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
    public String registerTask(@RequestBody Users users,Tasks tasks,HttpServletRequest request,HttpServletResponse responce)throws Exception {

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

    @PostMapping("/user/register")//注册接口
    public String registerUser(@RequestBody Users users){
        int count=3;
        count=usersMapper.selectUserNameSame(users.getUserName());//检查是否有相同用户名
        if(count!=0){
            return "用户已存在";
        }
        count=3;
        count=usersMapper.selectUserPhoneNumberSame(users.getUserPhoneNumber());//检查是否有相同电话号
        if(count!=0){
            return "电话号已被注册";
        }

        usersMapper.insertRegister(users);
        return "注册成功";
    }

    @PostMapping("/user/login/byusername")//账号密码登录
    public Object loginByUserName(@RequestBody Users users) throws Exception {
        int count=0;
        count=usersMapper.selectUserNameSame(users.getUserName());//检查是否有该用户名
        if(count==0){
            return  "该用户不存在";
        }
        count=0;
        count=usersMapper.selectUserNameUserPasswordSame(users.getUserName(),users.getUserPassword());//检查密码
        if(count==0){
            return  "密码错误";
        }
        Users userReturn=new Users();
        userReturn.setUserName(users.getUserName());
        userReturn.setUserID(usersMapper.selectForUserIDByUserName(userReturn.getUserName()));
        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @PostMapping("/user/login/byuserphonenumber")//手机号密码登录
    public Object loginByUserPhoneNumber(@RequestBody Users users) throws Exception {
        int count=0;
        count=usersMapper.selectUserPhoneNumberSame(users.getUserPhoneNumber());//检查是否有该手机号
        if(count==0){
            return  "该电话号码未注册";
        }
        count=0;
        users.setUserName(usersMapper.selectForUserNameByUserPhoneNumberUserPasswordSame(users.getUserPhoneNumber(),users.getUserPassword()));//检查密码
        if(users.getUserName()==null){
            return  "密码错误";
        }
        Users userReturn=new Users();
        userReturn.setUserName(users.getUserName());
        userReturn.setUserID(usersMapper.selectForUserIDByUserName(userReturn.getUserName()));
        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @PostMapping("/user/login/byuseridnumber")//身份证号密码登录
    public Object loginByUserIDNumber(@RequestBody Users users) throws Exception {
        int count=0;
        count=usersMapper.selectUserIDNumberSame(users.getUserIDNumber());//检查是否有该身份证号
        if(count==0){
            return  "该身份证号未注册";
        }
        count=0;
        users.setUserName(usersMapper.selectForUserNameByUserIDNumberUserPasswordSame(users.getUserIDNumber(),users.getUserPassword()));//检查密码
        if(users.getUserName()==null){
            return  "密码错误";
        }
        Users userReturn=new Users();
        userReturn.setUserName(users.getUserName());
        userReturn.setUserID(usersMapper.selectForUserIDByUserName(userReturn.getUserName()));
        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @GetMapping("/user/usercenter/finduserdata")//用户个人中心，查找用户数据,根据用户名查找
    public Object userCenterFindUserData(@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Users userReturn=usersMapper.selectForUsersByUserName(users.getUserName());

        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @PostMapping("/user/usercenter/changeuserdata")//用户个人中心，修改用户数据，根据用户id查找
    public String userCenterChangeUserData(@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int count=3;
        count=usersMapper.selectUserNameSame(users.getUserName());//检查是否有相同用户名
        if(count!=0){
            return "用户已存在";
        }
        count=3;
        count=usersMapper.selectUserPhoneNumberSame(users.getUserPhoneNumber());//检查是否有相同电话号
        if(count!=0){
            return "电话号已被注册";
        }
        count=3;
        count=usersMapper.selectUserIDNumberSame(users.getUserIDNumber());//检查是否有相同身份证号
        if(count!=0){
            return "身份证号已被注册";
        }

        usersMapper.updateUsersByID(users);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @PostMapping("/user/usercenter/changeuserpassworld")//用户修改密码，用户id查找
    public String userCenterChangeUserPassword(@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.updateUserPasswordByID(users);
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @GetMapping("/user/usercenter/findtimecoin")//查看用户时间币余额，通过用户名查看余额
    public Object userCenterFindTimeCoin(@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Users userReturn=usersMapper.selectForUserTimeCoinByUserName(users.getUserName());

        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @DeleteMapping("/user/usercenter/deleteusers")//注销用户，根据用户id
    public void userCenterDeleteUsers(@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.deleteUsers(users);
    }
}
