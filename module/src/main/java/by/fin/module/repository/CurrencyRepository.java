package by.fin.module.repository;

import by.fin.module.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
	Optional<Currency> findBycurAbbreviation(String CurAbbreviation);
}
