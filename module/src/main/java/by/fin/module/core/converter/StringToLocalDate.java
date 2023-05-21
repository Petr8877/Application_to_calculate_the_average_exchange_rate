package by.fin.module.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.TimeZone;

@Component
public class StringToLocalDate implements Converter<String, LocalDate> {
	@Override
	public LocalDate convert(String source) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(Long.parseLong(source)), TimeZone.getDefault().toZoneId());
	}
}
