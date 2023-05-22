package by.fin.service.impl;

import by.fin.module.core.dto.CurrencyDto;
import by.fin.module.core.dto.DailyExchangeRateDto;
import by.fin.module.core.dto.RatesDto;
import by.fin.module.core.dto.RequestAverageRateDto;
import by.fin.module.entity.Currency;
import by.fin.module.entity.DailyExchangeRate;
import by.fin.module.entity.Weekend;
import by.fin.module.repository.CurrencyRepository;
import by.fin.module.repository.DailyExchangeRateRepository;
import by.fin.module.repository.WeekendsRepository;
import by.fin.service.DailyRatesService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DailyRatesServiceImpl implements DailyRatesService {

	private static final LocalDate MIN_DATE = LocalDate.of(2022, 12, 1);
	private static final LocalDate MAX_DATE = LocalDate.of(2023, 5, 31);
	private final CurrencyRepository currencyRepository;
	private final DailyExchangeRateRepository dailyExchangeRateRepository;
	private final ConversionService conversionService;
	private final WeekendsRepository weekendsRepository;

	public DailyRatesServiceImpl(CurrencyRepository currencyRepository, DailyExchangeRateRepository dailyExchangeRateRepository, ConversionService conversionService, WeekendsRepository weekendsRepository) {
		this.currencyRepository = currencyRepository;
		this.dailyExchangeRateRepository = dailyExchangeRateRepository;
		this.conversionService = conversionService;
		this.weekendsRepository = weekendsRepository;
	}

	@Override
	@Transactional
	public List<DailyExchangeRateDto> saveRates(RatesDto ratesDto) {
		Currency currency = validationCurrency(ratesDto.currency());
		validationDates(ratesDto.startDate(), ratesDto.endDate());
		CurrencyDto[] currencyRaw = getCurrencyRaw(currency.getCurId(), ratesDto.startDate(), ratesDto.endDate());
		List<DailyExchangeRateDto> byBetween = dailyExchangeRateRepository.findByCurrencyAndDateBetween(
				ratesDto.currency(), ratesDto.startDate(), ratesDto.endDate());
		List<DailyExchangeRateDto> daily = new ArrayList<>();
		for (CurrencyDto currencyDto : Objects.requireNonNull(currencyRaw)) {
			daily.add(new DailyExchangeRateDto(ratesDto.currency(), currency.getCurScale(), currencyDto.Date(),
					currencyDto.Cur_OfficialRate()));
		}
		for (DailyExchangeRateDto dailyExchangeRateDto : daily) {
			if (!byBetween.contains(dailyExchangeRateDto)) {
				dailyExchangeRateRepository.save(Objects.requireNonNull(conversionService.convert(dailyExchangeRateDto,
						DailyExchangeRate.class)));
			}
		}
		return daily;
	}

	@Override
	@ReadOnlyProperty
	public List<DailyExchangeRateDto> getRates(String currency) {
		validationCurrency(currency);
		if (!dailyExchangeRateRepository.existsByCurrency(currency))
			throw new IllegalArgumentException("The requested currency is not in the database");
		return dailyExchangeRateRepository.findByCurrency(currency);
	}

	@Override
	@ReadOnlyProperty
	public double averageRate(RequestAverageRateDto rateDto) {
		Currency currency = validationCurrency(rateDto.currency());
		LocalDate startDate = LocalDate.of(rateDto.year(), rateDto.month(), 1);
		LocalDate endDate = LocalDate.of(rateDto.year(), rateDto.month(), startDate.lengthOfMonth());
		validationDates(startDate);
		CurrencyDto[] currencyRaw = getCurrencyRaw(currency.getCurId(), startDate, endDate);
		List<CurrencyDto> currencyRateWithoutWeekends = new ArrayList<>();
		for (CurrencyDto currencyDto : currencyRaw) {
			Weekend byIsDayOff = weekendsRepository.findBycalendarDate(currencyDto.Date());
			if (!byIsDayOff.isDayOff()) {
				currencyRateWithoutWeekends.add(currencyDto);
			}
		}
		double sum = 0;
		for (CurrencyDto currencyRateWithoutWeekend : currencyRateWithoutWeekends) {
			sum += currencyRateWithoutWeekend.Cur_OfficialRate();
		}
		return Math.pow(sum, 1.0 / currencyRateWithoutWeekends.size());
	}

	private CurrencyDto[] getCurrencyRaw(int currency, LocalDate startDate, LocalDate endDate) {
		String url = "https://api.nbrb.by/ExRates/Rates/Dynamics/" + currency +
				"?startDate=" + startDate + "&endDate=" + endDate;
		final RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, CurrencyDto[].class);
	}


	private Currency validationCurrency(String currency) {
		return currencyRepository.findBycurAbbreviation(currency)
				.orElseThrow(() -> new IllegalArgumentException("Check the correctness of the entered currency abbreviation."));
	}

	private void validationDates(LocalDate startDate, LocalDate endDate) {
		if (startDate.compareTo(MIN_DATE) < 0 || startDate.compareTo(MAX_DATE) > 0 || endDate.compareTo(MIN_DATE) < 0
				|| endDate.compareTo(MAX_DATE) > 0)
			throw new IllegalArgumentException("The date entered is not correct. Search is possible from 01.12.2022 to 31.05.2023");
		if ((startDate.compareTo(endDate)) > 0)
			throw new IllegalArgumentException("Check the dates you have entered. The start date is larger than the end date");
	}

	private void validationDates(LocalDate date) {
		if (date.compareTo(MIN_DATE) < 0 || date.compareTo(MAX_DATE) > 0)
			throw new IllegalArgumentException("The date entered is not correct. Search is possible from 01.12.2022 to 31.05.2023");
	}
}
