/**
 * 
 */
package TachografReaderUI.file.driverCardBlock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import TachografReaderUI.file.Block;
import TachografReaderUI.file.driverCardBlock.subBlocks.CardEventRecord;

/**
 * 
 * 2.15. CardEventData
 *
 * Informaci�n almacenada en una tarjeta de conductor o en una tarjeta del centro de ensayo y relativa a los incidentes asociados al titular de la tarjeta (requisitos 204 y 223).
 * 
 * CardEventData::= SEQUENCE SIZE(6) OF {
 * cardEventRecords SET SIZE(NoOfEventsPerType) OF CardEventRecord
 * }
 * CardEventData es una secuencia de cardEventRecords ordenada por valor ascendente del c�digo EventFaultType (excepto los registros relacionados con intentos de violaci�n de la seguridad, que se incluyen en el �ltimo conjunto de la secuencia).
 * cardEventRecords es un conjunto de registros de incidentes de un tipo en particular (o de una categor�a en particular, en el caso de los intentos de violaci�n de la seguridad).
 * 
 * @author Andr�s Carmona Gil
 * @version 0.0.1 
 *
 */
public class CardEventData extends Block implements CardBlock {
	

	private Set<CardEventRecord> cardEventRecords;
	
	/**
	 * 
	 */
	public CardEventData() {
		
	}


	/**
	 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
	 * segun  el tipo de propiedad.
	 * @param datos array de bytes
	 */
	public CardEventData(byte[] datos) {
		
		int end=datos.length/6;
		int end1=end/24;
		int start=0;
		this.cardEventRecords=new HashSet<CardEventRecord>();
		/*
		for (int i=0;i<6;i+=1){
			for (int j=0;j<end1-1;j++){
				CardEventRecord cer=new CardEventRecord(Arrays.copyOfRange(datos,start,start+=24));
				this.cardEventRecords.add(cer);
			}

		}
		*/
		for (int i=start;i<datos.length-1;i+=24){
			byte [] b = Arrays.copyOfRange(datos,start,start+=24);
			CardEventRecord cer=new CardEventRecord(b);
			if (b[0] != 0x00)
				this.cardEventRecords.add(cer);
		}
		
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CardEventData [cardEventRecords=" + cardEventRecords.toString() + "]";
	}



	/**
	 * Obtiene un conjunto de registros de incidentes de un tipo en particular (o de una categor�a en particular, en el caso de los intentos de violaci�n de la seguridad).
	 * @return the cardEventRecords
	 */
	public Set<CardEventRecord> getCardEventRecords() {
		return cardEventRecords;
	}

	/**
	 * Asigna un conjunto de registros de incidentes de un tipo en particular (o de una categor�a en particular, en el caso de los intentos de violaci�n de la seguridad).
	 * @param cardEventRecords the cardEventRecords to set
	 */
	public void setCardEventRecords(Set<CardEventRecord> cardEventRecords) {
		this.cardEventRecords = cardEventRecords;
	}
}
