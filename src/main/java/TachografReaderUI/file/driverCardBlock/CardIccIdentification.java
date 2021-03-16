package TachografReaderUI.file.driverCardBlock;

import java.io.IOException;
import java.util.Arrays;
import TachografReaderUI.file.Block;
import TachografReaderUI.file.driverCardBlock.subBlocks.CardApprovalNumber;
import TachografReaderUI.file.driverCardBlock.subBlocks.ExtendedSerialNumber;
import TachografReaderUI.helpers.OctetString;


/**
 *
 * 2.19. CardIccIdentification
 *
 * Informaci�n almacenada en una tarjeta y relativa a la identificaci�n de la tarjeta con circuito integrado (CI) (requisito 192).
 *
 * CardIccIdentification::= SEQUENCE {
 * clockStop OCTET STRING (SIZE(1)),
 * cardExtendedSerialNumber ExtendedSerialNumber,
 * cardApprovalNumber CardApprovalNumber
 * cardPersonaliserID OCTET STRING (SIZE(1)),
 * embedderIcAssemblerId OCTET STRING (SIZE(5)),
 * icIdentifier OCTET STRING (SIZE(2))
 * }
 * 
 * clockStop es el modo de paro de reloj, tal y como se define en la norma EN 726-3.
 * cardExtendedSerialNumber es el n�mero de serie y la referencia de fabricaci�n de la tarjeta CI, tal y como se definen en la norma EN 726-3. Esta informaci�n se completa con el tipo de datos ExtendedSerialNumber.
 * cardApprovalNumber es el n�mero de homologaci�n del modelo de tarjeta.
 * cardPersonaliserID es la identificaci�n personal de la tarjeta, tal y como se define en la norma EN 726-3.
 * embedderIcAssemblerId es la identificaci�n del fabricante de la tarjeta/encargado de integrar el CI, tal y como se define en la norma EN 726-3.
 * icIdentifier es el identificador del CI que incorpora la tarjeta y del fabricante de dicho CI, tal y como se define en la norma EN 726-3.
 * 
 * @author Andr�s Carmona Gil
 * @version 0.0.1  	
 */
public class CardIccIdentification extends Block implements CardBlock{
	
	public static final String FIDhex="0x0002";
	
	private String clockStop;
	
	private ExtendedSerialNumber cardExtendedSerialNumber;
	
	private CardApprovalNumber cardApprovalNumber;
	
	private String cardPersonaliserID="";
	
	private String embedderIcAssemblerId="";
	
	private String icIdentifier="";
	
	/**
	 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
	 * segun  el tipo de propiedad.
	 * @param datos array de bytes
	 */
	public CardIccIdentification (byte[] datos) throws IOException{
		
		 // start es indice donde comienza el dato el siguiente dato comienza en start mas lengh del anterior
		// DriverCardSizes una clase de tipo enum con las prodiedades (bytes max, bytes min, por defecto)
		int start=0;
		OctetString os=new OctetString(Arrays.copyOfRange(datos, start, start+=DriverCardSizes.CLOCKSTOP.getMax()));
		this.clockStop=os.getOctetString();
		
		this.cardExtendedSerialNumber=new ExtendedSerialNumber(Arrays.copyOfRange(datos, start, start+=DriverCardSizes.CARDEXTENDEDSERIALNUMBER.getMax()));
			
		this.cardApprovalNumber= new CardApprovalNumber(Arrays.copyOfRange(datos, start, start+=DriverCardSizes.CARDAPPROVALNUMBER.getMax()));
		
		os=new OctetString(Arrays.copyOfRange(datos, start, start+=DriverCardSizes.CARDPERSONALISERID.getMax()));
		this.cardPersonaliserID = os.getOctetString();					
		
		os=new OctetString(Arrays.copyOfRange(datos, start, start+=DriverCardSizes.EMBEDDERICASSEMBLERID.getMax()));
		this.embedderIcAssemblerId=os.getOctetString();								
		
		os=new OctetString(Arrays.copyOfRange(datos, start,start+DriverCardSizes.ICIDENTIFIER.getMax()));
		this.icIdentifier=os.getOctetString();
			
			
			
			
			
		
	}
	
	public CardIccIdentification(){}

	
	
	/**
	 * Obtiene el modo de paro de reloj, tal y como se define en la norma EN 726-3.
	 * @return the clockStop
	 */
	public String getClockStop() {
		return clockStop;
	}

	/**
	 * Asigna el modo de paro de reloj, tal y como se define en la norma EN 726-3.
	 * @param clockStop the clockStop to set
	 */
	public void setClockStop(String clockStop) {
		this.clockStop = clockStop;
	}

	/**
	 * Obtiene el n�mero de serie y la referencia de fabricaci�n de la tarjeta CI, tal y como se definen en la norma EN 726-3. Esta informaci�n se completa con el tipo de datos ExtendedSerialNumber.
	 * @return the cardExtendedSerialNumber
	 */
	public ExtendedSerialNumber getCardExtendedSerialNumber() {
		return cardExtendedSerialNumber;
	}

	/**
	 * Asigna el n�mero de serie y la referencia de fabricaci�n de la tarjeta CI, tal y como se definen en la norma EN 726-3. Esta informaci�n se completa con el tipo de datos ExtendedSerialNumber.
	 * @param cardExtendedSerialNumber the cardExtendedSerialNumber to set
	 */
	public void setCardExtendedSerialNumber(
			ExtendedSerialNumber cardExtendedSerialNumber) {
		this.cardExtendedSerialNumber = cardExtendedSerialNumber;
	}

	/**
	 * Obtiene el n�mero de homologaci�n del modelo de tarjeta.
	 * @return the cardApprovalNumber
	 */
	public CardApprovalNumber getCardApprovalNumber() {
		return cardApprovalNumber;
	}

	/**
	 * Asigna el n�mero de homologaci�n del modelo de tarjeta.
	 * @param cardApprovalNumber the cardApprovalNumber to set
	 */
	public void setCardApprovalNumber(CardApprovalNumber cardApprovalNumber) {
		this.cardApprovalNumber = cardApprovalNumber;
	}

	/**
	 * Obtiene la identificaci�n personal de la tarjeta, tal y como se define en la norma EN 726-3.
	 * @return the cardPersonaliserID
	 */
	public String getCardPersonaliserID() {
		return cardPersonaliserID;
	}

	/**
	 * Asigna la identificaci�n personal de la tarjeta, tal y como se define en la norma EN 726-3.
	 * @param cardPersonaliserID the cardPersonaliserID to set
	 */
	public void setCardPersonaliserID(String cardPersonaliserID) {
		this.cardPersonaliserID = cardPersonaliserID;
	}

	/**
	 * Obtiene la identificaci�n del fabricante de la tarjeta/encargado de integrar el CI, tal y como se define en la norma EN 726-3.
	 * @return the embedderIcAssemblerId
	 */
	public String getEmbedderIcAssemblerId() {
		return embedderIcAssemblerId;
	}

	/**
	 * Asigna la identificaci�n del fabricante de la tarjeta/encargado de integrar el CI, tal y como se define en la norma EN 726-3.
	 * @param embedderIcAssemblerId the embedderIcAssemblerId to set
	 */
	public void setEmbedderIcAssemblerId(String embedderIcAssemblerId) {
		this.embedderIcAssemblerId = embedderIcAssemblerId;
	}

	/**
	 * Obtiene el identificador del CI que incorpora la tarjeta y del fabricante de dicho CI, tal y como se define en la norma EN 726-3.
	 * @return the icIdentifier
	 */
	public String getIcIdentifier() {
		return icIdentifier;
	}

	/**
	 * Asigna el identificador del CI que incorpora la tarjeta y del fabricante de dicho CI, tal y como se define en la norma EN 726-3.
	 * @param icIdentifier the icIdentifier to set
	 */
	public void setIcIdentifier(String icIdentifier) {
		this.icIdentifier = icIdentifier;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CardIccIdentification [clockStop=" + clockStop
				+ ", cardExtendedSerialNumber=" + cardExtendedSerialNumber
				+ ", cardApprovalNumber=" + cardApprovalNumber
				+ ", cardPersonaliserID=" + cardPersonaliserID
				+ ", embedderIcAssemblerId=" + embedderIcAssemblerId
				+ ", icIdentifier=" + icIdentifier + "]";
	}

	

	
}
