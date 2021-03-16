package TachografReaderUI.file.certificate;

import TachografReaderUI.file.driverCardBlock.subBlocks.ExtendedSerialNumber;

import java.util.Arrays;

public class KeyIdentifier {  //21

    private ExtendedSerialNumber extendedSerialNumber; // 8

    private CertificateRequestID certificateRequestID; // 5 vu

    private CertificationAuthorityKID certificationAuthorityKID; //8 card

    public KeyIdentifier(){

    }

    public KeyIdentifier(byte[] datos) {
        int start=0;
        //this.extendedSerialNumber=new ExtendedSerialNumber(Arrays.copyOfRange(datos, start, start+=8));
        //this.certificateRequestID=new CertificateRequestID(Arrays.copyOfRange(datos, start, start+=5)); //VU
        this.certificationAuthorityKID=new CertificationAuthorityKID(Arrays.copyOfRange(datos, start, start+=8));

    }

    public ExtendedSerialNumber getExtendedSerialNumber() {
        return extendedSerialNumber;
    }

    public void setExtendedSerialNumber(ExtendedSerialNumber extendedSerialNumber) {
        this.extendedSerialNumber = extendedSerialNumber;
    }

    public CertificateRequestID getCertificateRequestID() {
        return certificateRequestID;
    }

    public void setCertificateRequestID(CertificateRequestID certificateRequestID) {
        this.certificateRequestID = certificateRequestID;
    }

    public CertificationAuthorityKID getCertificationAuthorityKID() {
        return certificationAuthorityKID;
    }

    public void setCertificationAuthorityKID(CertificationAuthorityKID certificationAuthorityKID) {
        this.certificationAuthorityKID = certificationAuthorityKID;
    }
}
