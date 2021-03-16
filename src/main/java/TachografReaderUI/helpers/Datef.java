/**
 * 
 */
package TachografReaderUI.helpers;

import java.util.Arrays;


public class Datef {
	
	private String year;
	private String month;
	private String day;

	public Datef(byte[] bytes) {
		int start=0;
		this.year = BCDString.BCDtoString(Arrays.copyOfRange(bytes, start, start+=2));
		this.month = BCDString.BCDtoString(Arrays.copyOfRange(bytes, start, start+=1));
		this.day = BCDString.BCDtoString(Arrays.copyOfRange(bytes, start, start+=1));
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
}
