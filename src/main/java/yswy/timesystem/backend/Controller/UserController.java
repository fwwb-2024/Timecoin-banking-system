package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import yswy.timesystem.backend.Mapper.UsersMapper;

import yswy.timesystem.backend.Util.TokenUtil;



import org.springframework.web.bind.annotation.*;
import yswy.timesystem.backend.Entity.Users;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class UserController {
    @Resource
    UsersMapper usersMapper;


//接口

    @Operation(summary = "注册接口", description = "返回\"用户已存在\"\"电话号已被注册\"\"注册成功\"")
    @Parameter(name = "userName", description = "账号", example = "string")
    @Parameter(name = "userPassword", description = "密码", example = "string")
    @Parameter(name = "userPhoneNumber", description = "手机号", example = "string")
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

    @Operation(summary = "账号密码登录接口", description = "返回用户id，账号名，新token（藏在user对象里），\"该用户不存在\"，\"密码错误\"")
    @Parameter(name = "userName", description = "账号", example = "string")
    @Parameter(name = "userPassword", description = "密码", example = "string")
    @PostMapping("/user/login/byUserName")//账号密码登录
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

    @Operation(summary = "手机号密码登录接口", description = "返回，账号，用户id，新token（藏在user对象里），\"该电话号码未注册\"，\"密码错误\"")
    @Parameter(name = "userPassword", description = "密码", example = "string")
    @Parameter(name = "userPhoneNumber", description = "手机号", example = "string")
    @PostMapping("/user/login/byUserPhoneNumber")//手机号密码登录
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
        userReturn.setUserName(usersMapper.selectForUserNameByUserPhoneNumber(users.getUserPhoneNumber()));
        userReturn.setUserID(usersMapper.selectForUserIDByUserName(userReturn.getUserName()));
        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @Operation(summary = "身份证号密码登录接口", description = "返回，账号，id，新token（藏在user对象里），\"该身份证号未注册\"，\"密码错误\"")
    @Parameter(name = "userPassword", description = "密码", example = "string")
    @Parameter(name = "userIDNumber", description = "身份证号", example = "string")
    @PostMapping("/user/login/byUserIDNumber")//身份证号密码登录
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
        userReturn.setUserName(usersMapper.selectForUserNameByUserIDNumber(users.getUserIDNumber()));
        userReturn.setUserID(usersMapper.selectForUserIDByUserName(userReturn.getUserName()));
        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @Operation(summary = "用户个人中心，查找用户数据接口", description = "返回201，用户个人信息，不包含密码、时间币余额、用户状态反馈，有新token（藏在user对象里）")
    @Parameter(name = "userName", description = "账号", example = "string")
    @GetMapping("/user/userCenter/findUserData")//用户个人中心，查找用户数据,根据用户名查找
    public Object userCenterFindUserData(@RequestParam String userName, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Users userReturn = usersMapper.selectForUsersByUserName(userName);

        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @Operation(summary = "用户个人中心，修改用户数据接口", description = "返回201，新token，\"用户已存在\"，\"电话号已被注册\"，\"身份证号已被注册\"")
    @Parameter(name = "user", description = "修改用户个人信息，不包括密码、时间币余额、用户状态反馈", example = "对象")
    @PostMapping("/user/userCenter/changeUserData")//用户个人中心，修改用户数据，根据用户id查找
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

    @GetMapping("/user/userCenter/changeUserPhoto")//修改用户头像
    public void userCenterChangeUserPhoto(@RequestParam int photo){


        System.out.println("ok");
    }

    @Operation(summary = "用户修改密码接口", description = "返回201，新token")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "userPassword", description = "密码", example = "string")
    @Parameter(name = "userName", description = "账号", example = "string")
    @PostMapping("/user/userCenter/changeUserPassworld")//用户修改密码，用户id查找
    public String userCenterChangeUserPassword(@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.updateUserPasswordByID(users.getUserID(),users.getUserPassword());
        return TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "查看用户时间币余额接口", description = "返回201，时间币余额，新token（藏在user对象里）")
    @Parameter(name = "userName", description = "账号", example = "string")
    @GetMapping("/user/userCenter/findTimeCoin")//查看用户时间币余额，通过用户名查看余额
    public Object userCenterFindTimeCoin(@RequestParam String userName,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Users userReturn=new Users();
        userReturn.setUserTimeCoin(usersMapper.selectForUserTimeCoinByUserName(userName));
        userReturn.setUserName(userName);

        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @Operation(summary = "注销用户接口", description = "返回201")
    @Parameter(name = "userID", description = "id", example = "123")
    @GetMapping("/user/userCenter/deleteUsers")//注销用户，根据用户id
    public void userCenterDeleteUsers(@RequestParam int userID,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.deleteUsers(userID);
    }
}
