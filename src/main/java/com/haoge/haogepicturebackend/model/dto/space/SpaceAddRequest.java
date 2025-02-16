package com.haoge.haogepicturebackend.model.dto.space;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增空间请求体
 */
@Data
public class SpaceAddRequest implements Serializable {

    private String spaceName;

    private Integer spaceLevel;

    private static final long serialVersionUID = 1L;
}
