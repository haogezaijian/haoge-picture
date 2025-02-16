package com.haoge.haogepicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoge.haogepicturebackend.model.dto.space.SpaceAddRequest;
import com.haoge.haogepicturebackend.model.dto.space.SpaceQueryRequest;
import com.haoge.haogepicturebackend.model.entity.Picture;
import com.haoge.haogepicturebackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haoge.haogepicturebackend.model.entity.User;
import com.haoge.haogepicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Space service.
 */
public interface SpaceService extends IService<Space> {

    /**
     * Add space long.
     *
     * @param spaceAddRequest the space add request
     * @param loginUser       the login user
     * @return the long
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);


    /**
     * Valid space.
     *
     * @param space the space
     * @param add   the add
     */
    void validSpace(Space space,Boolean add);

    /**
     * Gets space vo.
     *
     * @param space   the space
     * @param request the request
     * @return the space vo
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * Gets space vo page.
     *
     * @param spacePage the space page
     * @param request   the request
     * @return the space vo page
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * Gets query wrapper.
     *
     * @param spaceQueryRequest the space query request
     * @return the query wrapper
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);


    /**
     * Fill space by space level.
     *
     * @param space the space
     */
    void fillSpaceBySpaceLevel(Space space);


}
