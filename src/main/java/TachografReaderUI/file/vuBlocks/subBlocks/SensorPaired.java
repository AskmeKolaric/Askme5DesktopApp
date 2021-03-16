/**
 * 
 */
package TachografReaderUI.file.vuBlocks.subBlocks;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import TachografReaderUI.file.driverCardBlock.subBlocks.ExtendedSerialNumber;
import TachografReaderUI.file.vuBlocks.VuSizes;
import TachografReaderUI.helpers.IA5String;
import TachografReaderUI.helpers.RealTime;

/**
 * @author Andres Carmona Gil
 * @version 0.0.1
 * 
 * 2.97.   SensorPaired
 * 
 * Información almacenada en una unidad intravehicular y relativa a la identificación del sensor de movimiento acoplado a la unidad intravehicular (requisito 079).
 * SensorPaired ::= SEQUENCE {
 * sensorSerialNumber SensorSerialNumber,
 * sensorApprovalNumber SensorApprovalNumber,
 * sensorPairingDateFirst SensorPairingDate
 * }
 * sensorSerialNumber es el número de serie del sensor de movimiento que está acoplado actualmente a la unidad intravehicular.
 * sensorApprovalNumber es el número de homologación del sensor de movimiento que está acoplado actualmente a la unidad intravehicular.
 * sensorPairingDateFirst es la fecha en que el sensor de movimiento acoplado actualmente a la unidad intravehicular se acopló por primera vez a una unidad intravehicular.
 *
 */
public class SensorPaired {
	
	
	/**
	 * 2.99.   SensorSerialNumber
	 * Número de serie del sensor de movimiento.
	 * SensorSerialNumber ::= ExtendedSerialNumber
	 */
	private ExtendedSerialNumber sensorSerialNumber;
	/**
	 * 2.92.   SensorApprovalNumber
	 * Número de homologación del sensor.
	 * SensorApprovalNumber ::= IA5String(SIZE(8))
	 * Asignación de valor: sin especificar.
	 */
	private String sensorApprovalNumber;
	/**
	 * 2.98.   SensorPairingDate
	 * Fecha de un acoplamiento entre el sensor de movimiento y la unidad intravehicular.
	 * SensorPairingDate ::= TimeReal
	 * Asignación de valor: sin especificar.
	 */
	private Date sensorPairingDateFirst;

	/**
	 * @throws IOException 
	 * 
	 */
	public SensorPaired(byte[] bytes) throws IOException {
		int start=0;
		this.sensorSerialNumber=new ExtendedSerialNumber(Arrays.copyOfRange(bytes, start, start += VuSizes.SENSORSERIALNUMBER.getSize()));
		
		IA5String ia5s=new IA5String(Arrays.copyOfRange(bytes, start, start += VuSizes.SENSORAPPROVALNUMBER.getSize()));
		this.sensorApprovalNumber=ia5s.getiA5String();		
		this.sensorPairingDateFirst= RealTime.getRealTime(Arrays.copyOfRange(bytes, start, start += VuSizes.SENSORPAIRINGDATEFIRST.getSize()));
	}

	/**
	 * @return the sensorSerialNumber
	 */
	public ExtendedSerialNumber getSensorSerialNumber() {
		return sensorSerialNumber;
	}

	/**
	 * @param sensorSerialNumber the sensorSerialNumber to set
	 */
	public void setSensorSerialNumber(ExtendedSerialNumber sensorSerialNumber) {
		this.sensorSerialNumber = sensorSerialNumber;
	}

	/**
	 * @return the sensorApprovalNumber
	 */
	public String getSensorApprovalNumber() {
		return sensorApprovalNumber;
	}

	/**
	 * @param sensorApprovalNumber the sensorApprovalNumber to set
	 */
	public void setSensorApprovalNumber(String sensorApprovalNumber) {
		this.sensorApprovalNumber = sensorApprovalNumber;
	}

	/**
	 * @return the sensorPairingDateFirst
	 */
	public Date getSensorPairingDateFirst() {
		return sensorPairingDateFirst;
	}

	/**
	 * @param sensorPairingDateFirst the sensorPairingDateFirst to set
	 */
	public void setSensorPairingDateFirst(Date sensorPairingDateFirst) {
		this.sensorPairingDateFirst = sensorPairingDateFirst;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SensorPaired [sensorSerialNumber=" + sensorSerialNumber + ", sensorApprovalNumber="
				+ sensorApprovalNumber + ", sensorPairingDateFirst=" + sensorPairingDateFirst + "]";
	}

	
}
