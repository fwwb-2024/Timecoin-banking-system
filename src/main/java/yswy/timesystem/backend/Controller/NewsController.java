package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.units.qual.N;
import org.springframework.web.bind.annotation.*;
import yswy.timesystem.backend.Entity.News;
import yswy.timesystem.backend.Mapper.NewsMapper;
import yswy.timesystem.backend.Util.TokenUtil;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping
public class NewsController {
    @Resource
    NewsMapper newsMapper;


    @Operation(summary = "发布新闻接口", description = "token错就返回201，\"发布成功\"")
    @Parameter(name = "newsTitle`,`newsAuthorID`,`newsAuthorName`,`newsTime`,`newsDetail newsPhoto", description = "对象", example = "对象")
    @PostMapping("/new/register")//发布新闻接口
    public String registerNews(@RequestBody News news, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        newsMapper.insertRegister(news);
        return "发布成功";
    }

    @Operation(summary = "删除新闻接口", description = "token错就返回201，\"删除成功\"")
    @Parameter(name = "newsID", description = "int", example = "int")
    @GetMapping ("/new/deleteNews")//删除新闻接口
    public String deleteNews(@RequestParam int newsID, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        newsMapper.deleteNews(newsID);
        return "删除成功";
    }

    @Operation(summary = "修改新闻接口", description = "token错就返回201，\"修改成功\"")
    @Parameter(name = "newsID newsTitle`,`newsAuthorID`,`newsAuthorName`,`newsTime`,`newsDetail newsPhoto", description = "对象", example = "对象")
    @PostMapping("/new/changeNews")//修改新闻接口
    public String changeNews(@RequestBody News news, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        newsMapper.updateNewsByNewsID(news);

        return "修改成功";
    }

    @Operation(summary = "查看新闻详情接口", description = "token错就返回201，对象")
    @Parameter(name = "newsID", description = "对象", example = "对象")
    @GetMapping("/new/findNewsDetail")//查看新闻详情接口
    public Object findNewsDetail(@RequestParam int newID, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        News news=newsMapper.selectNewsDetailByNewsID(newID);
        return news;
    }

    @Operation(summary = "查看新闻列表接口", description = "token错就返回201，对象list")
    @Parameter(name = "offSet", description = "int", example = "int")
    @GetMapping("/new/findNewsList")//查看新闻列表接口
    public List<News> findNewsList(@RequestParam int offSet, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        List<News> newsList=newsMapper.selectNewsByNewsIDDesc(offSet);
        return newsList;
    }

}
