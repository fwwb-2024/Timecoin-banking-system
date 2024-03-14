package yswy.timesystem.backend.Controller;

import yswy.timesystem.backend.Mapper.UsersMapper;

import yswy.timesystem.backend.Util.TokenUtil;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import yswy.timesystem.backend.Entity.Users;

@RestController
@RequestMapping
public class UserController {
    @Resource
    UsersMapper usersMapper;


//接口

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
