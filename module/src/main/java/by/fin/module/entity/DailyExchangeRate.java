package by.fin.module.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@Entity
@NoArgsConstructor
public class DailyExchangeRate {

	@Id
	@Column(name = "currency_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String currency;
	private int curScale;
	private LocalDate date;
	private double curOfficialRate;

	public DailyExchangeRate(String currency, int curScale, LocalDate date, double curOfficialRate) {
		this.currency = currency;
		this.curScale = curScale;
		this.date = date;
		this.curOfficialRate = curOfficialRate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCurScale(int curScale) {
		this.curScale = curScale;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setCurOfficialRate(double curOfficialRate) {
		this.curOfficialRate = curOfficialRate;
	}
}