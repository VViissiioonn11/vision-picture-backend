package com.vision.visionpicturebackend.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vision.visionpicturebackend.constant.UserConstant;
import com.vision.visionpicturebackend.exception.BusinessException;
import com.vision.visionpicturebackend.exception.ErrorCode;
import com.vision.visionpicturebackend.model.dto.user.UserAddRequest;
import com.vision.visionpicturebackend.model.dto.user.UserQueryRequest;
import com.vision.visionpicturebackend.model.entity.User;
import com.vision.visionpicturebackend.model.enums.UserRoleEnum;
import com.vision.visionpicturebackend.model.vo.LoginUserVO;
import com.vision.visionpicturebackend.model.vo.UserVO;
import com.vision.visionpicturebackend.service.UserService;
import com.vision.visionpicturebackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 21026
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-03-29 16:09:27
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //校验参数

        if(StrUtil.hasBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<2){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号过短");
        }
        if(userPassword.length()<6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户数输入密码不一致");
        }
        //设置用户账号是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = this.baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }
        //密码加密
        String encryptPassword =getEncryptPassword(userPassword);

        //插入数据到数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("匿名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saveResult = this.save(user);
        if(!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"注册失败，请稍后");
        }
        return user.getId();
    }

    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "vision";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        log.info("用户登录userAccount="+userAccount+",userPassword"+userPassword);
        //校验
        if(StrUtil.hasBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<2){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号过短");
        }
        if(userPassword.length()<6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");
        }
        //加密密码
        String encryptPassword =getEncryptPassword(userPassword);
        //查询是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount).eq("userPassword",encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        //不存在抛异常
        if(user==null){
            log.info("user login fail,userAccount:{}",userAccount);
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码错误");
        }
        //保存用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user);
        return this.getLoginUserVO(user);
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if(user==null){
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user,loginUserVO);
        return loginUserVO;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object userobj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser=(User)userobj;
        if(currentUser==null||currentUser.getId()==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long userId=currentUser.getId();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        currentUser= this.baseMapper.selectOne(queryWrapper);
        if(currentUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        Object userobj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(userobj==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录");
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public UserVO getUserVO(User user) {
        if(user==null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if(userList==null){
            return new ArrayList<UserVO>();
        }

        return userList.stream()
                .map(this::getUserVO)
                .collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if(userQueryRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"查询条件为空");
        }
        Long id = userQueryRequest.getId();
        String userName = userQueryRequest.getUserName();
        String userAccount = userQueryRequest.getUserAccount();
        String userRole = userQueryRequest.getUserRole();
        String userProfile = userQueryRequest.getUserProfile();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotEmpty(id),"id",id);
        queryWrapper.eq(StrUtil.isNotEmpty(userRole),"userRole",userRole);
        queryWrapper.like(StrUtil.isNotEmpty(userName),"userName",userName);
        queryWrapper.like(StrUtil.isNotEmpty(userAccount),"userAccount",userAccount);
        queryWrapper.like(StrUtil.isNotEmpty(userProfile),"userProfile",userProfile);
        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField),sortOrder.equals("assend") ,sortField);
        return queryWrapper;
    }

    @Override
    public Long adminAddUser(UserAddRequest UserAddRequest) {
        User user=new User();
        BeanUtils.copyProperties(UserAddRequest, user);
        final String DEFAULT_PASSWORD="123456";
        String encryptPassword=getEncryptPassword(DEFAULT_PASSWORD);
        user.setUserPassword(encryptPassword);
        Boolean result =save(user);
        if(!result){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return user.getId();
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }


}




