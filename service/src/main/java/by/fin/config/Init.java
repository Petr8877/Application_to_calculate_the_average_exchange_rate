package by.fin.config;

import by.fin.module.core.dto.CurInfo;
import by.fin.module.entity.Currency;
import by.fin.module.repository.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class Init implements CommandLineRunner {

	private final CurrencyRepository currencyRepository;
	private final ConversionService conversionService;

	public Init(CurrencyRepository currencyRepository, ConversionService conversionService) {
		this.currencyRepository = currencyRepository;
		this.conversionService = conversionService;
	}

	@Override
	public void run(String... args) throws Exception {
		List<Currency> all = currencyRepository.findAll();
		if (all.size() == 0){
			final RestTemplate restTemplate = new RestTemplate();
			String url2 = "https://api.nbrb.by/exrates/rates?periodicity=0";
			final CurInfo[] rate = restTemplate.getForObject(url2, CurInfo[].class);
			for (CurInfo curInfo : rate) {
				currencyRepository.save(Objects.requireNonNull(conversionService.convert(curInfo, Currency.class)));
			}
		}
	}
}
