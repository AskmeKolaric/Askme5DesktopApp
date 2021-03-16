package TachografReaderUI.file;


import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import TachografReaderUI.file.vuBlocks.Activity;
import TachografReaderUI.file.vuBlocks.EventsFaults;
import TachografReaderUI.file.vuBlocks.ListActivity;
import TachografReaderUI.file.vuBlocks.Resumen;
import TachografReaderUI.file.vuBlocks.Speed;
import TachografReaderUI.file.vuBlocks.Technical;
import TachografReaderUI.file.vuBlocks.Trep;
import TachografReaderUI.helpers.Number;

public class VuBlockFile {


	private HashMap<String,Block> listBlock;	
	@JsonIgnore
	private ListActivity listActivity = null; //VU_ACTIVITY(0X7602),
	@JsonIgnore
	private EventsFaults eventFault = null; //VU_EVENT_FAULT(0X7603),
	@JsonIgnore
	private Speed speed = null; //VU_SPEED(0X7604),
	@JsonIgnore
	private Technical technical = null; //VU_TECHNICAL(0X7605);
	@JsonIgnore
	private Resumen resumen = null; // VU_RESUMEN(0X7601),

	public VuBlockFile(){
		
	}
	
	public VuBlockFile(byte[] datos) throws Exception{
		int start=0;
		ListActivity listActivity=new ListActivity();
		this.listBlock=new HashMap();

		while(start<datos.length){	

			if(datos[start]==0x76){
					start+=1;					
					if(start<datos.length){
						if(datos[start]>0x00 && datos[start]<0x06){

						    int word = Number.getNumber(Arrays.copyOfRange(datos, start-1, start+1));
							Block b=(Block) FactoriaBloques.getFactoria(word, Arrays.copyOfRange(datos, start+1, datos.length));

							if(b.getTRED()== Trep.VU_ACTIVITY.toString()){
								listActivity.add((Activity)b);
							}else{
								this.listBlock.put(b.getTRED(), b);
							}
							start+=b.getSize();
						}
					}
			}else{				
				start+=1;	
			}
			
		}

		this.listBlock.put("VU_ACTIVITY", listActivity);
		setTrep();
	}

	private void setTrep(){
		
		this.resumen = (Resumen)this.listBlock.get(Trep.VU_RESUMEN.toString());
		this.listActivity=(ListActivity) this.listBlock.get(Trep.VU_ACTIVITY.toString());
		this.eventFault=(EventsFaults) this.listBlock.get(Trep.VU_EVENT_FAULT.toString());
		this.speed=(Speed) this.listBlock.get(Trep.VU_SPEED.toString());
		this.technical=(Technical) this.listBlock.get(Trep.VU_TECHNICAL.toString());
	}

	public HashMap<String, Block> getListBlock() {
		return listBlock;
	}

	public void setListBlock(HashMap<String, Block> listBlock) {
		this.listBlock = listBlock;
	}
	
	public Resumen getResumen() {
		return resumen;
	}
	
	public void setResumen(Resumen resumen) {
		this.resumen = resumen;
	}
	
	public EventsFaults getEventFault() {
		return eventFault;
	}
	
	public void setEventFault(EventsFaults eventFault) {
		this.eventFault = eventFault;
	}
	
	public Speed getSpeed() {
		return speed;
	}
	
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}
	
	public Technical getTechnical() {
		return technical;
	}
	
	public void setTechnical(Technical technical) {
		this.technical = technical;
	}
	
	public ListActivity getListActivity() {
		return listActivity;
	}

	public void setListActivity(ListActivity listActivity) {
		this.listActivity = listActivity;
	}
	
	@Override
	public String toString() {
		return "VuBlockFile [listBlock=" + listBlock + ", resumen=" + resumen + ", activity=" + listActivity
				+ ", eventFault=" + eventFault + ", speed=" + speed + ", technical=" + technical + "]";
	}
	
}
