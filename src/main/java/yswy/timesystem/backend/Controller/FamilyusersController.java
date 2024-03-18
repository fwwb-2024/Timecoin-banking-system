package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import yswy.timesystem.backend.Mapper.UsersMapper;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Familyusers;
import yswy.timesystem.backend.Entity.Users;
import yswy.timesystem.backend.Mapper.FamilyusersMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 23:02
 * @Description: com.example.backend.controller
 * @version: 1.0
 */
@RestController
@RequestMapping
public class FamilyusersController {

    @Resource
    private FamilyusersMapper familyusersMapper;

    @Resource
    private UsersMapper usersMapper;

    @Operation(summary = "添加家庭成员接口", description = "，返回201，\"该用户不存在\"\"用户已在家庭中\"\"添加成功\"")
    @Parameter(name = "userName", description = "用户名", example = "string")
    @Parameter(name = "familyID", description = "id", example = "123")
    @PostMapping("/familyusers/familyCenter/createFamilyuser")//添加家庭成员
    public String registerFamilyuser(@RequestBody Familyusers familyusers, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        try{
            familyusers.setUserID(usersMapper.selectForUserIDByUserName(familyusers.getUserName()));
        }catch (Exception e){
            return "该用户不存在";
        }

        int count=3;
        count=familyusersMapper.selectFamilyIDUserIDSame(familyusers.getFamilyID(),familyusers.getUserID());
        if(count!=0){
            return "用户已在家庭中";
        }
        else{

            familyusersMapper.insertRegister(familyusers);
            return "添加成功";
        }

    }

    @Operation(summary = "删除家庭成员接口", description = "，返回201，\"删除成功\"")
    @Parameter(name = "userID", description = "id", example = "123")
    @Parameter(name = "familyID", description = "id", example = "123")
    @GetMapping("/familyusers/familyCenter/deleteFamilyusers")//删除家庭成员
    public String familyCenterDeleteFamilyusers(@RequestParam int familyID,@RequestParam int userID,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        familyusersMapper.deleteFamilyusers(familyID,userID);
        return  "删除成功";
    }

    @Operation(summary = "查看所在家庭接口", description = "返回201，一串familyusers对象")
    @Parameter(name = "userID", description = "id", example = "123")
    @GetMapping("/familyusers/familyCenter/findFamilys")//查看所在家庭
    public List<Familyusers> familyCenterFindFamilys(@RequestParam int userID, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByUserID(userID);
    }

    @Operation(summary = "查看家庭所有成员接口", description = "返回201，一串familyusers对象")
    @Parameter(name = "familyID", description = "id", example = "123")
    @GetMapping("/familyusers/familyCenter/findUsers")//查看家庭所有成员
    public List<Familyusers> familyCenterFindUsers(@RequestParam int familyID,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByFamilyID(familyID);
    }
}
