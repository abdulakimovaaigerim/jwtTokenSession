package peaksoft.service;

import org.springframework.data.domain.PageRequest;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequePagination;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SumAllChequeOfDay;

import java.time.LocalDate;

public interface ChequeService {

    SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest);

    ChequePagination getAll(Long userId, PageRequest pageRequest);

    ChequeResponse getChequeById(Long id);

    SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest);

    SimpleResponse deleteChequeById(Long id);

    SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date);

    SimpleResponse avgCheque(Long userId, LocalDate date);

}
