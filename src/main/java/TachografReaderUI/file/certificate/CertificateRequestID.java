package TachografReaderUI.file.certificate;


import TachografReaderUI.file.driverCardBlock.subBlocks.ManufacturerCode;
import TachografReaderUI.helpers.BCDString;
import TachografReaderUI.helpers.Number;
import TachografReaderUI.helpers.OctetString;

import java.util.Arrays;

public class CertificateRequestID {

    private int requestSerialNumber;

    private String requestMonthYear;

    private String crIdentifier;

    private String manufactureCode;

    public CertificateRequestID() {
    }
    public CertificateRequestID(byte[] datos) {
        int start=0;
        this.requestSerialNumber= Number.getNumber(Arrays.copyOfRange(datos, start, start+= 1));
        this.requestMonthYear= BCDString.BCDtoString(Arrays.copyOfRange(datos, start, start+= 2));
        OctetString os=new OctetString(Arrays.copyOfRange(datos, start, start+= 1));
        this.crIdentifier= os.getOctetString();
        ManufacturerCode mc=new ManufacturerCode(Arrays.copyOfRange(datos, start, start+= 1));
        this.manufactureCode= mc.getManufactureCode();
    }

    public int getRequestSerialNumber() {
        return requestSerialNumber;
    }

    public void setRequestSerialNumber(int requestSerialNumber) {
        this.requestSerialNumber = requestSerialNumber;
    }

    public String getRequestMonthYear() {
        return requestMonthYear;
    }

    public void setRequestMonthYear(String requestMonthYear) {
        this.requestMonthYear = requestMonthYear;
    }

    public String getCrIdentifier() {
        return crIdentifier;
    }

    public void setCrIdentifier(String crIdentifier) {
        this.crIdentifier = crIdentifier;
    }

    public String getManufactureCode() {
        return manufactureCode;
    }

    public void setManufactureCode(String manufactureCode) {
        this.manufactureCode = manufactureCode;
    }
}
