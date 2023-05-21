package by.fin.module.core.converter;

import by.fin.module.core.dto.DailyExchangeRateDto;
import by.fin.module.entity.DailyExchangeRate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DailyExchangeRateDtoToDailyExchangeRate implements Converter<DailyExchangeRateDto, DailyExchangeRate> {
	@Override
	public DailyExchangeRate convert(DailyExchangeRateDto source) {
		return new DailyExchangeRate(source.currency(), source.curScale(), source.date(), source.curOfficialRate());
	}
}
