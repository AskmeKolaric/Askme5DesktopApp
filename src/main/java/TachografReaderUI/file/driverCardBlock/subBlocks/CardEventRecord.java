/**
 * 
 */
package TachografReaderUI.file.driverCardBlock.subBlocks;

import java.util.Arrays;
import java.util.Date;
import TachografReaderUI.file.driverCardBlock.DriverCardSizes;
import TachografReaderUI.helpers.RealTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CardEventRecord  {
	
	
	private String eventType;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	private Date eventBeginTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	private Date eventEndTime;
	private VehicleRegistrationIdentification eventVehicleRegistration;
	
	
	public CardEventRecord() {}

	public CardEventRecord(byte[] bytes) {
		int start=0;
		EventFaultType eft= new EventFaultType(Arrays.copyOfRange(bytes,start, start+= DriverCardSizes.EVENTTYPE.getMax()));
		this.eventType=eft.getEventFaultType();
		
		this.eventBeginTime = RealTime.getRealTime(Arrays.copyOfRange(bytes,start,start+= DriverCardSizes.EVENTBEGINTIME.getMax()));
		this.eventEndTime = RealTime.getRealTime(Arrays.copyOfRange(bytes,start,start+= DriverCardSizes.EVENTENDTIME.getMax()));
		
		@SuppressWarnings("unused")
		VehicleRegistrationIdentification vri=new VehicleRegistrationIdentification(Arrays.copyOfRange(bytes,start,start+= DriverCardSizes.EVENTVEHICLEREGISTRATION.getMax()));
		this.eventVehicleRegistration = vri;
	}

	@Override
	public String toString() {
		return "CardEventRecord [eventType=" + eventType + ", eventBeginTime="
				+ eventBeginTime + ", eventEndTime=" + eventEndTime
				+ ", eventVehicleRegistration=" + eventVehicleRegistration
				+ "]";
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getEventBeginTime() {
		return eventBeginTime;
	}

	public void setEventBeginTime(Date eventBeginTime) {
		this.eventBeginTime = eventBeginTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public VehicleRegistrationIdentification getEventVehicleRegistration() {
		return eventVehicleRegistration;
	}

	public void setEventVehicleRegistration(
			VehicleRegistrationIdentification eventVehicleRegistration) {
		this.eventVehicleRegistration = eventVehicleRegistration;
	}
	
}
