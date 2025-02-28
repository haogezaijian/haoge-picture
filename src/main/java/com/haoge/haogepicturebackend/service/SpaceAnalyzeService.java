package com.haoge.haogepicturebackend.service;

import com.haoge.haogepicturebackend.model.dto.picture.analyze.*;
import com.haoge.haogepicturebackend.model.entity.Space;
import com.haoge.haogepicturebackend.model.entity.User;
import com.haoge.haogepicturebackend.model.vo.space.analysis.SpaceCategoryAnalyzeResponse;
import com.haoge.haogepicturebackend.model.vo.space.analysis.SpaceSizeAnalyzeResponse;
import com.haoge.haogepicturebackend.model.vo.space.analysis.SpaceTagAnalyzeResponse;
import com.haoge.haogepicturebackend.model.vo.space.analysis.SpaceUserAnalyzeResponse;
import com.haoge.haogepicturebackend.model.vo.space.analyze.SpaceUsageAnalyzeResponse;

import java.util.List;

public interface SpaceAnalyzeService {

    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
