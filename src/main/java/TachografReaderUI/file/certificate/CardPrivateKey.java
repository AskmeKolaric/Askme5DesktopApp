package TachografReaderUI.file.certificate;

import java.util.Arrays;


public class CardPrivateKey {

    private RSAKeyPrivateExponent CardPrivateKey;  //RSAKeyPrivateExponent

    public CardPrivateKey() {
    }
    public CardPrivateKey(byte[] datos) {
        this.CardPrivateKey=new RSAKeyPrivateExponent(Arrays.copyOfRange(datos, 0, 128));
    }

    public RSAKeyPrivateExponent getCardPrivateKey() {
        return CardPrivateKey;
    }

    public void setCardPrivateKey(RSAKeyPrivateExponent cardPrivateKey) {
        CardPrivateKey = cardPrivateKey;
    }
}
