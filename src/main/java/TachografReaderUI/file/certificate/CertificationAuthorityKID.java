package TachografReaderUI.file.certificate;


import TachografReaderUI.file.driverCardBlock.subBlocks.NationNumeric;
import TachografReaderUI.helpers.Number;
import TachografReaderUI.helpers.OctetString;

import java.util.Arrays;

public class CertificationAuthorityKID {

    private String nationNumeric;

    private String nationAlpha;

    private int keySerialNumber;

    private String additionalInfo;

    private String caIdentifier;

    public CertificationAuthorityKID() {
    }

    public CertificationAuthorityKID(byte[] datos) {
        int start=0;
        NationNumeric nn=new NationNumeric(Arrays.copyOfRange(datos, start, start+=1));
        this.nationNumeric=nn.getNationNumeric();
        NationAlpha na=new NationAlpha(Arrays.copyOfRange(datos, start, start+=3));
        this.nationAlpha=na.getNationAlpha();
        this.keySerialNumber= Number.getNumber(Arrays.copyOfRange(datos, start, start+=1));
        OctetString os=new OctetString(Arrays.copyOfRange(datos, start, start+=2));
        this.additionalInfo=os.getOctetString();
        os=new OctetString(Arrays.copyOfRange(datos, start, start+=1));
        this.caIdentifier=os.getOctetString();
    }

    public String getNationNumeric() {
        return nationNumeric;
    }

    public void setNationNumeric(String nationNumeric) {
        this.nationNumeric = nationNumeric;
    }

    public String getNationAlpha() {
        return nationAlpha;
    }

    public void setNationAlpha(String nationAlpha) {
        this.nationAlpha = nationAlpha;
    }

    public int getKeySerialNumber() {
        return keySerialNumber;
    }

    public void setKeySerialNumber(int keySerialNumber) {
        this.keySerialNumber = keySerialNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCaIdentifier() {
        return caIdentifier;
    }

    public void setCaIdentifier(String caIdentifier) {
        this.caIdentifier = caIdentifier;
    }
}
