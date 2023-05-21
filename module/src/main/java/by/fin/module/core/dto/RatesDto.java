package by.fin.module.core.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RatesDto(@NotBlank String currency,
					   @NotNull LocalDate startDate,
					   @NotNull LocalDate endDate) {
}
