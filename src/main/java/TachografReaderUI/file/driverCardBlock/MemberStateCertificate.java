/**
 * 
 */
package TachografReaderUI.file.driverCardBlock;

import TachografReaderUI.file.Block;
import TachografReaderUI.file.driverCardBlock.subBlocks.Certificate;

/**
 * 
 * 2.68. MemberStateCertificate
 * El certificado de la clave p�blica de un Estado miembro, expedido por la autoridad de certificaci�n europea.
 * MemberStateCertificate ::= Certificate
 * @author Negrero
 *
 */
public class MemberStateCertificate extends Block implements
		CardBlock {
	
		private Certificate certificate;
		
		
		public MemberStateCertificate() {}
		/**
		 * Constructor que asigna los bytes que le corresponda a cada propiedad y lo interpreta 
		 * segun  el tipo de propiedad.
		 * @param datos array de bytes
		 */
		public MemberStateCertificate(byte[] datos) {
			this.certificate=new Certificate(datos);
		}
		
		public String toString(){
			String cadena="";
			cadena=" certificado member state Certificate:"+this.certificate.getCertificate().getOctetString();
			return cadena;
		}

		/**
		 * Obtiene el certificado de la clave p�blica de un Estado miembro, expedido por la autoridad de certificaci�n europea.
		 * @return the certificate
		 */
		public Certificate getCertificate() {
			return certificate;
		}

		/**
		 * Asigna el certificado de la clave p�blica de un Estado miembro, expedido por la autoridad de certificaci�n europea.
		 * @param certificate the certificate to set
		 */
		public void setCertificate(Certificate certificate) {
			this.certificate = certificate;
		}
		

}
