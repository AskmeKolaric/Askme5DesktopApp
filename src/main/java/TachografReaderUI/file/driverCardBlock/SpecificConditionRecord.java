/**
 * 
 */
package TachografReaderUI.file.driverCardBlock;

import java.util.Arrays;
import java.util.Date;
import TachografReaderUI.file.Block;
import TachografReaderUI.file.driverCardBlock.subBlocks.SpecificConditionType;
import TachografReaderUI.helpers.RealTime;

/**
 * 2.104. SpecificConditionRecord
 * Informaci�n almacenada en una tarjeta de conductor, una tarjeta del centro de ensayo o una unidad intravehicular y
 * relativa a una condici�n espec�fica (requisitos 105a, 212a y 230a).
 * SpecificConditionRecord ::= SEQUENCE {
 * entryTime TimeReal,
 * specificConditionType SpecificConditionType
 * }
 * entryTime es la fecha y la hora de la entrada.
 * specificConditionType es el c�digo que identifica a la condici�n espec�fica.
 * 
 * @author Andr�s Carmona Gil
 * @version 0.0.1
 *
 */
public class SpecificConditionRecord extends Block implements
		CardBlock {
	private Date entryTime;
	private  String specificConditionType;
	
	
	public SpecificConditionRecord() {}
	/**
	 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
	 * segun  el tipo de propiedad.
	 * @param datos array de bytes
	 */
	public SpecificConditionRecord(byte[] datos){
		int start=0;
		this.entryTime= RealTime.getRealTime(Arrays.copyOfRange(datos, start, start+= DriverCardSizes.ENTRYTIME.getMax()));
		SpecificConditionType sct=new SpecificConditionType(Arrays.copyOfRange(datos,start,start+= DriverCardSizes.SPECIFICCIONDITIONTYPE.getMax()));
		this.specificConditionType=sct.getSpecificConditionType();
		}

	/**
	 * Obtiene la fecha y la hora de la entrada.
	 * @return Date
	 */
	public Date getEntryTime() {
		return entryTime;
	}
	/**
	 * Asigna la fecha y la hora de la entrada.
	 * @param entryTime
	 */
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	/**
	 * Obtiene el c�digo que identifica a la condici�n espec�fica.
	 * @return String
	 */
	public String getSpecificConditionType() {
		return specificConditionType;
	}
	/**
	 * Asigna el c�digo que identifica a la condici�n espec�fica.
	 * @param specificConditionType
	 */
	public void setSpecificConditionType(String specificConditionType) {
		this.specificConditionType = specificConditionType;
	}

	@Override
	public String toString() {
		return "SpecificConditionRecord [entryTime=" + entryTime
				+ ", specificConditionType=" + specificConditionType + "]";
	}
	
}
