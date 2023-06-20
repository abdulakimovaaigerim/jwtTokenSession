package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.StopList;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public SimpleResponse saveStopList(Long menuItemId, StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                new NotFoundException("MenuItem with id: " + menuItemId + " is not found!"));

        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        if (stopList.getDate().equals(stopListRequest.date())) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("StopList for this day already exists!")
                    .build();
        }
        stopList.setMenuItem(menuItem);
        menuItem.setStopList(stopList);
        stopListRepository.save(stopList);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with id: " + stopList.getId() + " is saved!"))
                .build();
    }

    @Override
    public List<StopListResponse> getAllStopLists(Long menuItemId) {
        return stopListRepository.findAllByMenuItemId(menuItemId);
    }

    @Override
    public StopListResponse findByIdStopList(Long stopListId) {
        return stopListRepository.getStopListById(stopListId).orElseThrow(() ->
                new NotFoundException("StopList with: " + stopListId + " is not found!"));
    }

    @Override
    public SimpleResponse updateStopList(Long stopListId, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(stopListId).orElseThrow(() ->
                new NotFoundException("StopList with id: " + stopListId + " is not found!"));

        if (stopList.getDate().equals(stopListRequest.date())) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("StopList for this day already exists!")
                    .build();
        }

        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        stopListRepository.save(stopList);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with id: " + stopList.getId() + " is updated!"))
                .build();
    }

    @Override
    public SimpleResponse deleteByIdStopList(Long stopListId) {
        if (!stopListRepository.existsById(stopListId)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(String.format("StopList with id: " + stopListId + " doesn't exists!"))
                    .build();
        }
        StopList stopList = stopListRepository.findById(stopListId).orElseThrow(() ->
                new NotFoundException("StopList with id: " + stopListId + " doesn't exists!"));
        stopListRepository.delete(stopList);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with id: " + stopListId + " is deleted!"))
                .build();
    }
}
