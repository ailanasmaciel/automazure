package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
	
	public static String getDate() {
		Calendar dataAtual = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyy");
		String dataFormatada = simpleDateFormat.format(dataAtual.getTime());
		return dataFormatada;
	}

}
