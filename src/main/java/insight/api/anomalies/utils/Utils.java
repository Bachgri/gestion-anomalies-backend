package insight.api.anomalies.utils;

import java.util.Date;

public class Utils {
	public static String getFullDate() {
		Date d = new Date();
		return "" + (d.getYear() + 1900 ) + "_" + ((d.getMonth()+1)<9?"0"+(d.getMonth()+1): (d.getMonth()+1));
	}
	public static String getFullDay() {
		Date d = new Date();
		return "" + d.getDate();
	}
	public static String getFullDate(String s) {
		//Date d = new Date(s);
		s= s.replaceAll("T", " ");
		s= s.replaceAll("Z", " ");
		
		System.out.println("" + s.split(" ")[0].split("-")[0]);//+ "_" +s.split(" ")[0].replaceAll("-", "_").split("_").toString());
		return "" + s.split(" ")[0].split("-")[0] + "_" +s.split(" ")[0].split("-")[1];
	}
	public static String getFullDay(String s) {
		//Date d = new Date(s);
		s= s.replaceAll("T", " ");
		s= s.replaceAll("Z", " ");

		System.err.println("" + s.split(" ")[0].replaceAll("-", "_").split("_")[2]);
		return "" + s.split(" ")[0].split("-")[2];
	}		
	public static String currentTimeStamp() {
		Date d = new Date();
		return "" + (d.getYear()+1900)+"-"+(d.getMonth()+1)+"-"+d.getDate()+" " + (d.getHours()+1)+":"+d.getMinutes()+":"+d.getSeconds();
	}
}
