/**
 * 
 */
package TachografReaderUI.helpers;

import java.nio.charset.Charset;

public class OctetString {
	
	private String octetString="";
	
	protected OctetString() {
	}

	public OctetString(byte b){
		if (Character.isValidCodePoint(b))
		this.octetString=String.format("%c", b);	
	}

	public OctetString (byte[] b){
		
		for (byte bite:b){
			String hex = Integer.toString( ( bite & 0xff ) + 0x100, 16).substring( 1 );
			int num=Integer.parseInt(hex,16);
			//ver tabla utf8
			if (num>=0x30 && num<=0x7a){
				this.octetString+=String.format("%c", bite);
			}else{
				this.octetString+=" " + Integer.toString( ( bite & 0xff ) + 0x100, 16).substring( 1 );
			}			
		}
	}
	

	public OctetString(byte[] datos, short codePage) {
		if (codePage>0 && codePage<16){
			if (codePage==12){
				codePage=1;
			}
			Charset cs=Charset.forName("ISO-8859-"+codePage);
			String so=new String(datos,cs);
			this.octetString=so;
		}
	}


	public String getOctetString() {
		return octetString;
	}


	public void setOctetString(String octetString) {
		this.octetString = octetString;
	}
	
	public static String getHexString(byte[] b) throws Exception {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}
}
