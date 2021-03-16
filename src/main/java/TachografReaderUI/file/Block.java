/**
 * 
 */
package TachografReaderUI.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import TachografReaderUI.file.certificate.Signature;

public abstract class Block {

	private String FID;	
	
	private String TRED;
	
	private int size;			
	@JsonIgnore
	private byte[] datos;

	private Signature signature;
	
	
	public String getTRED() {
		return TRED;
	}

	public void setTRED(String tRED) {
		TRED = tRED;
	}

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getDatos() {
		return datos;
	}

	public void setDatos(byte[] datos) {
		this.datos = datos;
	}

	public void toBinaryString(){
		String s;
		for (Byte bite:this.datos){
			s = String.format("%8s", Integer.toBinaryString(bite & 0xFF)).replace(' ', '0');
			System.out.println(s);
		}

	}

	public void toHexString(){
		for (Byte bite:this.datos){
			System.out.println(Integer.toHexString(bite));
		}
	}

	public String toJson(){
		ObjectMapper mapper=new ObjectMapper();
		String str="";
		try {
			str=mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}		
		return str;
	}


	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	
}
