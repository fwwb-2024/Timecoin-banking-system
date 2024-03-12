package com.example.backend.controller;

import com.example.backend.Service.FabricServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 12:08
 * @Description: com.example.backend.controller
 * @version: 1.0
 */
@RestController
public class FabricController {
    @Resource
    private FabricServiceImpl fabricService;

    @GetMapping("fabric/get01")
    public void getUserTest(){
        Map<String, Object> userByID = fabricService.getUserByID("1");
        System.out.println(userByID);
    }
}
