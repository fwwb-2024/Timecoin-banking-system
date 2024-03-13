package com.example.backend.Controller;

import com.example.backend.Util.TokenUtil;
import com.example.backend.Entity.Familys;
import com.example.backend.Entity.Users;
import com.example.backend.Mapper.FamilysMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 23:00
 * @Description: com.example.backend.controller
 * @version: 1.0
 */
@RestController
public class FamilysController {

    @Resource
    private FamilysMapper familysMapper;

    @PostMapping("/family/familycenter/createfamily")//创建家庭
    public String registerFamily(@RequestBody Familys familys, Users users, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.insertRegister(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @PostMapping("/family/familycenter/changefamilyname")//修改家庭名称，id查找
    public String familyCenterChangeFamilyName(@RequestBody Familys familys,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateFamilyNameByFamilyID(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @PostMapping("/family/familycenter/changehouseholder")//修改家庭主人，id查找
    public String familyCenterChangeHouseHolder(@RequestBody Familys familys,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.updateHouseHolderByFamilyID(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @DeleteMapping("/family/familycenter/deletefamilys")//删除家庭,根据id
    public String familyCenterDeleteFamilys(@RequestBody Familys familys,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familysMapper.deleteFamilys(familys);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

}
