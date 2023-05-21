package by.fin.module.core.dto;

import java.time.LocalDate;
public record CurInfo(int Cur_ID,
					  LocalDate Date,
					  String Cur_Abbreviation,
					  int Cur_Scale,
					  double Cur_OfficialRate) {
}
