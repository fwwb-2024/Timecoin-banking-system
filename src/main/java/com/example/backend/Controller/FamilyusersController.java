package com.example.backend.Controller;

import com.example.backend.Util.TokenUtil;
import com.example.backend.Entity.Familyusers;
import com.example.backend.Entity.Users;
import com.example.backend.Mapper.FamilyusersMapper;
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

    @PostMapping("/familyusers/familycenter/createfamilyuser")//添加家庭成员
    public String registerFamilyuser(@RequestBody Familyusers familyusers, Users users, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familyusersMapper.insertRegister(familyusers);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @DeleteMapping("/familyusers/familycenter/deletefamilyusers")//删除家庭成员
    public String familyCenterDeleteFamilyusers(@RequestBody Familyusers familyusers,Users users,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        familyusersMapper.deleteFamilyusers(familyusers);
        return  TokenUtil.tokenServiceOne(users.getUserName());
    }

    @GetMapping("/familyusers/familycenter/findfamilys")//查看所在家庭
    public List<Familyusers> familyCenterFindFamilys(@RequestBody Familyusers familyusers, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByUserID(familyusers);
    }

    @GetMapping("/familyusers/familycenter/findusers")//查看家庭所有成员
    public List<Familyusers> familyCenterFindUsers(@RequestBody Familyusers familyusers,HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return familyusersMapper.selectFamilyusersByFamilyID(familyusers);
    }
}
