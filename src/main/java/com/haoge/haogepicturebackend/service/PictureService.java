package com.haoge.haogepicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoge.haogepicturebackend.model.dto.picture.PictureQueryRequest;
import com.haoge.haogepicturebackend.model.dto.picture.PictureUpdateRequest;
import com.haoge.haogepicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haoge.haogepicturebackend.model.entity.User;
import com.haoge.haogepicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author haoge
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-02-09 21:25:30
*/
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     */
    PictureVO uploadPicture(MultipartFile multipartFile, PictureUpdateRequest pictureUpdateRequest, User loginUser);

    /**
     * 获取查询对象
     *
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片包装类（单条）
     *
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     *
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片
     *
     * @param picture
     */
    void validPicture(Picture picture);
}
