package com.vision.visionpicturebackend.aop;

import com.vision.visionpicturebackend.annotation.AuthCheck;
import com.vision.visionpicturebackend.exception.BusinessException;
import com.vision.visionpicturebackend.exception.ErrorCode;
import com.vision.visionpicturebackend.model.entity.User;
import com.vision.visionpicturebackend.model.enums.UserRoleEnum;
import com.vision.visionpicturebackend.service.UserService;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole= authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=((ServletRequestAttributes)requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum mustRoleEnum=UserRoleEnum.getEnumByValue(mustRole);
        if(mustRoleEnum==null){
            return joinPoint.proceed();
        }
        UserRoleEnum enumByValue=UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        if(enumByValue==null){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        if(UserRoleEnum.ADMIN.equals(mustRoleEnum)&&! UserRoleEnum.ADMIN.equals(enumByValue)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"权限不足");
        }
        return joinPoint.proceed();
    }

}
