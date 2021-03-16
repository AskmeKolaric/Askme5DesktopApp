/**
 * 
 */
package TachografReaderUI.file.driverCardBlock.subBlocks;

public class ActivityChangeInfo {
	
	private String s,c,p,aa,t;
	private int hours;
	private int min;
	
	public ActivityChangeInfo() {}

	@SuppressWarnings("unused")
	public ActivityChangeInfo(byte[] bytes){
				
		
		String s1 = String.format("%8s", Integer.toBinaryString(bytes[0] & 0xFF)).replace(' ', '0');
		String s2 = String.format("%8s", Integer.toBinaryString(bytes[1] & 0xFF)).replace(' ', '0');				
		String s3 = s1+s2;
		
		int s,c,p,aa,t;		
		s = Integer.valueOf(s3.substring(0, 1));
		c = Integer.valueOf(s3.substring(1, 2));
		p = Integer.valueOf(s3.substring(2, 3));
		aa = Integer.valueOf(s3.substring(3, 5));
		t = Short.valueOf(s3.substring(5,16),2);
	
		this.s = (s==0)?"conductor":"segundo conductor";
		if (p==0){
			this.c = (c==0)?"solitario":"en equipo";
		} else {
			this.c = (c==0)?"indeterminado":"entrada manual";
		}
		this.p = (p==0)?"card status":"not inserted";
		switch(aa){
			case 00: this.aa="break/rest";break;
			case 01: this.aa="availabilty";break;
			case 10: this.aa="work";break;
			case 11: this.aa="driving";break;
		}
		int hora = 0;
		int min = 0;
		if (t > 60){
			hora = t/60;
			min = t%60;
		} else {
			min = t;
		}
		
		this.hours = hora;
		this.min = min;
		this.t = String.valueOf(hora)+":"+String.valueOf(min);
		
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "\nActivityChangeInfo [s=" + s + ", c=" + c + ", p=" + p + ", aa="
				+ aa + ", t=" + t + "]";
	}

}
	


