package com.example.backend.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

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

    @Resource
    private Network network;

    /**
     * 通过userId查询账户信息
     * @param userId String
     * @return  map : timeCoin-int, name-String, userId-String
     */
    public Map<String, Object> getUserByID(String userId) {
        try {
            byte[] getUsers = contract.evaluateTransaction("getUser", userId);
            String user = new String(getUsers, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSON.parseObject(user); //String转Json对象
            Map<String, Object> map = new HashMap<>();
            map.put("timeCoin", jsonObject.get("money"));
            map.put("name", jsonObject.get("name"));
            map.put("userId", jsonObject.get("userId"));
            return map;
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 添加账户信息(添加前查询，没有已有帐户报错)
     * @param userId String
     * @param userName String
     * @param timeCoin int
     * @return String 区块地址（不需要）
     */
    public String AddUser(String userId,String userName,int timeCoin){
        try {
            byte[] addUsers = contract.createTransaction("addUser")
                    .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER))) //提供背书节点为其背书
                    .submit(userId, userName, timeCoin + "");
            String remark = new String(addUsers, StandardCharsets.UTF_8);
            return remark;
        } catch (ContractException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
