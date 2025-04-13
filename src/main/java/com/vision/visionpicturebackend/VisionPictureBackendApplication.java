package com.vision.visionpicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vision.visionpicturebackend.mapper")
public class VisionPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisionPictureBackendApplication.class, args);
    }

}
