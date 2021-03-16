/**
 * 
 */
package TachografReaderUI.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileDDD {

	public static final String FILE_TYPE_DDD = "ddd";
	public static final String FILE_TYPE_JSON = "json";

	private String nameFile=null;		

	private VuBlockFile vuBlockFile;

	private CardBlockFile cardBlockFile;


	public FileDDD() {

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FileDDD(byte[] bytes) throws Exception {
		if (bytes[0]==0x76){
			this.vuBlockFile=new VuBlockFile(bytes);
		}else{
			this.cardBlockFile=new CardBlockFile(bytes);
		}

	}


	public VuBlockFile getVuBlockFile() {
		return vuBlockFile;
	}

	public void setVuBlockFile(VuBlockFile vuBlockFile) {
		this.vuBlockFile = vuBlockFile;
	}

	public CardBlockFile getCardBlockFile() {
		return cardBlockFile;
	}

	public void setCardBlockFile(CardBlockFile cardBlockFile) {
		this.cardBlockFile = cardBlockFile;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	@Override
	public String toString() {
		return "FileDDD [nameFile=" + nameFile + ", vuBlockFile=" + vuBlockFile + ", cardBlockFile=" + cardBlockFile
				+ "]";
	}

	@JsonIgnore
	public String getJson() {
		ObjectMapper mapper = new ObjectMapper();
		String str = "";
		try {
			str = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		return str;
	}
}
