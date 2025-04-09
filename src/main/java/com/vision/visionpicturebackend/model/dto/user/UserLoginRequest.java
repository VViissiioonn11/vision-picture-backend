package com.vision.visionpicturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO:用户登录请求
 * @author vision
 * 创建时间: 2025/3/30 16:14
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}
