package TachografReaderUI.file.certificate;

import java.math.BigInteger;
import java.util.Arrays;

public class PublicKey {

    private RSAKeyModulus rsaKeyModulus;

    private RSAKeyPublicExponent rsaKeyPublicExponent;

    public PublicKey() {

    }
    public PublicKey(byte[] datos) {  // 136
        int start=0;
        this.rsaKeyModulus=new RSAKeyModulus(Arrays.copyOfRange(datos, start, start+=128)); //128
        this.rsaKeyPublicExponent=new RSAKeyPublicExponent(Arrays.copyOfRange(datos, start, start+=8)); //8

    }

    public RSAKeyModulus getRsaKeyModulus() {
        return rsaKeyModulus;
    }

    public void setRsaKeyModulus(RSAKeyModulus rsaKeyModulus) {
        this.rsaKeyModulus = rsaKeyModulus;
    }

    public RSAKeyPublicExponent getRsaKeyPublicExponent() {
        return rsaKeyPublicExponent;
    }

    public void setRsaKeyPublicExponent(RSAKeyPublicExponent rsaKeyPublicExponent) {
        this.rsaKeyPublicExponent = rsaKeyPublicExponent;
    }

    public byte[] recover(byte[] sign) {
        BigInteger signature= new BigInteger(1, sign);
        BigInteger X_CA_PK=signature.modPow(this.getRsaKeyPublicExponent().getRsaKeyPublicExponent_bc(),this.getRsaKeyModulus().getRsaKeyModulus_bg());
        return X_CA_PK.toByteArray();
    }
}
