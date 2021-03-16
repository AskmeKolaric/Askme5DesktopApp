/**
 * 
 */
package TachografReaderUI.file;

import java.io.IOException;
import java.util.*;

import TachografReaderUI.file.certificate.Signature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import TachografReaderUI.file.driverCardBlock.CardControlActivityDataRecord;
import TachografReaderUI.file.driverCardBlock.CardDrivingLicenceInformation;
import TachografReaderUI.file.driverCardBlock.CardPlaceDailyWorkPeriod;
import TachografReaderUI.file.driverCardBlock.DriverCardApplicationIdentification;
import TachografReaderUI.file.driverCardBlock.Fid;
import TachografReaderUI.helpers.Number;
import TachografReaderUI.file.driverCardBlock.CardCertificate;
import TachografReaderUI.file.driverCardBlock.CardChipIdentification;
import TachografReaderUI.file.driverCardBlock.CardCurrentUse;
import TachografReaderUI.file.driverCardBlock.CardDriverActivity;
import TachografReaderUI.file.driverCardBlock.CardEventData;
import TachografReaderUI.file.driverCardBlock.CardFaultData;
import TachografReaderUI.file.driverCardBlock.CardIccIdentification;
import TachografReaderUI.file.driverCardBlock.CardIdentification;
import TachografReaderUI.file.driverCardBlock.CardVehiclesUsed;
import TachografReaderUI.file.driverCardBlock.LastCardDownload;
import TachografReaderUI.file.driverCardBlock.MemberStateCertificate;
import TachografReaderUI.file.driverCardBlock.SpecificConditionRecord;


public class CardBlockFile {
	

	private String nameFile=null;
	// block file of driver card
	@JsonIgnore
	private CardIccIdentification icc = null;
	@JsonIgnore
	private CardChipIdentification ic = null;
	@JsonIgnore
	private DriverCardApplicationIdentification application_identification = null;
	@JsonIgnore
	private CardCertificate card_certificate = null;
	@JsonIgnore
	private MemberStateCertificate ca_certificate = null;
	@JsonIgnore
	private CardIdentification identification = null;
	@JsonIgnore
	private LastCardDownload card_download = null;
	@JsonIgnore
	private CardDrivingLicenceInformation driving_lincense_info = null;
	@JsonIgnore
	private CardEventData event_data = null;
	@JsonIgnore
	private CardFaultData fault_data = null;
	@JsonIgnore
	private CardDriverActivity driver_activity_data = null;
	@JsonIgnore
	private CardVehiclesUsed vehicles_used = null;
	@JsonIgnore
	private CardPlaceDailyWorkPeriod places = null;
	@JsonIgnore
	private CardCurrentUse current_usage = null;
	@JsonIgnore
	private CardControlActivityDataRecord control_activity_data = null;
	@JsonIgnore
	private SpecificConditionRecord specific_conditions = null;
	@JsonIgnore
	private byte[] fileArray;

	private boolean sid=false;

	private HashMap<String, Block> listBlock;

	public CardBlockFile() {

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CardBlockFile(byte[] bytes) throws Exception {
		HashMap<String, Block> lista = new HashMap();

		this.listBlock=new HashMap();
		try {
			int start=0;
			while( start < bytes.length){
				int fid = Number.getNumber(Arrays.copyOfRange(bytes, start, start += 2));
				if(this.existe_Fid(fid)){
					byte tipo = bytes[start];
					start += 1;
					Integer longitud = (int) Number.getNumber(Arrays.copyOfRange(bytes, start, start += 2));
					byte[] datos = new byte[longitud];
					datos = Arrays.copyOfRange(bytes, start, start += longitud);

					if (tipo == 0) {
						Block block = (Block) FactoriaBloques.getFactoria(fid, datos);
						if (block != null) {
							this.listBlock.put(block.getFID(), (Block) block);							
							
						}else{
							this.listBlock.get(nameBlock(fid)).setSignature(new Signature(datos));
						}
					}else{
						this.listBlock.get(nameBlock(fid)).setSignature(new Signature(datos));
					}
				}else{
					throw new Error("Block not found");
				}
				
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		this.asignarBloques();

		
		
	}

	private void asignarBloques() {
		
		this.icc = (CardIccIdentification) this.listBlock.get(Fid.EF_ICC
				.toString());
		this.ic = (CardChipIdentification) this.listBlock.get(Fid.EF_IC
				.toString());				
		this.application_identification = (DriverCardApplicationIdentification) this.listBlock
				.get(Fid.EF_APPLICATION_IDENTIFICATION.toString());				
		this.card_certificate = (CardCertificate) this.listBlock
				.get(Fid.EF_CARD_CERTIFICATE.toString());
		this.ca_certificate = (MemberStateCertificate) this.listBlock
				.get(Fid.EF_CA_CERTIFICATE.toString());
		this.identification = (CardIdentification) this.listBlock
				.get(Fid.EF_IDENTIFICATION.toString());
		this.card_download = (LastCardDownload) this.listBlock
				.get(Fid.EF_CARD_DOWNLOAD.toString());
		this.driving_lincense_info = (CardDrivingLicenceInformation) this.listBlock
				.get(Fid.EF_DRIVING_LICENSE_INFO.toString());
		this.event_data = (CardEventData) this.listBlock
				.get(Fid.EF_EVENTS_DATA.toString());
		this.fault_data = (CardFaultData) this.listBlock
				.get(Fid.EF_FAULTS_DATA.toString());
		this.driver_activity_data = (CardDriverActivity) this.listBlock
				.get(Fid.EF_DRIVER_ACTIVITY_DATA.toString());		
		this.vehicles_used = (CardVehiclesUsed) this.listBlock
				.get(Fid.EF_VEHICLES_USED.toString());
		this.vehicles_used.setNoOfCardVehicleRecords(this.application_identification.getNoOfCardVehicleRecords().getNoOfCardVehicleRecords());
		this.places = (CardPlaceDailyWorkPeriod) this.listBlock
				.get(Fid.EF_PLACES.toString());		
		if (this.places!=null)
		this.places.setNoOfCArdPlaceRecords(this.application_identification
				.getNoOfCardPlaceRecords().getNoOfCardPlaceRecords());
		this.current_usage = (CardCurrentUse) this.listBlock
				.get(Fid.EF_CURRENT_USAGE.toString());
		this.control_activity_data = (CardControlActivityDataRecord) this.listBlock
				.get(Fid.EF_CONTROL_ACTIVITY_DATA.toString());
		this.specific_conditions = (SpecificConditionRecord) this.listBlock
				.get(Fid.EF_SPECIFIC_CONDITIONS.toString());
		
		
		
	}


	private String nameBlock(int fid){
		String str="";
		switch (fid) {
			case 0x0002:
				str=Fid.EF_ICC.toString();
				break;
			case 0x0005:
				str=Fid.EF_IC.toString();
				break;
			case 0x0501:
				str=Fid.EF_APPLICATION_IDENTIFICATION.toString();
				break;
			case 0xc100:
				str=Fid.EF_CARD_CERTIFICATE.toString();
				break;
			case 0xc108:
				str=Fid.EF_CA_CERTIFICATE.toString();
				break;
			case 0x0520:
				str=Fid.EF_IDENTIFICATION.toString();
				break;
			case 0x050E:
				str=Fid.EF_CARD_DOWNLOAD.toString();
				break;
			case 0x0521:
				str=Fid.EF_DRIVING_LICENSE_INFO.toString();
				break;
			case 0x0502:
				str=Fid.EF_EVENTS_DATA.toString();
				break;
			case 0x0503: // Faults data
				str=Fid.EF_FAULTS_DATA.toString();
				break;
			case 0x0504: // Driver activity data
				str=Fid.EF_DRIVER_ACTIVITY_DATA.toString();
				break;
			case 0x0505:// vehicles uses
				str=Fid.EF_VEHICLES_USED.toString();
				break;
			case 0x0506: // Places
				str=Fid.EF_PLACES.toString();
				break;
			case 0x0507: // Currents usage
				str=Fid.EF_CURRENT_USAGE.toString();
				break;
			case 0x0508: // Control activity data
				str=Fid.EF_CONTROL_ACTIVITY_DATA.toString();
				break;
			case 0x0522:
				str=Fid.EF_SPECIFIC_CONDITIONS.toString();
				break;
			default:
				break;
		}
		return str;
	}

	private boolean existe_Fid(int fid) {
		Fid[] list_fid = Fid.values();
		boolean ok = false;
		for (int i = 0; i < list_fid.length; i++) {
			if (list_fid[i].getId() == fid) {
				ok = true;
				i = list_fid.length;
			}
		}

		return ok;
	}


	public CardIccIdentification getIcc() {
		return icc;
	}


	public void setIcc(CardIccIdentification icc) {
		this.icc = icc;
	}

	public CardChipIdentification getIc() {
		return ic;
	}

	public void setIc(CardChipIdentification ic) {
		this.ic = ic;
	}

	public DriverCardApplicationIdentification getApplication_identification() {
		return application_identification;
	}

	public void setApplication_identification(
			DriverCardApplicationIdentification application_identification) {
		this.application_identification = application_identification;
	}

	public CardCertificate getCard_certificate() {
		return card_certificate;
	}

	public void setCard_certificate(CardCertificate card_certificate) {
		this.card_certificate = card_certificate;
	}

	public MemberStateCertificate getCa_certificate() {
		return ca_certificate;
	}

	public void setCa_certificate(MemberStateCertificate ca_certificate) {
		this.ca_certificate = ca_certificate;
	}

	public CardIdentification getIdentification() {
		return identification;
	}

	public void setIdentification(CardIdentification identification) {
		this.identification = identification;
	}

	public LastCardDownload getCard_download() {
		return card_download;
	}

	public void setCard_download(LastCardDownload card_download) {
		this.card_download = card_download;
	}

	public CardDrivingLicenceInformation getDriving_lincense_info() {
		return driving_lincense_info;
	}

	public void setDriving_lincense_info(
			CardDrivingLicenceInformation driving_lincense_info) {
		this.driving_lincense_info = driving_lincense_info;
	}

	public CardEventData getEvent_data() {
		return event_data;
	}


	public void setEvent_data(CardEventData event_data) {
		this.event_data = event_data;
	}

	public CardFaultData getFault_data() {
		return fault_data;
	}


	public void setFault_data(CardFaultData fault_data) {
		this.fault_data = fault_data;
	}

	public CardDriverActivity getDriver_activity_data() {
		return driver_activity_data;
	}

	public void setDriver_activity_data(CardDriverActivity driver_activity_data) {
		this.driver_activity_data = driver_activity_data;
	}

	public CardVehiclesUsed getVehicles_used() {
		return vehicles_used;
	}

	public void setVehicles_used(CardVehiclesUsed vehicles_used) {
		this.vehicles_used = vehicles_used;
	}

	public CardPlaceDailyWorkPeriod getPlaces() {
		return places;
	}

	public void setPlaces(CardPlaceDailyWorkPeriod places) {
		this.places = places;
	}

	public CardCurrentUse getCurrent_usage() {
		return current_usage;
	}

	public void setCurrent_usage(CardCurrentUse current_usage) {
		this.current_usage = current_usage;
	}

	public CardControlActivityDataRecord getControl_activity_data() {
		return control_activity_data;
	}

	public void setControl_activity_data(
			CardControlActivityDataRecord control_activity_data) {
		this.control_activity_data = control_activity_data;
	}

	public SpecificConditionRecord getSpecific_conditions() {
		return specific_conditions;
	}

	public void setSpecific_conditions(
			SpecificConditionRecord specific_conditions) {
		this.specific_conditions = specific_conditions;
	}

	@Override
	public String toString() {
		return "CardBlockFile [nameFile=" + nameFile + ", icc=" + icc + ", ic=" + ic + ", application_identification="
				+ application_identification + ", card_certificate=" + card_certificate + ", ca_certificate="
				+ ca_certificate + ", identification=" + identification + ", card_download=" + card_download
				+ ", driving_lincense_info=" + driving_lincense_info + ", event_data=" + event_data + ", fault_data="
				+ fault_data + ", driver_activity_data=" + driver_activity_data + ", vehicles_used=" + vehicles_used
				+ ", places=" + places + ", current_usage=" + current_usage + ", control_activity_data="
				+ control_activity_data + ", specific_conditions=" + specific_conditions + ", sid=" + sid
				+ ", listBlock=" + listBlock + "]";
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public boolean isSid() {
		return sid;
	}

	public void setSid(boolean sid) {
		this.sid = sid;
	}

	public HashMap<String, Block> getListBlock() {
		return listBlock;
	}

	public void setListBlock(HashMap<String, Block> listBlock) {
		this.listBlock = listBlock;
	}

	public byte[] getFileArray() {
		return fileArray;
	}

	public void setFileArray(byte[] fileArray) {
		this.fileArray = fileArray;
	}

	@JsonIgnore
	public String getJson() {
		ObjectMapper mapper = new ObjectMapper();
		String str = "";
		try {
			str = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		return str;
	}

}
