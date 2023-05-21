package by.fin.web.controller;

import by.fin.module.core.dto.DailyExchangeRateDto;
import by.fin.module.core.dto.RatesDto;
import by.fin.module.core.dto.RequestAverageRateDto;
import by.fin.service.DailyRatesService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/rates")
@RestController
public class DailyExchangeRates {

	private final DailyRatesService dailyRatesService;

	public DailyExchangeRates(DailyRatesService dailyRatesService) {
		this.dailyRatesService = dailyRatesService;
	}

	@PostMapping("/period")
	public List<DailyExchangeRateDto> dailyRates(@RequestBody @Validated RatesDto ratesDto){
		return dailyRatesService.saveRates(ratesDto);
	}

	@GetMapping("/{currency}")
	public List<DailyExchangeRateDto> getRates(@PathVariable("currency") @NotEmpty String currency){
		return dailyRatesService.getRates(currency);
	}

	@PostMapping("/average")
	public double average (@RequestBody @Validated RequestAverageRateDto ratesDto){
		return dailyRatesService.averageRate(ratesDto);
	}
}
