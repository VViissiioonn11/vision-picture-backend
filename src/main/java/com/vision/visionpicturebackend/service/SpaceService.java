package com.vision.visionpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vision.visionpicturebackend.model.dto.space.SpaceAddRequest;
import com.vision.visionpicturebackend.model.dto.space.SpaceQueryRequest;
import com.vision.visionpicturebackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vision.visionpicturebackend.model.entity.User;
import com.vision.visionpicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 21026
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2025-04-30 15:43:05
*/
public interface SpaceService extends IService<Space> {

    //创建空间
    long addSpace(SpaceAddRequest spaceAddRequest, User logininUser);

    //校验空间,add说明是否为创建时校验
    void validSpace (Space space,Boolean add);

    //获取空间包装类
    SpaceVO getSpaceVO (Space space, HttpServletRequest request);

    //获取空间包装类分页
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);


    //获取查询对象
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    // 根据空间级别，自动填充限额
    void fillSpaceBySpaceLevel(Space space);

}
