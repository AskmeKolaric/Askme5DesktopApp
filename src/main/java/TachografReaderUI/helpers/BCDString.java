/**
 * 
 */
package TachografReaderUI.helpers;


public class BCDString {

	public static String BCDtoString(byte bcd) {
		StringBuffer sb = new StringBuffer();

		// elimino 4 ultimos bits
		byte high = (byte) (bcd & 0xf0);
		// desplazo los 4 bits hacia la derecha
		high >>>= (byte) 4;		
		// pongo a cero los cuatro primeros bits
		high = (byte) (high & 0x0f);
		// elimino los 4 primeros bits
		byte low = (byte) (bcd & 0x0f);

		sb.append(high);
		sb.append(low);

		return sb.toString();
	}


	public static String BCDtoString(byte[] bcd) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < bcd.length; i++) {
			sb.append(BCDtoString(bcd[i]));
		}

		return sb.toString();
	}
}
