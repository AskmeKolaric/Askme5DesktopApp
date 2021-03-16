package TachografReaderUI.file.certificate;

import java.util.Arrays;

public class VuPublicKey {

    private PublicKey publicKey;

    public VuPublicKey() {

    }

    public VuPublicKey(byte[] datos){
        this.publicKey=new PublicKey(Arrays.copyOfRange(datos, 0, 136));
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
