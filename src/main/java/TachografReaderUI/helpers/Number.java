package TachografReaderUI.helpers;

public class Number {

	
	public Number(){
		
	}

	public static int getInteger_32(byte[] bytes){
		int i= (bytes[0]<<24)&0xff000000|
			       (bytes[1]<<16)&0x00ff0000|
			       (bytes[2]<< 8)&0x0000ff00|
			       (bytes[3]<< 0)&0x000000ff;
		return i;
	}

	public static int getInteger_24(byte[] bytes){
		int i= (bytes[0]<<16)&0xff0000|
			       (bytes[1]<< 8)&0x00ff00|
			       (bytes[2]<< 0)&0x0000ff;
			       
		return i;
	}

	public static int getShort_16(byte[] bytes){
		int num = 0;
		int i= ((bytes[0]<< 8)&0xff00|
			       (bytes[1]<< 0)&0x00ff);

		return i;
	}

	public static short getShort_8(byte[] bytes){
		short i=(short) ((bytes[0]<< 0)&0x000000ff);
		return i;
	}
	
	public static int getNumber(byte[] bytes){
		int n=0;
		switch (bytes.length) {
		case 1:
			n=getShort_8(bytes);
			break;
		case 2:
			n=getShort_16(bytes);
			break;
		case 3:
			n=getInteger_24(bytes);
			break;
		case 4:
			n=getInteger_32(bytes);
			break;
		default:
			n=0;
			break;
		}
		
		return n;
	}
}
