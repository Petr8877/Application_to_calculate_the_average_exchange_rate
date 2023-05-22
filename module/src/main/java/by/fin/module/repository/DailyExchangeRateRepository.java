package by.fin.module.repository;

import by.fin.module.core.dto.DailyExchangeRateDto;
import by.fin.module.entity.DailyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyExchangeRateRepository extends JpaRepository<DailyExchangeRate, Long> {
	List<DailyExchangeRateDto> findByCurrency(String currency);

	boolean existsByCurrency(String currency);

	List<DailyExchangeRateDto> findByCurrencyAndDateBetween(String currency, LocalDate startDate, LocalDate endDate);
}
