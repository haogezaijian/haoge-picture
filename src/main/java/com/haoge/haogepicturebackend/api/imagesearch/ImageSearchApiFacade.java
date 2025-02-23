package com.haoge.haogepicturebackend.api.imagesearch;

import com.haoge.haogepicturebackend.api.imagesearch.model.ImageSearchResult;
import com.haoge.haogepicturebackend.api.imagesearch.sub.GetImageFirstUrlApi;
import com.haoge.haogepicturebackend.api.imagesearch.sub.GetImageListApi;
import com.haoge.haogepicturebackend.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {
    /**
     * 搜索图片
     *
     * @param imageUrl the image url
     * @return the image list
     */
    public static List<ImageSearchResult> searchPicture(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.searchImageUrl(imageUrl);
        System.out.println("imagePageUrl = " + imagePageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        System.out.println("imageFirstUrl = " + imageFirstUrl);
        return GetImageListApi.getImageList(imageFirstUrl);
    }

    public static void main(String[] args) {
        String imageUrl = "https://www.codefather.cn/logo.png";
        List<ImageSearchResult> imageList = searchPicture(imageUrl);
        for (ImageSearchResult image : imageList) {
            System.out.println(image.getThumbUrl());
        }
    }
}
