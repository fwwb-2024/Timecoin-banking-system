package yswy.timesystem.backend.Controller;

import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Admins;
import yswy.timesystem.backend.Mapper.AdminsMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @PostMapping("/admin/register")//管理员注册接口，由高级管理员注册
    public String registerAdmin(@RequestBody Admins admins, String adminNameTwo, HttpServletRequest request, HttpServletResponse responce) throws Exception {

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
}
