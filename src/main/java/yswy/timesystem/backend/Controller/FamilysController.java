package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import yswy.timesystem.backend.Entity.Familyusers;
import yswy.timesystem.backend.Mapper.FamilyusersMapper;
import yswy.timesystem.backend.Mapper.UsersMapper;
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

    @Resource
    private FamilyusersMapper familyusersMapper;

    @Resource
    private UsersMapper usersMapper;

    @Operation(summary = "创建家庭接口", description = "，返回201，家庭id")
    @Parameter(name = "houseHolder", description = "id", example = "123")
    @Parameter(name = "familyName", description = "家庭名", example = "string")
    @Parameter(name = "houseHolderName", description = "家主名", example = "string")
    @PostMapping("/family/familyCenter/createFamily")//创建家庭
    public int registerFamily(@RequestBody Familys familys, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.insertRegister(familys);
        int familyID=familysMapper.selectFamilyIDByFamilyNameAndHouseHolder(familys);

        Familyusers familyusers=new Familyusers();
        familyusers.setUserName(usersMapper.selectForUserNameByUserID(familys.getHouseHolder()));
        familyusers.setFamilyID(familyID);
        familyusers.setUserID(familys.getHouseHolder());
        familyusersMapper.insertRegister(familyusers);

        return  familyID;
    }

    @Operation(summary = "修改家庭名称接口", description = "，返回201，\"修改成功\"")
    @Parameter(name = "familyID", description = "id", example = "123")
    @Parameter(name = "familyName", description = "家庭名", example = "string")
    @PostMapping("/family/familyCenter/changeFamilyName")//修改家庭名称，id查找
    public String familyCenterChangeFamilyName(@RequestBody Familys familys,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateFamilyNameByFamilyID(familys);
        return  "修改成功";
    }

    @Operation(summary = "修改家庭主人接口", description = "，返回201，\"修改成功\"")
    @Parameter(name = "familyID", description = "id", example = "123")
    @Parameter(name = "houseHolder", description = "家庭主人", example = "123")
    @Parameter(name = "houseHolderName", description = "家主名", example = "string")
    @PostMapping("/family/familyCenter/changeHouseHolder")//修改家庭主人，id查找
    public String familyCenterChangeHouseHolder(@RequestBody Familys familys,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateHouseHolderByFamilyID(familys);
        return  "修改成功";
    }

    @Operation(summary = "删除家庭接口", description = "，返回201，\"删除成功\"")
    @Parameter(name = "familyID", description = "id", example = "123")
    @GetMapping("/family/familyCenter/deleteFamilys")//删除家庭,根据id
    public String familyCenterDeleteFamilys(@RequestParam int familyID,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        //TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.deleteFamilys(familyID);
        return  "删除成功";
    }

}
