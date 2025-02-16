package com.haoge.haogepicturebackend.model.dto.space;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑空间请求参数
 */
@Data
public class SpaceEditRequest implements Serializable {
    private Long id;

    private String spaceName;

    private static final long serialVersionUID = 1L;
}
