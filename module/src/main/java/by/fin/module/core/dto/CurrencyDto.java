package by.fin.module.core.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CurrencyDto(@NotNull long Cur_ID,
						  @NotNull LocalDate Date,
						  @NotNull double Cur_OfficialRate) {
}
