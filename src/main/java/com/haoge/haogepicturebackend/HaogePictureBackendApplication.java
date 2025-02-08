package com.haoge.haogepicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.haoge.haogepicturebackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class HaogePictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaogePictureBackendApplication.class, args);
    }

}
