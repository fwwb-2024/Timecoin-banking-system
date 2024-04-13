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
import yswy.timesystem.backend.Entity.Tasks;
import yswy.timesystem.backend.Entity.Tasksmulti;
import yswy.timesystem.backend.Mapper.UsersMapper;

import yswy.timesystem.backend.Service.FabricServiceImpl;
import yswy.timesystem.backend.Util.DailyTaskUtil;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {
    @Resource
    private UsersMapper usersMapper;

    @Resource
    private FabricServiceImpl fabricService;


//接口

    //private static String USER_PHOTO_PATH = "C:\\Users\\Administrator\\Desktop\\fwwb\\clone\\Timecoin-banking-system\\src\\main\\resources\\static\\userphoto\\";
    //private static String USER_PHOTO_STATIC_PATH="static\\userphoto\\";
    //private static String USER_STATIC="http://10.195.28.44:9090/";
    private static String USER_PHOTO_PATH = "/root/static/userphoto/";
    private static String USER_PHOTO_STATIC_PATH="root/static/userphoto/";
    private static String USER_STATIC="http://123.249.5.46:9090/";

    @Operation(summary = "用户区块链刷新接口", description = "返回\"注册成功\"")
    @Parameter(name = "userID", description = "int", example = "123")
    @Parameter(name = "userName", description = "String", example = "string")
    @Parameter(name = "userTimeCoin", description = "int", example = "123")
    @PostMapping("/user/flashUser")//
    public String flashUser(@RequestBody Users users){
        int userID=users.getUserID();
        String s=fabricService.AddUser(String.valueOf(userID),users.getUserName(),users.getUserTimeCoin());
        return "注册成功";
    }

    @Operation(summary = "用户注册接口", description = "返回\"用户已存在\"\"电话号已被注册\"\"注册成功\"")
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
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期时间格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        // 格式化当前日期为指定格式的字符串
        String formattedDate = currentDate.format(formatter);
        users.setUserRegistTime(formattedDate);

        usersMapper.insertRegister(users);
        int userID=usersMapper.selectForUserIDByUserName(users.getUserName());//此处代码应该在审核成正常用户后
        String s=fabricService.AddUser(String.valueOf(userID),users.getUserName(),500);
        usersMapper.updateUserTimeCoinByID(userID,500);//
        return "注册成功";
    }

    @Operation(summary = "用户账号密码登录接口", description = "返回用户id，账号名，新token（藏在user对象里），\"该用户不存在\"，\"密码错误\"")
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
        userReturn.setUserStatus(usersMapper.selectForUserStatusByUserID(userReturn.getUserID()));
        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @Operation(summary = "用户手机号密码登录接口", description = "返回，账号，用户id，新token（藏在user对象里），\"该电话号码未注册\"，\"密码错误\"")
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
        userReturn.setUserStatus(usersMapper.selectForUserStatusByUserID(userReturn.getUserID()));
        userReturn.setToken(TokenUtil.tokenServiceOne(userReturn.getUserName()));
        return userReturn;
    }

    @Operation(summary = "用户身份证号密码登录接口", description = "返回，账号，id，新token（藏在user对象里），\"该身份证号未注册\"，\"密码错误\"")
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
        userReturn.setUserStatus(usersMapper.selectForUserStatusByUserID(userReturn.getUserID()));
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
        if(users.getUserName().equals(usersMapper.selectForUserNameByUserID(users.getUserID()))){
        }
        else{
            count=usersMapper.selectUserNameSame(users.getUserName());//检查是否有相同用户名
            if(count!=0){
                return "用户已存在";
            }
        }
        if(users.getUserPhoneNumber().equals(usersMapper.selectForUserPhoneNumberByUserID(users.getUserID()))){
        }
        else{
            count=3;
            count=usersMapper.selectUserPhoneNumberSame(users.getUserPhoneNumber());//检查是否有相同电话号
            if(count!=0){
                return "电话号已被注册";
            }
        }

        if(users.getUserIDNumber()!=null){
            if(users.getUserIDNumber().equals(usersMapper.selectForUserIDNumberByUserID(users.getUserID()))){
            }
            else{
                count=3;
                count=usersMapper.selectUserIDNumberSame(users.getUserIDNumber());//检查是否有相同身份证号
                if(count!=0) {
                    return "身份证号已被注册";
                }
            }
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

        // 设置文件大小限制，例如1MB
        long maxFileSize = 10240 * 1024; // 1MB in bytes

        if (file.isEmpty()) {
            return "文件为空";
        }
        // 检查文件大小
        if (file.getSize() > maxFileSize) {
            return "文件大小超过限制";
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

        //Map<String,Object> fabricUser=fabricService.getUserByID(userReturn.getUserID().toString());
        //int timeCoinFabric=(Integer) fabricUser.get("timeCoin");
        //userReturn.setUserTimeCoin(timeCoinFabric);
        //usersMapper.updateUserTimeCoinByID(userReturn.getUserID(),timeCoinFabric);


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

    @Operation(summary = "用户申请，从游客到未审核，或从未通过到未审核接口", description = "返回201，\"申请成功\"")
    @Parameter(name = "userID", description = "id", example = "123")
    @GetMapping("/user/userCenter/userAccessUserNormal")//
    public String userCenterUserAccessUserNormal(@RequestParam int userID){

        usersMapper.updateUserStatusOneByUserID(userID,"");
        return "申请成功";
    }


    @Operation(summary = "管理员审核用户，从未审核到正常,从冻结到正常接口", description = "返回201，\"审核成功\"")
    @Parameter(name = "userID", description = "id", example = "123")
    @GetMapping("/user/userCenter/adminAccessUserNormal")//
    public String userCenterAdminAccessUserNormal(@RequestParam int userID,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.updateUserStatusTwoByUserID(userID,"");
        return "审核成功";
    }

    @Operation(summary = "管理员审核用户不通过，从未审核到未通过接口", description = "返回201，\"审核成功\"")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "userStatusRemark", description = "String", example = "String")
    @GetMapping("/user/userCenter/adminNotAccessUserNormal")//
    public String userCenterAdminNotAccessUserNormal(@RequestParam int userID,@RequestParam String userStatusRemark,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.updateUserStatusThreeByUserID(userID,userStatusRemark);
        return "审核成功";
    }

    @Operation(summary = "管理员将用户设为冻结接口", description = "返回201，\"冻结成功\"")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "userStatusRemark", description = "String", example = "String")
    @GetMapping("/user/userCenter/adminCloseUserNormal")//
    public String userCenterAdminCloseUserNormal(@RequestParam int userID,@RequestParam String userStatusRemark,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.updateUserStatusFourByUserID(userID,userStatusRemark);
        return "冻结成功";
    }

    @Operation(summary = "管理员将用户设为官方号接口", description = "返回201，\"审核成功\"")
    @Parameter(name = "userID", description = "id", example = "123")
    @GetMapping("/user/userCenter/adminAccessUserSpecial")//
    public String userCenterAdminAccessUserSpecial(@RequestParam int userID,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        usersMapper.updateUserStatusFiveByUserID(userID,"");
        return "冻结成功";
    }

    @Operation(summary = "管理员查看未审核用户接口", description = "返回201，一串tasks对象")
    @Parameter(name = "offSet", description = "从第几页开始查找，从0开始，0,10,20,30.。。", example = "0")
    @GetMapping("/admins/userCenter/findUnAccessUserAdmin")//
    public List<Users> userCenterFindUnAccessUserAdmin(@RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce)throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        int userStatus=1;


        List<Users> usersList;

        usersList = usersMapper.selectUnAccessByUserIDAscAdmin(offSet,userStatus);


        return usersList;
    }


    @Operation(summary = "管理员查看用户注册总量接口", description = "返回201，根据季度月星期长度的数组")
    @Parameter(name = "chooses", description = "int", example = "1星期,2月,3季度")
    @GetMapping("/admin/userCenter/findRecentRegisterUsers")//
    public List<Tasksmulti> userCenterFindRecentRegisterUsers(@RequestParam int chooses, HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        if(chooses==1){
            List<Tasksmulti> taskCounts=usersMapper.getRecentRegisterUsers(7);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
            return result;
        } else if (chooses==2) {
            List<Tasksmulti> taskCounts=usersMapper.getRecentRegisterUsers(30);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,30);
            return result;
        }else if (chooses==3){
            List<Tasksmulti> taskCounts=usersMapper.getRecentRegisterUsers(90);
            List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,90);
            return result;
        }

        List<Tasksmulti> taskCounts=usersMapper.getRecentRegisterUsers(7);
        List<Tasksmulti> result= DailyTaskUtil.convertToArray(taskCounts,7);
        return result;
    }

    @Operation(summary = "管理员查看用户总量接口", description = "返回201，counts")
    @GetMapping("/admin/userCenter/findAllUserNumber")//
    public int userCenterFindAllUserNumber(HttpServletRequest request, HttpServletResponse responce)throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int result=usersMapper.getAllUserNumber();

        return result;
    }
}
