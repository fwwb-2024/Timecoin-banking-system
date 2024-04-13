package yswy.timesystem.backend.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 11:35
 * @Description: Service:fabric-java-sdk 实例工具包
 * @version: 1.0
 */
@Component
public class FabricServiceImpl {
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
     * 添加账户信息(添加前查询，没有已有帐户报错,有一定延迟1-2s),如果有，则覆盖。
     * @param userId String
     * @param userName String
     * @param timeCoin int
     * @return String 区块地址（不需要），出错直接报错
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

    /**
     * 查询当前用户的账本历史信息
     * @param userId String
     * @return 历史信息数组
     */
    public ArrayList getHistory(String userId) {
        byte[] queryAResultBefore = new byte[0];
        try {
            queryAResultBefore = contract.evaluateTransaction("getHistory", userId);
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }
        String history = new String(queryAResultBefore, StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(history);
        ArrayList<Object> arrayList = new ArrayList<>();
        Map map = (Map) JSON.parse(history);
        Set<Map.Entry<String, String>> ent = map.entrySet();
        for(Map.Entry<String, String> entry: ent){
            String key = entry.getKey();
            String value = entry.getValue();
            arrayList.add(value);
        }
        return arrayList;
    }

    public String queryAll() throws ContractException {
        byte[] queryAResultBefore = contract.evaluateTransaction("queryAll");
        return new String(queryAResultBefore, StandardCharsets.UTF_8);
    }

    /**
     *
     * @param userIdA 转账人Id
     * @param userIdB 收款人Id
     * @param timeCoin 转账金额
     * @return  交易区块地址（不作为前端使用）String,错误直接报错
     */
    public String transfer(String userIdA, String userIdB, int timeCoin) {
        byte[] invokeResult = new byte[0];
        try {
            invokeResult = contract.createTransaction("transfer")
                    .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                    .submit(userIdA,userIdB, String.valueOf(timeCoin));
        } catch (ContractException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String txId = new String(invokeResult, StandardCharsets.UTF_8);
        return txId;
    }
}