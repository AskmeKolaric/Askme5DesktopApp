package TachografReaderUI.file.certificate;

import java.util.Arrays;

public class CardPublicKey {

    private PublicKey cardPublicKey;
    public CardPublicKey(){

    }
    public CardPublicKey(byte[] datos) {

        this.cardPublicKey = new PublicKey(Arrays.copyOfRange(datos, 0, 136));
    }

    public PublicKey getCardPublicKey() {
        return cardPublicKey;
    }

    public void setCardPublicKey(PublicKey cardPublicKey) {
        this.cardPublicKey = cardPublicKey;
    }
}
