package com.haoge.haogepicturebackend.model.dto.space;

import com.haoge.haogepicturebackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 空间查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SpaceQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private Long userId;

    private String spaceName;

    private Integer spaceLevel;

    private static final long serialVersionUID = 1L;
}
