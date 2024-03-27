package yswy.timesystem.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.units.qual.N;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @Parameter(name = "news_title`,`news_author_id`,`news_author_name`,`news_time`,`news_detail", description = "对象", example = "对象")
    @PostMapping("/new/register")//发布新闻接口
    public String registerNews(@RequestBody News news, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        newsMapper.insertRegister(news);
        return "发布成功";
    }

    @Operation(summary = "删除新闻接口", description = "token错就返回201，\"删除成功\"")
    @Parameter(name = "news_id", description = "int", example = "int")
    @PostMapping("/new/deleteNews")//删除新闻接口
    public String deleteNews(@RequestBody int newsID, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        newsMapper.deleteNews(newsID);
        return "删除成功";
    }

    @Operation(summary = "修改新闻接口", description = "token错就返回201，\"修改成功\"")
    @Parameter(name = "news_title`,`news_author_id`,`news_author_name`,`news_time`,`news_detail", description = "对象", example = "对象")
    @PostMapping("/new/changeNews")//删除新闻接口
    public String changeNews(@RequestBody News news, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        newsMapper.updateNewsByNewsID(news);
        return "修改成功";
    }

    @Operation(summary = "查看新闻详情接口", description = "token错就返回201，对象")
    @Parameter(name = "news_id", description = "对象", example = "对象")
    @PostMapping("/new/findNewsDetail")//查看新闻详情接口
    public Object findNewsDetail(@RequestBody int newID, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        News news=newsMapper.selectNewsDetailByNewsID(newID);
        return news;
    }

    @Operation(summary = "查看新闻列表接口", description = "token错就返回201，对象list")
    @Parameter(name = "offSet", description = "int", example = "int")
    @PostMapping("/new/findNewsList")//查看新闻列表接口
    public List<News> findNewsList(@RequestBody int offSet, HttpServletRequest request, HttpServletResponse responce) throws Exception {

        TokenUtil.tokenServiceTwo(request,responce);

        List<News> newsList=newsMapper.selectNewsByNewsIDDesc(offSet);
        return newsList;
    }

}
