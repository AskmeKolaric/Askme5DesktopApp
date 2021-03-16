/**
 * 
 */
package TachografReaderUI.helpers;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;

public class RealTime {

	
	@SuppressWarnings("unused")
	private Date fecha;
	
	protected RealTime() {
	}

	public RealTime(byte[] bytes){
		this.fecha=new Date((long) ByteBuffer.wrap(Arrays.copyOfRange(bytes, 61, 65)).getInt()*1000);
	}

	public static Date getRealTime(byte[] bytes){
		return new Date((long) ByteBuffer.wrap(bytes).getInt()*1000);
	}
}
