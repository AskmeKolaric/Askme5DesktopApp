/**
 * 
 */
package TachografReaderUI.helpers;

import java.io.UnsupportedEncodingException;

public class IA5String {
	
	private String iA5String="";

	@SuppressWarnings("unused")
	public IA5String(byte[] bytes) throws UnsupportedEncodingException{
		int num;
		
		for (byte bite:bytes){
			if(bite>0x20 && bite!=0xff && bite!='?' ){				
				this.iA5String+=String.format("%c", bite);	
			}
						
		}
	}

	public String getiA5String() {
		return iA5String;
	}

	public void setiA5String(String iA5String) {
		this.iA5String = iA5String;
	}
	
	
	
}
