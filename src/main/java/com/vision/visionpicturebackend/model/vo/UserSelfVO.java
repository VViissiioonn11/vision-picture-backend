package com.vision.visionpicturebackend.model.vo;

import lombok.Data;

/**
 * TODO: 用户自己的视图
 * @author vision
 * 创建时间: 2025/4/3 15:05
 */
@Data
public class UserSelfVO {
    /**
     * 账号
     */
    private String userAccount;


    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;
    private static final long serialVersionUID = 1L;

}