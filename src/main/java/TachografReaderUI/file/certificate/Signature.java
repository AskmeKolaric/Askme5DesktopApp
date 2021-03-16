package TachografReaderUI.file.certificate;

import TachografReaderUI.file.error.ExceptionSignatureContent;
import TachografReaderUI.file.error.ExceptionSignatureHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Signature {

    private String signature;
    private byte[] signature_bytes;
    private boolean verified=false;
    public Signature(byte[] bytes) {
        this.signature_bytes=bytes;
        StringBuffer sb=new StringBuffer();
        for (byte b:bytes){
            sb.append(String.format("%x",b));
        }
        this.signature = sb.toString();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public byte[] getSignature_bytes() {
        return signature_bytes;
    }

    public void setSignature_bytes(byte[] signature_bytes) {
        this.signature_bytes = signature_bytes;
    }


    public boolean verify(byte[] datas,PublicKey publicKey,String block) throws NoSuchAlgorithmException, ExceptionSignatureHash, ExceptionSignatureContent {
        boolean verify=true;
        byte[] sr=publicKey.recover(this.getSignature_bytes());
        byte[] H= Arrays.copyOfRange(sr,107,128);
        byte[] random={0x30,0x21,0x30,0x09,0x06,0x05,0x2b,0x0e,0x03,0x02,0x1a,0x05,0x00,0x04,0x14};
        MessageDigest md=MessageDigest.getInstance("SHA-1");
        md.update(datas);
        byte[] hashc=md.digest();
        for (int i=0;i<hashc.length-1;i++){
            if (hashc[i]!=H[i]){
                verify=false;
                throw new ExceptionSignatureHash("hash datas not equal hash in signature for block"+block,new Throwable("signature (128 bytes) = content(106 bytes) + hash (20 bytes)"));
                //throw new Error("hash file certificate not equal");
            }
        }
        for (int i=1;i<91;i++){
            if(sr[i]!=-1){
                verify=false;
                throw new ExceptionSignatureContent("content signature not equals FF for block "+block,new Throwable(" signature = EQT.SK [00 || 01 || PS(91 bytes) || 00 || DER(SHA-1(datos))(15 bytes)] PS=FF"));
            }
        }
        for (int i=0;i<15;i++){
            if(sr[i+92]!=random[i]){
                verify=false;
                throw new ExceptionSignatureContent("content signature not equals FF for block"+block,new Throwable("DER(SHA-1({0x30,0x21,0x30,0x09,0x06,0x05,0x2b,0x0e,0x03,0x02,0x1a,0x05,0x00,0x04,0x14})"));
            }

        }

        this.verified=verify;
        return verify;
    }
}
