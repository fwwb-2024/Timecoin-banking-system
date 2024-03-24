package yswy.timesystem.backend.Util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurerUtil implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:C:/Users/Administrator/Desktop/fwwb/clone/Timecoin-banking-system/src/main/resources/static/userphoto");
    }

}
