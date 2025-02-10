package com.haoge.haogepicturebackend.service;

import com.haoge.haogepicturebackend.model.dto.picture.PictureUpdateRequest;
import com.haoge.haogepicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haoge.haogepicturebackend.model.entity.User;
import com.haoge.haogepicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

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

}
