package TachografReaderUI.file.driverCardBlock.subBlocks;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import TachografReaderUI.file.driverCardBlock.DriverCardSizes;
import TachografReaderUI.helpers.Datef;

/**
 * 2.50. DriverCardHolderIdentification
 * Informaci�n almacenada en una tarjeta de conductor y relativa a la identificaci�n del titular de dicha tarjeta (requisito
 * 195).
 * DriverCardHolderIdentification ::= SEQUENCE {
 * cardHolderName HolderName,
 * cardHolderBirthDate Datef,
 * cardHolderPreferredLanguage Language
 * }
 * 
 * cardHolderName es el nombre y los apellidos del titular de la tarjeta de conductor.
 * cardHolderBirthDate es la fecha de nacimiento del titular de la tarjeta de conductor.
 * cardHolderPreferredLanguage es el idioma preferido por el titular de la tarjeta.
 * 
 * @author Andres Carmona Gil
 * @version 0.0.1
 *
 *
 */
public class DriverCardHolderIdentification {
	
	private HolderName cardHolderName;
	private String cardHolderBirthDate;
	private String cardHolderPreferredLanguage;
	
	
	public DriverCardHolderIdentification() {}
	/**
	 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
	 * segun  el tipo de propiedad.
	 * @param bytes
	 */
	public DriverCardHolderIdentification(byte[] bytes) throws UnsupportedEncodingException {
		
		int start=0;
		
		this.cardHolderName=new HolderName(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.CARDHOLDERNAME.getMax()));
		
		Datef df=new Datef(Arrays.copyOfRange(bytes, start, start+= DriverCardSizes.CARDHOLDERBIRTHDATE.getMax()));
		this.cardHolderBirthDate=df.getYear()+"-"+df.getMonth()+"-"+df.getDay();
		
		Language l=new Language(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.CARDHOLDERPREFERREDLANGUAGE.getMax()));
		this.cardHolderPreferredLanguage=l.getLanguage();
	}
	
	/**
	 * Obtiene el nombre y los apellidos del titular de la tarjeta de conductor.
	 * @return the cardHolderName
	 */
	public HolderName getCardHolderName() {
		return cardHolderName;
	}

	/**
	 * Asigna el nombre y los apellidos del titular de la tarjeta de conductor.
	 * @param cardHolderName the cardHolderName to set
	 */
	public void setCardHolderName(HolderName cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	/**
	 * Obtiene la fecha de nacimiento del titular de la tarjeta de conductor.
	 * @return the cardHolderBirthDate
	 */
	public String getCardHolderBirthDate() {
		return cardHolderBirthDate;
	}

	/**
	 * Asigna la fecha de nacimiento del titular de la tarjeta de conductor.
	 * @param cardHolderBirthDate the cardHolderBirthDate to set
	 */
	public void setCardHolderBirthDate(String cardHolderBirthDate) {
		this.cardHolderBirthDate = cardHolderBirthDate;
	}

	/**
	 * Obitene el idioma preferido por el titular de la tarjeta.
	 * @return the cardHolderPreferredLanguage
	 */
	public String getCardHolderPreferredLanguage() {
		return cardHolderPreferredLanguage;
	}

	/**
	 * Asigna el idioma preferido por el titular de la tarjeta.
	 * @param cardHolderPreferredLanguage the cardHolderPreferredLanguage to set
	 */
	public void setCardHolderPreferredLanguage(String cardHolderPreferredLanguage) {
		this.cardHolderPreferredLanguage = cardHolderPreferredLanguage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DriverCardHolderIdentification [cardHolderName=" + cardHolderName + ", cardHolderBirthDate="
				+ cardHolderBirthDate + ", cardHolderPreferredLanguage=" + cardHolderPreferredLanguage + "]";
	}


	
}


