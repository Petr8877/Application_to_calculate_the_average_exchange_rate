package by.fin.module.core.dto;

import java.time.LocalDate;

public record DailyExchangeRateDto(String currency,
								   int curScale,
								   LocalDate date,
								   double curOfficialRate) {
}
