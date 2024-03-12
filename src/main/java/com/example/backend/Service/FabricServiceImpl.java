package com.example.backend.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 11:35
 * @Description: com.example.backend.Service
 * @version: 1.0
 */
@Component
public class FabricServiceImpl {    //fabric-java-sdk 实例工具包
    @Resource
    private Contract contract;

    public Map<String, Object> getUserByID(String UserId) {
        try {
            byte[] getUsers = contract.evaluateTransaction("getUser", UserId);
            String user = new String(getUsers, StandardCharsets.UTF_8);
            System.out.println(user);
            JSONObject jsonObject = JSON.parseObject(user); //String转Json对象
            Map<String, Object> map = new HashMap<>();
            map.put("money", jsonObject.get("money"));
            map.put("name", jsonObject.get("name"));
            map.put("userId", jsonObject.get("userId"));
            return map;
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }

    }
}
