package TachografReaderUI.file.certificate;

import java.util.Arrays;

public class VuPrivateKey {

    private RSAKeyPrivateExponent rsaKeyPrivateExponent;

    public VuPrivateKey() {
    }
    public VuPrivateKey(byte[] datos) {
        this.rsaKeyPrivateExponent=new RSAKeyPrivateExponent(Arrays.copyOfRange(datos, 0, 128));
    }

    public RSAKeyPrivateExponent getRsaKeyPrivateExponent() {
        return rsaKeyPrivateExponent;
    }

    public void setRsaKeyPrivateExponent(RSAKeyPrivateExponent rsaKeyPrivateExponent) {
        this.rsaKeyPrivateExponent = rsaKeyPrivateExponent;
    }
}
