package yswy.timesystem.backend.Controller;

import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Ledgers;
import yswy.timesystem.backend.Mapper.LedgersMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hutaosama
 * @date: 2024-03-12 - 03 - 12 - 23:04
 * @Description: com.example.backend.controller
 * @version: 1.0
 */
@RestController
@RequestMapping
public class LedgersController {
    @Resource
    private LedgersMapper ledgersMapper;

    @GetMapping("/ledgers/usercenter/findledgers")//查看账单，用户id查找
    public List<Ledgers> familyCenterFindUsers(@RequestBody Ledgers ledgers, int offSet, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return ledgersMapper.selectLedgersByUserID(ledgers,offSet);
    }
}
