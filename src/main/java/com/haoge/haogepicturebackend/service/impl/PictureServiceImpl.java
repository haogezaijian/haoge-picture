package com.haoge.haogepicturebackend.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haoge.haogepicturebackend.exception.ErrorCode;
import com.haoge.haogepicturebackend.exception.ThrowUtils;
import com.haoge.haogepicturebackend.manager.FileManager;
import com.haoge.haogepicturebackend.model.dto.file.UploadPictureResult;
import com.haoge.haogepicturebackend.model.dto.picture.PictureUpdateRequest;
import com.haoge.haogepicturebackend.model.entity.Picture;
import com.haoge.haogepicturebackend.model.entity.User;
import com.haoge.haogepicturebackend.model.vo.PictureVO;
import com.haoge.haogepicturebackend.service.PictureService;
import com.haoge.haogepicturebackend.mapper.PictureMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
* @author haoge
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-02-09 21:25:30
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

    @Resource
    private FileManager fileManager;

    @Override
    public PictureVO uploadPicture(MultipartFile multipartFile, PictureUpdateRequest pictureUpdateRequest, User loginUser) {
        //校验参数
        ThrowUtils.throwIf(loginUser==null,ErrorCode.NO_AUTH_ERROR);
        //判断是新增还是删除
        Long pictureId = null;
        if (pictureUpdateRequest != null) {
            pictureId = pictureUpdateRequest.getId();
        }
        if (pictureId != null) {
            boolean exists = this.lambdaQuery().eq(Picture::getId, pictureId).exists();
            ThrowUtils.throwIf(!exists, ErrorCode.NOT_FOUND_ERROR, "图片不存在");
        }
        //上传图片
        String uploadPathPrefix = String.format("public/%s", loginUser.getId());
        UploadPictureResult uploadPictureResult = fileManager.uploadPicture(multipartFile, uploadPathPrefix);
        //操作数据库
        Picture picture = new Picture();
        picture.setUrl(uploadPictureResult.getUrl());
        picture.setName(uploadPictureResult.getPicName());
        picture.setPicSize(uploadPictureResult.getPicSize());
        picture.setPicWidth(uploadPictureResult.getPicWidth());
        picture.setPicHeight(uploadPictureResult.getPicHeight());
        picture.setPicScale(uploadPictureResult.getPicScale());
        picture.setPicFormat(uploadPictureResult.getPicFormat());
        picture.setUserId(loginUser.getId());
        if (pictureId != null) {
            picture.setId(pictureId);
            picture.setUpdateTime(new Date());
        }
        boolean res = this.saveOrUpdate(picture);
        ThrowUtils.throwIf(!res, ErrorCode.OPERATION_ERROR, "图片上传失败，数据库操作失败");
        return PictureVO.objToVo(picture);
    }
}




