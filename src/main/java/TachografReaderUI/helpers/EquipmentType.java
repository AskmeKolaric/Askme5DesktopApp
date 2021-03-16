/**
 * 
 */
package TachografReaderUI.helpers;

public class EquipmentType {
	

	private Short equipmentType;

	public EquipmentType(byte[] datos) {
		
		this.equipmentType=(short) datos[0];
	}

	public String getEquipmentType() {
		
		switch(this.equipmentType){
			case 1: return "Driver Card (1)";
			case 2: return "Workshop Card(2)";
			case 3: return "Control Card(3)";
			case 4: return "Company Card(4)";
			case 5: return "Manufacturing Card(5)";
			case 6: return "Vehicle Unit(6)";
			case 7: return "Motion Sensor(7)";
			default: return "RFU(8....255)";
		}
		
	}

	public void setEquipmentType(Short equipmentType) {
		this.equipmentType = equipmentType;
	}
	
	
}
