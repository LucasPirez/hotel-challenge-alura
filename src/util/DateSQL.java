package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Date;

public class DateSQL {
	
	public String getFormatSQL(Date d) {
		SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(d);
		
		return date;
	}
	
	public Date formatDateInput(String d) {
		
		 Date fechaParseada = null;
		try {
			fechaParseada = new SimpleDateFormat("yyyy/MM/dd")
					.parse(d.replaceAll("-", "/"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return fechaParseada;
	}
}
