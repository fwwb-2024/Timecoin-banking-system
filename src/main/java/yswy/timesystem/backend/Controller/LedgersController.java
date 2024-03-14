package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import yswy.timesystem.backend.Util.TokenUtil;
import yswy.timesystem.backend.Entity.Ledgers;
import yswy.timesystem.backend.Mapper.LedgersMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    @Operation(summary = "查看账单接口", description = "返回201，一串ledgers对象")
    @GetMapping("/ledgers/userCenter/findLedgers")//查看账单，用户id查找
    public List<Ledgers> familyCenterFindUsers(@RequestBody Ledgers ledgers, @RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce) throws Exception{

        TokenUtil.tokenServiceTwo(request,responce);

        return ledgersMapper.selectLedgersByUserID(ledgers,offSet);
    }
}
