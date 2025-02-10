package com.haoge.haogepicturebackend.controller;

import com.haoge.haogepicturebackend.annotation.AuthCheck;
import com.haoge.haogepicturebackend.common.BaseResponse;
import com.haoge.haogepicturebackend.common.ResultUtils;
import com.haoge.haogepicturebackend.constant.UserConstant;
import com.haoge.haogepicturebackend.model.dto.picture.PictureUpdateRequest;
import com.haoge.haogepicturebackend.model.entity.User;
import com.haoge.haogepicturebackend.model.vo.PictureVO;
import com.haoge.haogepicturebackend.service.PictureService;
import com.haoge.haogepicturebackend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    /**
     * 上传图片
     */
    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PictureVO> uploadPicture(
            @RequestPart("file") MultipartFile multipartFile,
            PictureUpdateRequest pictureUpdateRequest,
            HttpServletRequest request
    ){
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(pictureService.uploadPicture(multipartFile, pictureUpdateRequest, loginUser));
    }
}
