package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequePagination;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SumAllChequeOfDay;
import peaksoft.entities.Cheque;
import peaksoft.entities.MenuItem;
import peaksoft.entities.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;


    @Override
    public SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems = new ArrayList<>();
        Cheque cheque = new Cheque();
        cheque.setCreateAt(LocalDate.now());
        for (Long aLong : chequeRequest.menuItemsId()) {
            MenuItem menuItem = menuItemRepository.findById(aLong).orElseThrow(() ->
                    new NotFoundException("MenuItem with id: " + aLong + " is not found!"));
            menuItems.add(menuItem);
        }
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with id: " + userId + " is not found!"));
        cheque.setUser(user);

        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }

        chequeRepository.save(cheque);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your check been accepted!")
                .build();
    }

    @Override
    public ChequePagination getAll(Long userId, PageRequest pageRequest) {
        Page<ChequeResponse> chequesPage = chequeRepository.getAllChequeByUserId(userId, pageRequest);
        List<ChequeResponse> cheques = chequesPage.getContent();
        for (ChequeResponse c : cheques) {
            BigDecimal total = c.getAveragePrice()
                    .multiply(new BigDecimal(c.getService()))
                    .divide(new BigDecimal(100))
                    .add(c.getAveragePrice());
            c.setGrandTotal(total);
            c.setMeals(chequeRepository.getMenuItems(c.getId()));
        }
        return ChequePagination.builder()
                .cheques(cheques)
                .currentPage(chequesPage.getPageable().getPageNumber() + 1)
                .totalPages(chequesPage.getTotalPages())
                .build();
    }

    @Override
    public ChequeResponse getChequeById(Long id) {
        ChequeResponse cheque = chequeRepository.getChequeById(id).orElseThrow(() ->
                new NotFoundException("Cheque with id: " + id + " is not found!"));

        cheque.setMeals(chequeRepository.getMenuItems(id));
        BigDecimal total = cheque.getAveragePrice()
                .multiply(new BigDecimal(cheque.getService()))
                .divide(new BigDecimal(100)).add(cheque.getAveragePrice());
        cheque.setGrandTotal(total);

        return cheque;
    }


    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems;
        Cheque cheque = chequeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cheque with id: " + id + " is not found!"));
        menuItems = chequeRequest.menuItemsId().stream().map(mealId -> menuItemRepository.findById(mealId).orElseThrow(
                () -> new NotFoundException("Meal with id: " + mealId + " is not found!"))).collect(Collectors.toList());

        for (MenuItem menuItem : cheque.getMenuItems()) {
            menuItem.getCheques().remove(cheque);
        }
        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your check has been updated!")
                .build();
    }

    @Override
    public SimpleResponse deleteChequeById(Long id) {
        if (!chequeRepository.existsById(id)) {
            throw new NotFoundException("Cheque with id: " + id + " doesn't exists!");
        }
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cheque with id: " + id + " doesn't exists!"));
        cheque.getMenuItems().forEach(menuItem -> menuItem.getCheques().remove(cheque));
        chequeRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Cheque with id: " + id + " is deleted!")
                .build();
    }

    @Override
    public SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date) {
        List<Cheque> cheques = chequeRepository.findAll();
        SumAllChequeOfDay sumAllChequeOfDay = new SumAllChequeOfDay();
        Long count = 0L;
        BigDecimal totalSum = new BigDecimal(0);
        int ser = 1;
        for (Cheque cheque : cheques) {
            if (cheque.getUser().getId().equals(userId) && cheque.getCreateAt().equals(date)) {
                sumAllChequeOfDay.setWaiter(cheque.getUser().getFirstName() + " " + cheque.getUser().getLastName());
                sumAllChequeOfDay.setDate(cheque.getCreateAt());
                count++;
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    totalSum = totalSum.add(menuItem.getPrice());
                    ser = menuItem.getRestaurant().getService();
                }
            }

        }
        BigDecimal service = totalSum.multiply(new BigDecimal(ser)).divide(new BigDecimal(100));
        sumAllChequeOfDay.setCounterOfCheque(count);
        sumAllChequeOfDay.setTotalSumma(totalSum.add(service));
        return sumAllChequeOfDay;
    }


    @Override
    public SimpleResponse avgCheque(Long userId, LocalDate date) {
        int total = 0;
        int count = 0;
        int ser = 1;
        for (Cheque cheque : chequeRepository.findAll()) {
            if (cheque.getCreateAt().equals(date)) {
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    total += menuItem.getPrice().intValue();
                    ser = menuItem.getRestaurant().getService();
                }
                count++;
            }
        }
        total += total * ser / 100;
        int avg = total / count;
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Average restaurant cheque: " + avg)
                .build();
    }
}
