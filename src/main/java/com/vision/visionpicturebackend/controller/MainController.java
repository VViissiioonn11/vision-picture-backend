package com.vision.visionpicturebackend.controller;

import com.vision.visionpicturebackend.common.BaseResponse;
import com.vision.visionpicturebackend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public BaseResponse<String> health() {
        System.out.println("ok");
        return ResultUtils.success("ok");
    }
}
