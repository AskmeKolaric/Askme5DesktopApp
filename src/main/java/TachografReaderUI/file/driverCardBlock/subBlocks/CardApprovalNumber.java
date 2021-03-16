package TachografReaderUI.file.driverCardBlock.subBlocks;

import TachografReaderUI.helpers.IA5String;

import java.io.IOException;

public class CardApprovalNumber {
	
	private String number;
	
	public CardApprovalNumber(){}

	public CardApprovalNumber(byte[] datos) throws IOException{		
		
		IA5String ia5=new IA5String(datos);
		this.number=ia5.getiA5String();
		
	}
	
	public String toString(){
		String cadena="";
		cadena="\nnumber: "+this.number;
		return cadena;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	

}
