package TachografReaderUI.file.certificate;

import TachografReaderUI.helpers.OctetString;

import java.math.BigInteger;

public class RSAKeyPublicExponent {

    private String rsaKeyPublicExponent;

    private BigInteger rsaKeyPublicExponent_bc;

    private byte[] rsaKeyPublicExponentBytes;

    public RSAKeyPublicExponent(){
    }

    public RSAKeyPublicExponent(byte[] datos){
        this.rsaKeyPublicExponentBytes=datos;
        OctetString os=new OctetString(datos);
        this.rsaKeyPublicExponent=os.getOctetString();
        this.rsaKeyPublicExponent_bc=new BigInteger(1,datos);
    }

    public String getRsaKeyPublicExponent() {
        return rsaKeyPublicExponent;
    }

    public void setRsaKeyPublicExponent(String rsaKeyPublicExponent) {
        this.rsaKeyPublicExponent = rsaKeyPublicExponent;
    }

    public BigInteger getRsaKeyPublicExponent_bc() {
        return rsaKeyPublicExponent_bc;
    }

    public void setRsaKeyPublicExponent_bc(BigInteger rsaKeyPublicExponent_bc) {
        this.rsaKeyPublicExponent_bc = rsaKeyPublicExponent_bc;
    }

    public byte[] getRsaKeyPublicExponentBytes() {
        return rsaKeyPublicExponentBytes;
    }

    public void setRsaKeyPublicExponentBytes(byte[] rsaKeyPublicExponentBytes) {
        this.rsaKeyPublicExponentBytes = rsaKeyPublicExponentBytes;
    }
}
