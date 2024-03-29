package yswy.timesystem.backend.Controller;

import yswy.timesystem.backend.Service.FabricServiceImpl;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 12:08
 * @Description: 测试用例，不作为实际使用
 * @version: 1.0
 */
@RestController
@RequestMapping
public class FabricController {
    @Resource
    private FabricServiceImpl fabricService;

    @GetMapping("fabric/get01")
    public String getUserTest(){
        Map<String, Object> userByID = fabricService.getUserByID("1");
        return userByID.toString();
    }

    @GetMapping("fabric/add01")
    public void addUserTest(){
        Map<String, Object> userByID = fabricService.getUserByID("4");
        if (!userByID.isEmpty()){
            System.out.println("error! 已经存在的账户！");
            return;
        }
        String s = fabricService.AddUser("4", "sjp", 1000);
        System.out.println(s);
    }
    @GetMapping("/getHistory")
    public String getHistory(String userId) throws ContractException {
        ArrayList history = fabricService.getHistory("1");
        System.out.println(history);
        return "ok";
    }

    @GetMapping("fabric/transfer")
    public void transferTest(){
        String s = fabricService.transfer("1", "4", 10);
        System.out.println(s);
    }
}
