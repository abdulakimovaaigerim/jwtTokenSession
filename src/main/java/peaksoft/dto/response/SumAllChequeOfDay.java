package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SumAllChequeOfDay {

    private String waiter;
    private LocalDate date;
    private Long counterOfCheque;
    private BigDecimal totalSumma;
}