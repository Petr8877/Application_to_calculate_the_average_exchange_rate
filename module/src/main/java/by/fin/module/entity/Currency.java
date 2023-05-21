package by.fin.module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@NoArgsConstructor
public class Currency {

	@Id
	@Column(name = "currency_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int curId;
	private String curAbbreviation;
	private int curScale;

	public Currency(int curId, String curAbbreviation, int curScale) {
		this.curId = curId;
		this.curAbbreviation = curAbbreviation;
		this.curScale = curScale;
	}

	public void setCurId(int curId) {
		this.curId = curId;
	}

	public void setCurAbbreviation(String curAbbreviation) {
		this.curAbbreviation = curAbbreviation;
	}

	public void setCurScale(int curScale) {
		this.curScale = curScale;
	}
}
