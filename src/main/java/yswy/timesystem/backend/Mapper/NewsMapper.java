package yswy.timesystem.backend.Mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import yswy.timesystem.backend.Entity.News;

import java.util.List;

@Mapper
public interface NewsMapper {
    //发布新闻
    @Insert("insert into `news` (`news_title`,`news_author_id`,`news_author_name`,`news_time`,`news_detail`) values(#{newsTitle},#{newsAuthorID},#{newsAuthorName},#{newsTime},#{newsDetail});")
    @Transactional
    void insertRegister(News news);

    //删除新闻,id
    @Delete("delete from `news` where `news_id` = #{newsID};")
    @Transactional
    void deleteNews(int newsID);

    //修改新闻,id
    @Update("update `news` set `news_title` = #{newsTitle},`news_author_id` =#{newsAuthorID},`news_author_name` =#{newsAuthorName},`news_time` =#{newsTime},`news_detail` ={newsDetail} where `admin_id` = #{adminID};")
    @Transactional
    void updateNewsByNewsID(News news);

    //查看新闻详情,id
    @Select("select `news_id` as newsID,`news_title` as newsTitle,`news_author_id` as newsAuthorID,`news_author_name` as newsAuthorName,`news_time` as newsTime,`news_detail` as newsDetail from `news` where `news_id`=#{newsID};")
    @Transactional
    News selectNewsDetailByNewsID(int newsID);

    //查看新闻列表
    @Select("select `news_id` as newsID,`news_title` as newsTitle,`news_author_id` as newsAuthorID,`news_author_name` as newsAuthorName,`news_time` as newsTime,`news_detail` as newsDetail from `news` order by news_id desc limit 10 offset #{offSet};")
    @Transactional
    List<News> selectNewsByNewsIDDesc(int offSet);
}
