package TachografReaderUI.file.driverCardBlock;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import TachografReaderUI.file.Block;
import TachografReaderUI.file.driverCardBlock.subBlocks.CardNumber;
import TachografReaderUI.file.driverCardBlock.subBlocks.DriverCardHolderIdentification;
import TachografReaderUI.file.driverCardBlock.subBlocks.Name;
import TachografReaderUI.file.driverCardBlock.subBlocks.NationNumeric;
import TachografReaderUI.helpers.RealTime;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 
 * 2.20. CardIdentification
 *
 * Informaci�n almacenada en una tarjeta y relativa a la identificaci�n de la tarjeta (requisitos 194, 215, 231, 235).
 * 
 * CardIdentification::= SEQUENCE
 * cardIssuingMemberState NationNumeric,
 * cardNumber CardNumber,
 * cardIssuingAuthorityName Name, 
 * cardIssueDate TimeReal,
 * cardValidityBegin TimeReal, 
 * cardExpiryDate TimeReal
 * }
 *
 * cardIssuingMemberState es el c�digo del Estado miembro que expide la tarjeta.
 * cardNumber es el n�mero de la tarjeta.
 * cardIssuingAuthorityName es el nombre de la autoridad que ha expedido la tarjeta.
 * cardIssueDate es la fecha en que se expidi� la tarjeta al titular actual.
 * cardValidityBegin es la fecha correspondiente al primer d�a de validez de la tarjeta. 
 * cardExpiryDate es la fecha en que termina la validez de la tarjeta.
 * @author Andr�s Carmona Gil
 * @version 0.0.1
 */
public class CardIdentification extends Block implements CardBlock{



	private String cardIssuingMemberState;
	
	private CardNumber cardNumber;
	
	private String cardIssuingAuthorityName;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	private Date cardIssueDate; 
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	private Date cardValidityBegin;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	private Date cardExpiryDate;
			
	private DriverCardHolderIdentification driverCardHolderIdentification;
	
	
	public CardIdentification() {}

	/**
	 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
	 * segun  el tipo de propiedad.
	 * @param datos array de bytes
	 */
	public CardIdentification(byte[] datos) throws UnsupportedEncodingException {
		//start indice array de bytef
		int start=0;
		
		NationNumeric nn=new NationNumeric(Arrays.copyOfRange(datos, start, start+= DriverCardSizes.CARDISSUINGMEMBERSTATE.getMax()));
		this.cardIssuingMemberState=nn.getNationNumeric();
		
		this.cardNumber=new CardNumber(Arrays.copyOfRange(datos,start,start+= DriverCardSizes.CARDNUMBER.getMax()));
		
		Name name=new Name(Arrays.copyOfRange(datos,start,start+= DriverCardSizes.CARDISSUINGAUTORITYNAME.getMax()));
		this.cardIssuingAuthorityName=name.getName();
		
		
		this.cardIssueDate= new Date();
		this.cardIssueDate= RealTime.getRealTime(Arrays.copyOfRange(datos,start, start+= DriverCardSizes.CARDISSUEDATE.getMax()));
		
		this.cardValidityBegin=new Date();
		this.cardValidityBegin=RealTime.getRealTime(Arrays.copyOfRange(datos,start, start+= DriverCardSizes.CARDVALIDITYBEGIN.getMax()));
		
		this.cardExpiryDate=new Date();
		this.cardExpiryDate=RealTime.getRealTime(Arrays.copyOfRange(datos,start, start+= DriverCardSizes.CARDEXPIRYDATE.getMax()));
		
		this.driverCardHolderIdentification=new DriverCardHolderIdentification(Arrays.copyOfRange(datos,start,start+= DriverCardSizes.DRIVERCARDHOLDERIDENTIFICATION.getMax()));
		
				
	}
	

	/**
	 * Obtiene el c�digo del Estado miembro que expide la tarjeta.
	 * @return the cardIssuingMemberState
	 */
	public String getCardIssuingMemberState() {
		return cardIssuingMemberState;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CardIdentification [cardIssuingMemberState=" + cardIssuingMemberState + ", cardNumber=" + cardNumber
				+ ", cardIssuingAuthorityName=" + cardIssuingAuthorityName + ", cardIssueDate=" + cardIssueDate
				+ ", cardValidityBegin=" + cardValidityBegin + ", cardExpiryDate=" + cardExpiryDate
				+ ", driverCardHolderIdentification=" + driverCardHolderIdentification + "]";
	}

	/**
	 * Asigna el c�digo del Estado miembro que expide la tarjeta.
	 * @param cardIssuingMemberState the cardIssuingMemberState to set
	 */
	public void setCardIssuingMemberState(String cardIssuingMemberState) {
		this.cardIssuingMemberState = cardIssuingMemberState;
	}

	/**
	 * Obtiene el n�mero de la tarjeta.
	 * @return the cardNumber
	 */
	public CardNumber getCardNumber() {
		return cardNumber;
	}

	/**
	 * Asigna el n�mero de la tarjeta.
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(CardNumber cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * Obtiene el nombre de la autoridad que ha expedido la tarjeta.
	 * @return the cardIssuingAuthorityName
	 */
	public String getCardIssuingAuthorityName() {
		return cardIssuingAuthorityName;
	}

	/**
	 * Asigna el nombre de la autoridad que ha expedido la tarjeta.
	 * @param cardIssuingAuthorityName the cardIssuingAuthorityName to set
	 */
	public void setCardIssuingAuthorityName(String cardIssuingAuthorityName) {
		this.cardIssuingAuthorityName = cardIssuingAuthorityName;
	}

	/**
	 * Obtiene la fecha en que se expidi� la tarjeta al titular actual.
	 * @return the cardIssueDate
	 */
	public Date getCardIssueDate() {
		return cardIssueDate;
	}

	/**
	 * Asigna la fecha en que se expidi� la tarjeta al titular actual.
	 * @param cardIssueDate the cardIssueDate to set
	 */
	public void setCardIssueDate(Date cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}

	/**
	 * Obtiene la fecha correspondiente al primer d�a de validez de la tarjeta. 
	 * @return the cardValidityBegin
	 */
	public Date getCardValidityBegin() {
		return cardValidityBegin;
	}

	/**
	 * Asigna la fecha correspondiente al primer d�a de validez de la tarjeta. 
	 * @param cardValidityBegin the cardValidityBegin to set
	 */
	public void setCardValidityBegin(Date cardValidityBegin) {
		this.cardValidityBegin = cardValidityBegin;
	}

	/**
	 * Obtiene la fecha en que termina la validez de la tarjeta.
	 * @return the cardExpiryDate
	 */
	public Date getCardExpiryDate() {
		return cardExpiryDate;
	}

	/**
	 * Asigna la fecha en que termina la validez de la tarjeta.
	 * @param cardExpiryDate the cardExpiryDate to set
	 */
	public void setCardExpiryDate(Date cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	/**
	 * @return the driverCardHolderIdentification
	 */
	public DriverCardHolderIdentification getDriverCardHolderIdentification() {
		return driverCardHolderIdentification;
	}

	/**
	 * @param driverCardHolderIdentification the driverCardHolderIdentification to set
	 */
	public void setDriverCardHolderIdentification(DriverCardHolderIdentification driverCardHolderIdentification) {
		this.driverCardHolderIdentification = driverCardHolderIdentification;
	}

	
	
	
	
}
