package com.vision.visionpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vision.visionpicturebackend.model.dto.picture.PictureQueryRequest;
import com.vision.visionpicturebackend.model.dto.picture.PictureUploadRequest;
import com.vision.visionpicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vision.visionpicturebackend.model.entity.User;
import com.vision.visionpicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author 21026
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-04-03 17:03:00
*/
public interface PictureService extends IService<Picture> {
    //上传图片
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest uploadPictureResult,
                            User loginUser
    );
    //获得图片查询条件
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);
    //获得图片视图
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);
    //获得图片分页视图
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);
    //数据校验
    void validPicture(Picture picture);
}
