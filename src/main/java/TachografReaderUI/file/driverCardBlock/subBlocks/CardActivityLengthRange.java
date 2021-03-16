package TachografReaderUI.file.driverCardBlock.subBlocks;


import TachografReaderUI.helpers.Number;

public class CardActivityLengthRange {
	
	private int cardActivityLengthRange;

	public CardActivityLengthRange() {}

	public CardActivityLengthRange(byte[] datos) {
		this.cardActivityLengthRange = Number.getShort_16(datos);
	}

	public int getCardActivityLengthRange() {
		return cardActivityLengthRange;
	}

	public void setCardActivityLengthRange(short cardActivityLengthRange) {
		this.cardActivityLengthRange = cardActivityLengthRange;
	}		

	
}
