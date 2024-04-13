package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Admins;
import yswy.timesystem.backend.Mapper.AdminsMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 22:57
 * @Description: com.example.backend.controller
 * @version: 1.0
 */
@RestController
@RequestMapping
public class AdminController {

    @Resource
    private AdminsMapper adminsMapper;

    /**
     *
     * @param admins 对象，管理员
     *
     */
    @Operation(summary = "管理员注册接口", description = "由高级管理员注册,token错就返回201，\"注册成功\"\"管理员已存在\"")
    @Parameter(name = "adminName", description = "新用户名", example = "string")
    @Parameter(name = "adminPassword", description = "新密码", example = "string")
    @PostMapping("/admin/register")//管理员注册接口，由高级管理员注册
    public String registerAdmin(@RequestBody Admins admins,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int count=3;
        count=adminsMapper.selectAdminNameSame(admins.getAdminName());//检查是否有相同用户名
        if(count!=0){
            return "管理员已存在";
        }

        adminsMapper.insertRegister(admins);
        return "注册成功";
    }

    @Operation(summary = "管理员登录接口", description = "返回\"该管理员不存在\"\"密码错误\"，id，账号，新token（藏在admin对象里）")
    @Parameter(name = "adminName", description = "用户名", example = "string")
    @Parameter(name = "adminPassword", description = "密码", example = "string")
    @PostMapping("/admin/login/byAdminName")//管理员登录接口
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

    @Operation(summary = "管理员个人中心查看管理员数据接口", description = "返回201，id，账号，权限，新token（藏在admin对象里）")
    @Parameter(name = "adminName", description = "用户名", example = "string")
    @GetMapping("/admin/adminCenter/findAdminData")//管理员个人中心查看管理员数据，通过管理员名获得
    public Object adminCenterFindAdminData(@RequestParam String adminName,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        Admins adminReturn=adminsMapper.selectForAdminsByAdminName(adminName);
        adminReturn.setToken(TokenUtil.tokenServiceOne(adminName));
        return adminReturn;
    }

    @Operation(summary = "管理员修改个人信息接口", description = "返回201，\"用户已存在\"，新token")
    @Parameter(name = "adminName", description = "用户名", example = "string")
    @Parameter(name = "adminID", description = "id", example = "123")
    @PostMapping("/admin/adminCenter/changeAdminData")//管理员修改个人信息，根据管理员id查找
    public String adminCenterChangeAdminData(@RequestBody Admins admins,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        int count=3;
        if(admins.getAdminName().equals(adminsMapper.selectForAdminNameByAdminID(admins.getAdminID()))){
        }
        else {
            count=adminsMapper.selectAdminNameSame(admins.getAdminName());//检查是否有相同用户名
            if(count!=0){
                return "用户已存在";
            }
        }


        adminsMapper.updateAdminsByID(admins);
        return TokenUtil.tokenServiceOne(admins.getAdminName());
    }

    @Operation(summary = "管理员修改密码接口", description = "返回201，\"修改成功\"")
    @Parameter(name = "adminPassword", description = "密码", example = "string")
    @Parameter(name = "adminID", description = "id", example = "123")
    @PostMapping("/admin/adminCenter/changeAdminPassword")//管理员修改密码，根据id查找
    public String adminCenterChangeAdminPassword(@RequestBody Admins admins,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        adminsMapper.updateAdminPasswordByID(admins);
        return "修改成功";
    }

    @Operation(summary = "注销管理员接口", description = "返回201，\"注销成功\"")
    @Parameter(name = "adminID", description = "id", example = "123")
    @GetMapping("/admin/adminCenter/deleteAdmins")//注销管理员，根据被注销的管理员的id查找
    public String adminCenterDeleteAdmins(@RequestParam int adminID,HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        adminsMapper.deleteAdmins(adminID);
        return "注销成功";
    }

    @Operation(summary = "高级管理员查看所有低级管理员数据接口", description = "返回201，一串对象）")
    @Parameter(name = "adminName", description = "高级管理员用户名", example = "string")
    @GetMapping("/admin/adminCenter/findAllAdminData")//高级管理员个人中心查看低级管理员数据，通过管理员名获得
    public List<Admins> adminCenterFindAllAdminData(@RequestParam String adminName, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);


        return adminsMapper.selectForAllAdminsByAdminPreviliege();
    }





}
