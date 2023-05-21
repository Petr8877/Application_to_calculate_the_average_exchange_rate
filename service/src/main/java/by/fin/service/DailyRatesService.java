package by.fin.service;

import by.fin.module.core.dto.DailyExchangeRateDto;
import by.fin.module.core.dto.RatesDto;
import by.fin.module.core.dto.RequestAverageRateDto;

import java.util.List;

public interface DailyRatesService {

	List<DailyExchangeRateDto> saveRates(RatesDto ratesDto);

	List<DailyExchangeRateDto> getRates(String currency);

	double averageRate(RequestAverageRateDto rateDto);
}
