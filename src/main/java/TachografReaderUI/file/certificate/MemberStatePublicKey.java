package TachografReaderUI.file.certificate;

import java.util.Arrays;

public class MemberStatePublicKey {

    private PublicKey publicKey;

    public MemberStatePublicKey() {
    }
    public MemberStatePublicKey(byte[] datos) {
        this.publicKey=new PublicKey(Arrays.copyOfRange(datos, 0, 136));
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
