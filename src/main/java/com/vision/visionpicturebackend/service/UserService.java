package com.vision.visionpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.visionpicturebackend.annotation.AuthCheck;
import com.vision.visionpicturebackend.constant.UserConstant;
import com.vision.visionpicturebackend.model.dto.user.UserAddRequest;
import com.vision.visionpicturebackend.model.dto.user.UserQueryRequest;
import com.vision.visionpicturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vision.visionpicturebackend.model.vo.LoginUserVO;
import com.vision.visionpicturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 21026
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-03-29 16:09:27
*/
public interface UserService extends IService<User> {


    //用户注册
    long userRegister(String username, String password,String checkPassword);
    //密码加密
    String getEncryptPassword(String userPassword);
    //用户登录
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);
    //获得登录态视图
    LoginUserVO getLoginUserVO(User user);

    //获取当前登录用户
    User getLoginUser(HttpServletRequest request);

    //用户注销
    boolean userLogout(HttpServletRequest request);

    //获得管理员的用户视图
    UserVO getUserVO(User user);

    //获得用户列表
    List<UserVO> getUserVOList(List<User> userList);


    //得到查询mybatis的查询条件
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    //管理员添加用户
    Long adminAddUser(UserAddRequest UserAddRequest);
    //是否为管理员
    boolean isAdmin(User user);

}
