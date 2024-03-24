package yswy.timesystem.backend;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan("yswy.timesystem.backend.Mapper")
public class BackendApplication {
    private static final Logger logger= LogManager.getLogger(BackendApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
