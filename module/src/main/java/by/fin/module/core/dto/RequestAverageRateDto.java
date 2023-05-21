package by.fin.module.core.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestAverageRateDto(@NotBlank String currency,
									@Min(1) @Max(12) @NotNull int month,
									@Max(2023) @NotNull int year) {
}
