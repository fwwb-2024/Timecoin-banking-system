package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "添加家庭成员接口", description = "user是操作人，对象，返回201，新token")
    @PostMapping("/familyusers/familyCenter/createFamilyuser")//添加家庭成员
    public String registerFamilyuser(@RequestBody Familyusers familyusers,@RequestBody Users users, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familyusersMapper.insertRegister(familyusers);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "删除家庭成员接口", description = "user是操作人，对象，返回201，新token")
    @DeleteMapping("/familyusers/familyCenter/deleteFamilyusers")//删除家庭成员
    public String familyCenterDeleteFamilyusers(@RequestBody Familyusers familyusers,@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familyusersMapper.deleteFamilyusers(familyusers);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "查看所在家庭接口", description = "返回201，一串familyusers对象")
    @GetMapping("/familyusers/familyCenter/findFamilys")//查看所在家庭
    public List<Familyusers> familyCenterFindFamilys(@RequestBody Familyusers familyusers, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByUserID(familyusers);
    }

    @Operation(summary = "查看家庭所有成员接口", description = "返回201，一串familyusers对象")
    @GetMapping("/familyusers/familyCenter/findUsers")//查看家庭所有成员
    public List<Familyusers> familyCenterFindUsers(@RequestBody Familyusers familyusers,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByFamilyID(familyusers);
    }
}
