package com.vision.visionpicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO: 用户注册请求
 * @author vision
 * 创建时间: 2025/3/30 14:48
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;



}
