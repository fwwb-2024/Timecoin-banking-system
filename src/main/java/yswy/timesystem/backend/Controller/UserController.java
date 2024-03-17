package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import yswy.timesystem.backend.Mapper.UsersMapper;

import yswy.timesystem.backend.Util.TokenUtil;



import org.springframework.web.bind.annotation.*;
import yswy.timesystem.backend.Entity.Users;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping
public class UserController {
    @Resource
    UsersMapper usersMapper;


//接口

    private static String USER_PHOTO_PATH = "C:\\Users\\Administrator\\Desktop\\fwwb\\clone\\Timecoin-banking-system\\src\\main\\resources\\static\\userphoto\\";
    private static String USER_PHOTO_STATIC_PATH="static\\userphoto\\";
    private static String USER_STATIC="http://10.195.28.44:9090/";

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
        userReturn.setUserPhoto(USER_STATIC + userReturn.getUserPhoto());
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



    @Operation(summary = "用户个人中心，上传或修改用户头像接口", description = "返回201，\"文件为空\"\"文件类型错误\"\"删除已存在的文件时出错\"\"文件上传或更新数据库失败\"userPhoto")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "file", description = "文件", example = "图片")
    @PostMapping("/user/userCenter/changeUserPhoto")//上传或修改用户头像
    public String userCenterChangeUserPhoto(@RequestParam MultipartFile file,@RequestParam int userID,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        if (file.isEmpty()) {
            return "文件为空";
        }
        // 检查文件类型
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            return "文件类型错误";
        }

        // 构造图片的文件名，使用userID作为文件名
        String fileName = String.valueOf(userID);
        // 构造完整的文件路径
        Path filePath = Paths.get(USER_PHOTO_PATH + fileName + "." + contentType.substring(contentType.lastIndexOf("/") + 1));

        // 检查文件是否存在
        if (Files.exists(filePath)) {
            try {
                // 删除已存在的文件
                Files.delete(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return "删除已存在的文件时出错";
            }
        }

        // 创建目录（如果目录不存在）
        File dir = new File(USER_PHOTO_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 保存文件
            file.transferTo(new File(USER_PHOTO_PATH + fileName + "." + contentType.substring(contentType.lastIndexOf("/") + 1)));
            // 构建数据库中的文件路径字符串
            String userPhoto = USER_PHOTO_STATIC_PATH + fileName + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
            userPhoto = userPhoto.replace('\\', '/');
            // 更新数据库中的图片路径
            usersMapper.updateTheUserPhotoByID(userID,userPhoto);
            return USER_STATIC + userPhoto;
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传或更新数据库失败";
        }
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
