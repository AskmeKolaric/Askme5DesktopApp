package TachografReaderUI.file.certificate;

import TachografReaderUI.helpers.OctetString;

import java.math.BigInteger;

public class RSAKeyModulus {

    private String rsaKeyModulus;

    private BigInteger rsaKeyModulus_bg;

    private byte[] rsaKeyModulusBytes;
    public RSAKeyModulus() {
    }
    public RSAKeyModulus(byte[] datos){
        this.rsaKeyModulusBytes = datos;
        OctetString os=new OctetString(datos);
        this.rsaKeyModulus=os.getOctetString();

        this.rsaKeyModulus_bg=new BigInteger(1,datos);


    }
    public String getRsaKeyModulus() {
        return rsaKeyModulus;
    }

    public void setRsaKeyModulus(String rsaKeyModulus) {
        this.rsaKeyModulus = rsaKeyModulus;
    }

    public BigInteger getRsaKeyModulus_bg() {
        return rsaKeyModulus_bg;
    }

    public void setRsaKeyModulus_bg(BigInteger rsaKeyModulus_bg) {
        this.rsaKeyModulus_bg = rsaKeyModulus_bg;
    }

    public byte[] getRsaKeyModulusBytes() {
        return rsaKeyModulusBytes;
    }

    public void setRsaKeyModulusBytes(byte[] rsaKeyModulusBytes) {
        this.rsaKeyModulusBytes = rsaKeyModulusBytes;
    }
}
