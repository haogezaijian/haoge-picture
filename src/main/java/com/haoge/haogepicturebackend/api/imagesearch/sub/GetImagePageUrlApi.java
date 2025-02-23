package com.haoge.haogepicturebackend.api.imagesearch.sub;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.haoge.haogepicturebackend.exception.BusinessException;
import com.haoge.haogepicturebackend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取以图搜图页面地址
 */
@Slf4j
public class GetImagePageUrlApi {

    /**
     * 调用百度识图接口，传入图片 URL，返回识别结果的 URL
     *
     * @param imageUrl 图片的 URL 地址
     * @return 识别结果的 URL
     */
    public static String searchImageUrl(String imageUrl) {
        if (StrUtil.isBlank(imageUrl)) {
            throw new IllegalArgumentException("图片 URL 不能为空");
        }

        // 定义百度识图上传接口地址
        String url = "https://graph.baidu.com/upload?uptime="+System.currentTimeMillis();

        // 构造表单数据
        String boundary = "----WebKitFormBoundaryVIa3lCG44JVN3vF1";
        String formData = buildFormData(imageUrl, boundary);

        // 发送 POST 请求
        HttpResponse response = HttpRequest.post(url)
                .header("accept", "*/*")
                .header("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("acs-token", "1740198714733_1740213146090_ukNtl6HQWg2vQGgDDxD5EReQScoLalx5dguuqvAsKRpX3fqeAcdCSnkRBTjcFh1OS80PZUqW7YWuxeSepO8h9UZu/27rjNcoF0u0sjZgCwAufQMtojBALPm30d5UEziJVStyQqOWqtSA0wF2rfNSzKWoHKwPaqgD26+MNkXeTUaoTeLSDHH49rHWg5Ba9DJNtO9fxsE/x35kg1bb6kE01w8dEGVZi5jr2Ib+2zTmL2Fr0kMKUOMKiXPbRJsf0HORI56xnkC2oQ7txNNOxq6SdXU5dXOdoknBq56RT8vuEH5UJnLR2fwdsr/tgmoZL+YE7u5RXFBgddnp/IMrmlah6E+DWs4Wj2bija1n8f+dQ/wrueQkwsrsRCup3fwV7bc5+89H8DPBtww5SaaO2KFfQU1x1s3Lz7nRny2dAut/ccETPIQtmRUmWGzWR/UoaPa2Xu3A8Wu9nnlUnePRUqqd3G+9h7IcFeoX3Oztjn4OpQQ=")
                .header("content-type", "multipart/form-data; boundary=" + boundary)
                .header("origin", "https://graph.baidu.com")
                .header("referer", "https://graph.baidu.com/pcpage/index?tpl_from=pc")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0")
                .body(formData)
                .timeout(10000) // 设置超时时间
                .execute();

        // 检查响应状态码
        if (response.isOk()) {
            String body = response.body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if ("0".equals(jsonObject.getStr("status"))) {
                JSONObject data = jsonObject.getJSONObject("data");
                return URLUtil.decode(data.getStr("url"), StandardCharsets.UTF_8); // 返回识别结果的 URL
            } else {
                throw new RuntimeException("百度识图接口返回错误：" + jsonObject.getStr("msg"));
            }
        } else {
            throw new RuntimeException("请求失败，HTTP 状态码：" + response.getStatus());
        }
    }

    /**
     * 构造 multipart/form-data 格式的表单数据
     *
     * @param imageUrl 图片 URL
     * @param boundary 分隔符
     * @return 表单数据字符串
     */
    private static String buildFormData(String imageUrl, String boundary) {
        StringBuilder formData = new StringBuilder();
        formData.append("--").append(boundary).append("\r\n");
        formData.append("Content-Disposition: form-data; name=\"image\"\r\n\r\n");
        formData.append(imageUrl).append("\r\n");

        formData.append("--").append(boundary).append("\r\n");
        formData.append("Content-Disposition: form-data; name=\"tn\"\r\n\r\n");
        formData.append("pc").append("\r\n");

        formData.append("--").append(boundary).append("\r\n");
        formData.append("Content-Disposition: form-data; name=\"from\"\r\n\r\n");
        formData.append("pc").append("\r\n");

        formData.append("--").append(boundary).append("\r\n");
        formData.append("Content-Disposition: form-data; name=\"image_source\"\r\n\r\n");
        formData.append("PC_UPLOAD_URL").append("\r\n");

        formData.append("--").append(boundary).append("\r\n");
        formData.append("Content-Disposition: form-data; name=\"sdkParams\"\r\n\r\n");
        formData.append("{\"data\":\"54da5f89dad57d9867c585be76b813da3f3a50b6079f373a2508a30cda2ae4b4cc74c20d53071eb6e9d4032a47c2b5aedb4d8fc5e5ebb9379b49f40e2c4a770ff339b7ecd15964ac37bf06bc48680c29\",\"key_id\":\"23\",\"sign\":\"53c7cd1b\"}").append("\r\n");

        formData.append("--").append(boundary).append("--\r\n");
        return formData.toString();
    }

    public static void main(String[] args) {
        try {
            String imageUrl = "https://www.codefather.cn/logo.png"; // 示例图片 URL
            String resultUrl = searchImageUrl(imageUrl);
            System.out.println("识别结果 URL: " + resultUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


