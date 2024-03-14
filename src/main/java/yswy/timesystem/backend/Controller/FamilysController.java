package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Familys;
import yswy.timesystem.backend.Entity.Users;
import yswy.timesystem.backend.Mapper.FamilysMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 23:00
 * @Description: com.example.backend.controller
 * @version: 1.0
 */
@RestController
@RequestMapping
public class FamilysController {

    @Resource
    private FamilysMapper familysMapper;

    @Operation(summary = "创建家庭接口", description = "user是操作人，对象，返回201，新token")
    @PostMapping("/family/familyCenter/createFamily")//创建家庭
    public String registerFamily(@RequestBody Familys familys,@RequestBody Users users, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.insertRegister(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "修改家庭名称接口", description = "user是操作人，对象，返回201，新token")
    @PostMapping("/family/familyCenter/changeFamilyName")//修改家庭名称，id查找
    public String familyCenterChangeFamilyName(@RequestBody Familys familys,@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateFamilyNameByFamilyID(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "修改家庭主人接口", description = "user是操作人，对象，返回201，新token")
    @PostMapping("/family/familyCenter/changeHouseHolder")//修改家庭主人，id查找
    public String familyCenterChangeHouseHolder(@RequestBody Familys familys,@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateHouseHolderByFamilyID(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @Operation(summary = "删除家庭接口", description = "user是操作人，对象，返回201，新token")
    @DeleteMapping("/family/familyCenter/deleteFamilys")//删除家庭,根据id
    public String familyCenterDeleteFamilys(@RequestBody Familys familys,@RequestBody Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.deleteFamilys(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

}
