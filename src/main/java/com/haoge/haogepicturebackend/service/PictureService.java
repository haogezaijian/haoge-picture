package com.haoge.haogepicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoge.haogepicturebackend.model.dto.picture.*;
import com.haoge.haogepicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haoge.haogepicturebackend.model.entity.User;
import com.haoge.haogepicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author haoge
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-02-09 21:25:30
*/
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     */
    PictureVO uploadPicture(Object inputSource, PictureUploadRequest pictureUploadRequest, User loginUser);

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

    /**
     * 图片审核
     *
     * @param pictureReviewRequest
     * @param loginUser
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);
    /**
     * 填充审核参数
     *
     * @param picture
     * @param loginUser
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest,
                                 User loginUser);

    /**
     * 清理图片文件
     *
     * @param picture
     */
    void cleanPictureFile(Picture picture);

    /**
     * 校验权限
     *
     * @param loginUser the login user
     * @param picture   the picture
     */
    void checkPictureAuth(User loginUser, Picture picture);

    /**
     * 删除图片
     *
     * @param loginUser the login user
     * @param id        the id
     */
    void deletePicture(User loginUser, Long id);

    /**
     * 编辑图片
     *
     * @param pictureEditRequest the picture edit request
     * @param request            the request
     */
    void editPicture(PictureEditRequest pictureEditRequest, HttpServletRequest request);


    /**
     * 根据颜色搜索图片
     *
     * @param spaceId   the space id
     * @param picColor  the pic color
     * @param loginUser the login user
     * @return the list
     */
    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);


    /**
     * 批量更新
     *
     * @param pictureEditByBatchRequest pictureEditByBatchRequest
     * @param loginUser                 登录的用户
     */
    void batchEditPictureMetadata(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);


}
