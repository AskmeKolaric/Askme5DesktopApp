/**
 * 
 */
package TachografReaderUI.file.driverCardBlock.subBlocks;

import java.util.Arrays;
import java.util.Date;
import TachografReaderUI.file.driverCardBlock.DriverCardSizes;
import TachografReaderUI.helpers.OdometerShort;
import TachografReaderUI.helpers.RealTime;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 2.84. PlaceRecord
 * Informaci�n relativa al lugar donde comienza o termina un per�odo de trabajo diario (requisitos 087, 202, 221).
 * PlaceRecord ::= SEQUENCE {
 * entryTime TimeReal,
 * entryTypeDailyWorkPeriod EntryTypeDailyWorkPeriod,
 * dailyWorkPeriodCountry NationNumeric,
 * dailyWorkPeriodRegion RegionNumeric,
 * vehicleOdometerValue OdometerShort
 * }
 * entryTime es una fecha y una hora relacionadas con la entrada.
 * entryTypeDailyWorkPeriod es el tipo de entrada.
 * dailyWorkPeriodCountry es el pa�s introducido.
 * dailyWorkPeriodRegion es la regi�n introducida
 * vehicleOdometerValue es la lectura del cuentakil�metros en el momento de introducir el lugar.
 * 
 * @author Andres Carmona Gil
 * @version 0.0.1
 *
 */

public class PlaceRecord  {

	    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
		private Date entryTime; 

		private  String entryTypeDailyWorkPeriod;

		private  String dailyWorkPeriodCountry;

		private  String dailyWorkPeriodRegion;

		private  int vehicleOdometerValue;
		
		
		
		public PlaceRecord() {}

		/**
		 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
		 * segun  el tipo de propiedad.
		 * @param bytes array de bytes
		 */
		public PlaceRecord(byte[] bytes) {
			int start=0;
			this.entryTime= RealTime.getRealTime(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.ENTRYTIME.getMax()));
			EntryTypeDailyWorkPeriod etdw = new EntryTypeDailyWorkPeriod(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.ENTRYTYPEDAILYWORKPERIOD.getMax()));
			this.entryTypeDailyWorkPeriod= etdw.getEntryTypeDailyWorkPeriod();
			NationNumeric nn=new NationNumeric(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.DAILYWORKPERIODCOUNTRY.getMax()));
			this.dailyWorkPeriodCountry=nn.getNationNumeric();					
			this.dailyWorkPeriodRegion=RegionNumeric.getRegionNumeric(bytes[start]);
			start+= DriverCardSizes.DAILYWORKPERIODREGION.getMax();
			this.vehicleOdometerValue= OdometerShort.getOdometerShort(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.VEHICLEODOMETERVALUE.getMax()));
		}

		/**
		 * Obtiene una fecha y una hora relacionadas con la entrada.
		 * @return the EntryTime
		 */
		public Date getEntryTime() {
			return entryTime;
		}

		/**
		 * Asigna una fecha y una hora relacionadas con la entrada.
		 * @param entryTime
		 */
		public void setEntryTime(Date entryTime) {
			this.entryTime = entryTime;
		}

		/**
		 * Obtiene el tipo de entrada.
		 * @return the EntryTypeDailyWorkPeriod
		 */
		public String getEntryTypeDailyWorkPeriod() {
			return entryTypeDailyWorkPeriod;
		}

		/**
		 * Asigna el tipo de entrada.
		 * @param entryTypeDailyWorkPeriod
		 */
		public void setEntryTypeDailyWorkPeriod(
				String entryTypeDailyWorkPeriod) {
			this.entryTypeDailyWorkPeriod = entryTypeDailyWorkPeriod;
		}

		/**
		 * Obtiene el pais introducido
		 * @return the DailyWorkPeriodCountry
		 */
		public String getDailyWorkPeriodCountry() {
			return dailyWorkPeriodCountry;
		}

		/**
		 * Asigna el pais introducido
		 * @param dailyWorkPeriodCountry
		 */
		public void setDailyWorkPeriodCountry(String dailyWorkPeriodCountry) {
			this.dailyWorkPeriodCountry = dailyWorkPeriodCountry;
		}

		/**
		 * Obtiene la region introducida.
		 * @return the DailyWorkPeriodRegion
		 */
		public String getDailyWorkPeriodRegion() {
			return dailyWorkPeriodRegion;
		}

		/**
		 * Asigna la region introducida.
		 * @param dailyWorkPeriodRegion
		 */
		public void setDailyWorkPeriodRegion(String dailyWorkPeriodRegion) {
			this.dailyWorkPeriodRegion = dailyWorkPeriodRegion;
		}

		/**
		 * Obtiene la lectura del cuentakil�metros en el momento de introducir el lugar.
		 * @return VehicleOdometerValue
		 */
		public int getVehicleOdometerValue() {
			return vehicleOdometerValue;
		}

		/**
		 * Asgina la lectura del cuentakil�metros en el momento de introducir el lugar.
		 * @param vehicleOdometerValue
		 */
		public void setVehicleOdometerValue(int vehicleOdometerValue) {
			this.vehicleOdometerValue = vehicleOdometerValue;
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "\nPlaceRecord [entryTime=" + entryTime
					+ ", entryTypeDailyWorkPeriod=" + entryTypeDailyWorkPeriod.toString()
					+ ", dailyWorkPeriodCountry=" + dailyWorkPeriodCountry
					+ ", dailyWorkPeriodRegion=" + dailyWorkPeriodRegion
					+ ", vehicleOdometerValue=" + vehicleOdometerValue + "]";
		}


		
		
}
