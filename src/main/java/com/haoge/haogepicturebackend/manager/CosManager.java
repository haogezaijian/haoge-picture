package com.haoge.haogepicturebackend.manager;

import com.haoge.haogepicturebackend.config.CosClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class CosManager {
    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;


    /**
     * 上传文件
     * @param key
     * @param file
     * @return
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest request = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        return cosClient.putObject(request);
    }

    /**
     * 获取文件
     * @param key
     * @return
     */
    public COSObject getObject(String key) {
        return cosClient.getObject(cosClientConfig.getBucket(), key);
    }

    /**
     *
     * @param key
     * @param file
     * @return
     */
    public PutObjectResult putPictureObject(String key, File file){
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        //对图片进行处理
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);

        putObjectRequest.setPicOperations(picOperations);
        return cosClient.putObject(putObjectRequest);
    }

}
