/**
 * 
 */
package TachografReaderUI.helpers;

public class OdometerShort {
	@SuppressWarnings("unused")
	private Integer OdometerShort; 

	static public int getOdometerShort(byte[] bytes){

		int num;
		num= Number.getInteger_24(bytes);
		return num;
		
	}
}
