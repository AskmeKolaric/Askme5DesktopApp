package TachografReaderUI.file.certificate;


import TachografReaderUI.helpers.EquipmentType;
import TachografReaderUI.helpers.OctetString;

import java.util.Arrays;

public class CertificateHolderAuthorisation {

    private String tachographApplicationId;

    private String equipmentType;

    public CertificateHolderAuthorisation() {
    }
    public CertificateHolderAuthorisation(byte[] datos) {
        int start=0;
        OctetString os=new OctetString(Arrays.copyOfRange(datos, start,start+= 6));//6
        this.tachographApplicationId=os.getOctetString();
        EquipmentType et=new EquipmentType(Arrays.copyOfRange(datos, start,start+= 1)); // 1
        this.equipmentType=et.getEquipmentType();
    }
    public String getTachographApplicationId() {
        return tachographApplicationId;
    }

    public void setTachographApplicationId(String tachographApplicationId) {
        this.tachographApplicationId = tachographApplicationId;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
}
