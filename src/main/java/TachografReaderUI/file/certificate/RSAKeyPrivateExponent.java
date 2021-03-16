package TachografReaderUI.file.certificate;

import TachografReaderUI.helpers.OctetString;

import java.util.Arrays;

public class RSAKeyPrivateExponent {

    private String RSAKeyPrivateExponent;

    public RSAKeyPrivateExponent() {

    }
    public RSAKeyPrivateExponent(byte[] datos) {
        OctetString os=new OctetString(Arrays.copyOfRange(datos, 0, 128));
        this.RSAKeyPrivateExponent=os.getOctetString();
    }

    public String getRSAKeyPrivateExponent() {
        return RSAKeyPrivateExponent;
    }

    public void setRSAKeyPrivateExponent(String RSAKeyPrivateExponent) {
        this.RSAKeyPrivateExponent = RSAKeyPrivateExponent;
    }
}
