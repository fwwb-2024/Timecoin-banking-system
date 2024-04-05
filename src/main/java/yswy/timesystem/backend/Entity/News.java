package yswy.timesystem.backend.Entity;


import lombok.Data;

@Data
public class News {
    private Integer newsID;
    private String newsTitle;
    private Integer newsAuthorID;
    private String newsAuthorName;
    private String newsTime;
    private String newsDetail;
    private String newsPhoto;
}
