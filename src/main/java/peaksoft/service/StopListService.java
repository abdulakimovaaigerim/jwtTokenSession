package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

public interface StopListService {

    SimpleResponse saveStopList(Long menuItemId, StopListRequest stopListRequest);

    List<StopListResponse> getAllStopLists(Long menuItemId);

    StopListResponse findByIdStopList(Long stopListId);

    SimpleResponse updateStopList(Long stopListId, StopListRequest stopListRequest);

    SimpleResponse deleteByIdStopList(Long stopListId);

}
