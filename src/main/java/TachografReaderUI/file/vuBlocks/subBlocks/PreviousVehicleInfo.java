/**
 * 
 */
package TachografReaderUI.file.vuBlocks.subBlocks;

import java.util.Arrays;
import java.util.Date;

import TachografReaderUI.file.Block;
import TachografReaderUI.file.driverCardBlock.subBlocks.VehicleRegistrationIdentification;
import TachografReaderUI.file.vuBlocks.VuSizes;
import TachografReaderUI.helpers.RealTime;

/**
 * @author Andres Carmona Gil
 * @verion 0.0.1
 *
 * 2.85.   PreviousVehicleInfo
 * Información relativa al vehículo que utilizara previamente un conductor, cuando inserta su tarjeta en una unidad intravehicular (requisito 081).
 * PreviousVehicleInfo ::= SEQUENCE {
 * vehicleRegistrationIdentification VehicleRegistrationIdentification,
 * cardWithdrawalTime TimeReal
 * }
 * vehicleRegistrationIdentification es el VRN y el nombre del Estado miembro donde se matriculara el vehículo.
 * cardWithdrawalTime es la fecha y la hora de extracción de la tarjeta.
 */
public class PreviousVehicleInfo extends Block{
	
	
	
	private VehicleRegistrationIdentification vehicleRegistrationIdentification;
	
	private Date cardWithdrawalTime;

	public PreviousVehicleInfo(byte[] bytes) {
		int start=0;
		this.vehicleRegistrationIdentification=new VehicleRegistrationIdentification(Arrays.copyOfRange(bytes, start, start += VuSizes.VEHICLEREGISTRATIONIDENTIFICATION_TREP2.getSize()));
		this.cardWithdrawalTime= RealTime.getRealTime(Arrays.copyOfRange(bytes, start, start += VuSizes.CARDWITHDRAWALTIME_VUCARDWDATA.getSize()));
	}
	
	

}
