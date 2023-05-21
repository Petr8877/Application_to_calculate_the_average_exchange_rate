package by.fin.module.core.converter;

import by.fin.module.core.dto.CurInfo;
import by.fin.module.entity.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurInfoToCurrency implements Converter<CurInfo, Currency> {
	@Override
	public Currency convert(CurInfo source) {
		return new Currency(source.Cur_ID(), source.Cur_Abbreviation(), source.Cur_Scale());
	}
}
