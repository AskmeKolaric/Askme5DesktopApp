/**
 * 
 */
package TachografReaderUI.file.driverCardBlock;

import java.util.*;
import TachografReaderUI.file.Block;
import TachografReaderUI.file.driverCardBlock.subBlocks.CardActivityDailyRecord;
import TachografReaderUI.helpers.Number;


/**
 * 2.13. CardDriverActivity
 * Informaci�n almacenada en una tarjeta de conductor o en una tarjeta del centro de ensayo y relativa a las actividades
 * del conductor (requisitos 199 y 219).
 * CardDriverActivity ::= SEQUENCE {
 * activityPointerOldestDayRecord INTEGER(0..CardActivityLengthRange-1),
 * activityPointerNewestRecord INTEGER(0..CardActivityLengthRange-1),
 * activityDailyRecords OCTET STRING (SIZE(CardActivityLengthRange))
 * }
 * activityPointerOldestDayRecord es un elemento que se�ala el comienzo del espacio de almacenamiento (n�mero de
 * bytes a partir del principio de la cadena) que corresponde al registro completo m�s antiguo de ese d�a en la cadena activityDailyRecords.
 * El valor m�ximo viene dado por la longitud de la cadena.
 * 
 * activityPointerNewestRecord es un elemento que se�ala el comienzo del espacio de almacenamiento (n�mero de
 * bytes a partir del principio de la cadena) que corresponde al registro m�s reciente de ese d�a en la cadena activityDailyRecords.
 * El valor m�ximo viene dado por la longitud de la cadena.
 * 
 * activityDailyRecords es el espacio disponible para almacenar los datos sobre la actividad del conductor (estructura de
 * datos: CardActivityDailyRecord) en cada uno de los d�as civiles en que se ha utilizado la tarjeta.
 * 
 * Asignaci�n de valor: esta cadena de octetos se va llenando c�clicamente con registros del tipo CardActivityDailyRecord.
 * En el primer uso, el almacenamiento comienza en el primer byte de la cadena. Cada nuevo registro se a�ade al
 * final del anterior. Cuando la cadena est� llena, el almacenamiento contin�a en el primer byte de la cadena, con independencia
 * de si hay alguna pausa dentro de un elemento de datos. Antes de introducir en la cadena nuevos datos de actividad
 * (ampliando el actual activityDailyRecord, o introduciendo un nuevo activityDailyRecord) que sustituyan a datos
 * antiguos, es preciso actualizar el activityPointerOldestDayRecord para reflejar la nueva ubicaci�n del registro completo
 * m�s antiguo de ese d�a, y adem�s es preciso poner a 0 la longitud activityPreviousRecordLength de este (nuevo) registro
 * completo m�s antiguo del d�a.
 * 
 * @author Andr�s Carmona Gil
 * @version 0.0.1
 *
 */
public class CardDriverActivity extends Block implements CardBlock {

	/**
	 * 
	 */
	public CardDriverActivity() {}

	private Integer activityPointerOldestDayRecord;
	private Integer activityPointerNewestRecord;
	private ArrayList<CardActivityDailyRecord> activityDailyRecords;

	
	/**
	 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
	 * segun  el tipo de propiedad.
	 * @param datos array de bytes
	 */
	public CardDriverActivity(byte[] datos){
		
		int start = 0; // indice del array de byte
		byte[] arrayorigen;
		this.activityPointerOldestDayRecord = (int) Number
				.getShort_16(Arrays.copyOfRange(datos, start, start += DriverCardSizes.ACTIVITYPOINTEROLDESTADAYRECORD.getMax()));
		this.activityPointerNewestRecord = (int) Number
				.getShort_16(Arrays.copyOfRange(datos, start, start += DriverCardSizes.ACTIVITYPOINTERNEWESTRECORD.getMax()));
		arrayorigen = Arrays.copyOfRange(datos, start, start += datos.length);

		// preparamos array de datos ya que es una grabacion ciclica y cuando
		// llega al final continua al principio
		// de los datos por lo que el inicio podria estar en medio de los datos
		byte[] arraybyte;
		if (this.activityPointerNewestRecord < this.activityPointerOldestDayRecord) {
			byte[] array1 = Arrays.copyOfRange(arrayorigen, this.activityPointerOldestDayRecord, datos.length-4);
			//byte[] array2 = Arrays.copyOfRange(arrayorigen, 0, this.activityPointerNewestRecord);
			byte[] array2 = Arrays.copyOfRange(arrayorigen, 0, this.activityPointerOldestDayRecord);
			arraybyte = new byte[array1.length + array2.length];
			System.arraycopy(array1, 0, arraybyte, 0, array1.length);
			System.arraycopy(array2, 0, arraybyte, array1.length, array2.length );

		} else {
			// hay que quitarle los 4 bytes primeros de
			// activityPointerOldestDayRecord y acitivityPointerNewestRecord
			arraybyte = Arrays.copyOfRange(datos, this.activityPointerOldestDayRecord + 4,
					this.activityPointerNewestRecord + 4 - this.activityPointerOldestDayRecord + 4);

		}
		int length = 0;
		int indice = 0;

		CardActivityDailyRecord cadr;
		this.activityDailyRecords = new ArrayList<CardActivityDailyRecord>();
		

		Calendar c = Calendar.getInstance();
		CardActivityDailyRecord last_cadr;
		while (indice < arraybyte.length) {
			length = Number.getShort_16(Arrays.copyOfRange(arraybyte, indice + DriverCardSizes.ACTIVITYRECORDLENGTH.getMax(),
					indice + indice + DriverCardSizes.ACTIVITYRECORDLENGTH.getMax() + DriverCardSizes.ACTIVITYRECORDLENGTH.getMax()));
			System.out.println("length "+length+" indice:"+indice);
			if (length > 0){
				byte[] arrayfrom = Arrays.copyOfRange(arraybyte, indice, indice += length);
				cadr = new CardActivityDailyRecord(arrayfrom);
				if (this.activityDailyRecords.size() > 0){
					last_cadr = this.activityDailyRecords.get(this.activityDailyRecords.size() - 1);
					if (last_cadr.getActivityRecordLength() == cadr.getActivityPreviousRecordLength()
							&& last_cadr.getActivityDailyPresenceCounter() + 1 == cadr.getActivityDailyPresenceCounter()){
						this.activityDailyRecords.add(cadr);
					}
				}else{
					this.activityDailyRecords.add(cadr);
				}
			}

		}
	}

	/**
	 * Obtenemos elemento que se�ala el comienzo del espacio de almacenamiento (n�mero de
	 * bytes a partir del principio de la cadena) que corresponde al registro completo m�s antiguo de ese d�a en la cadena activityDailyRecords.
	 * El valor m�ximo viene dado por la longitud de la cadena
	 * @return the activityPointerOldestDayRecord
	 */
	public Integer getActivityPointerOldestDayRecord() {
		return activityPointerOldestDayRecord;
	}

	/**
	 * Asignamos elemento que se�ala el comienzo del espacio de almacenamiento (n�mero de
	 * bytes a partir del principio de la cadena) que corresponde al registro completo m�s antiguo de ese d�a en la cadena activityDailyRecords.
	 * El valor m�ximo viene dado por la longitud de la cadena
	 * @param activityPointerOldestDayRecord the activityPointerOldestDayRecord to set
	 */
	public void setActivityPointerOldestDayRecord(
			Integer activityPointerOldestDayRecord) {
		this.activityPointerOldestDayRecord = activityPointerOldestDayRecord;
	}

	/**
	 * Obtienes elemento que se�ala el comienzo del espacio de almacenamiento (n�mero de
	 * bytes a partir del principio de la cadena) que corresponde al registro m�s reciente de ese d�a en la cadena activityDailyRecords.
	 * El valor m�ximo viene dado por la longitud de la cadena.
	 * @return the activityPointerNewestRecord
	 */
	public Integer getActivityPointerNewestRecord() {
		return activityPointerNewestRecord;
	}

	/**
	 * Asignamos elemento que se�ala el comienzo del espacio de almacenamiento (n�mero de
	 * bytes a partir del principio de la cadena) que corresponde al registro m�s reciente de ese d�a en la cadena activityDailyRecords.
	 * El valor m�ximo viene dado por la longitud de la cadena.
	 * @param activityPointerNewestRecord the activityPointerNewestRecord to set
	 */
	public void setActivityPointerNewestRecord(Integer activityPointerNewestRecord) {
		this.activityPointerNewestRecord = activityPointerNewestRecord;
	}

	/**
	 * Obtenemos el espacio disponible para almacenar los datos sobre la actividad del conductor (estructura de
	 * datos: CardActivityDailyRecord) en cada uno de los d�as civiles en que se ha utilizado la tarjeta.
	 * @return the activityDailyRecords
	 */
	public ArrayList<CardActivityDailyRecord> getActivityDailyRecords() {
		return activityDailyRecords;
	}

	/**
	 * Asignamos el espacio disponible para almacenar los datos sobre la actividad del conductor (estructura de
	 * datos: CardActivityDailyRecord) en cada uno de los d�as civiles en que se ha utilizado la tarjeta.
	 * @param activityDailyRecords the activityDailyRecords to set
	 */
	public void setActivityDailyRecords(ArrayList<CardActivityDailyRecord> activityDailyRecords) {
		this.activityDailyRecords = activityDailyRecords;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CardDriverActivity [activityPointerOldestDayRecord="
				+ activityPointerOldestDayRecord
				+ ", activityPointerNewestRecord="
				+ activityPointerNewestRecord + ", activityDailyRecords="
				+ activityDailyRecords + "]";
	}

	

	
	
	
}
