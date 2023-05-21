package by.fin.module.repository;

import by.fin.module.core.dto.DailyExchangeRateDto;
import by.fin.module.entity.DailyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyExchangeRateRepository extends JpaRepository<DailyExchangeRate, Long> {
	List<DailyExchangeRateDto> findBycurrency(String currency);

	boolean existsBycurrency(String currency);
}
