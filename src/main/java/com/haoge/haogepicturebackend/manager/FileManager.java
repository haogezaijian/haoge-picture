package com.haoge.haogepicturebackend.manager;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.haoge.haogepicturebackend.common.ResultUtils;
import com.haoge.haogepicturebackend.config.CosClientConfig;
import com.haoge.haogepicturebackend.exception.BusinessException;
import com.haoge.haogepicturebackend.exception.ErrorCode;
import com.haoge.haogepicturebackend.exception.ThrowUtils;
import com.haoge.haogepicturebackend.model.dto.file.UploadPictureResult;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Deprecated
public class FileManager {
    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    @Resource
    private CosManager cosManager;

    /**
     * 上传图片
     * @param multipartFile
     * @param uploadPathPrefix
     * @return
     */
    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
        //校验文件
        validPicture(multipartFile);
        //图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originalFilename = multipartFile.getOriginalFilename();
        String uploadFileName = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid,
                FileUtil.getSuffix(originalFilename));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFileName);
        //上传文件
        File file = null;
        try {
            file = File.createTempFile(uploadPath, null);
            multipartFile.transferTo(file);
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            //获取图片信息对象
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            String format = imageInfo.getFormat();
            int picWidth = imageInfo.getWidth();
            int picHeight = imageInfo.getHeight();
            double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
            //封装返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            uploadPictureResult.setUrl(cosClientConfig.getHost()+"/"+uploadPath);
            uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setPicWidth(picWidth);
            uploadPictureResult.setPicHeight(picHeight);
            uploadPictureResult.setPicScale(picScale);
            uploadPictureResult.setPicFormat(imageInfo.getFormat());
            //返回
            return uploadPictureResult;
        } catch (Exception e) {
            log.error("图片上传到对象存储失败" , e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            deleteTempFile(file);
        }
    }

    private static void deleteTempFile(File file) {
        if (file != null) {
            boolean delete = file.delete();
            if (!delete) {
                log.error("file delete error, filepath = {} ", file.getAbsolutePath());
            }
        }
    }

    /**
     * 校验文件
     * @param multipartFile
     */
    private void validPicture(MultipartFile multipartFile) {
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "上传文件为空");
        //校验文件大小
        long fileSize = multipartFile.getSize();
        final long maxSize = 1024 * 1024 ;
        ThrowUtils.throwIf(fileSize > maxSize * 2, ErrorCode.PARAMS_ERROR, "文件大小不能超过2M");
        //校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        //允许上传的文件后缀集合
        final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpg", "jpeg", "png", "webp");
        ThrowUtils.throwIf(!ALLOW_FORMAT_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "不支持的文件格式");
    }

}
