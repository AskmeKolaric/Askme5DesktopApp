package TachografReaderUI.models;

public enum Fid {

    MF("3f,00"),  //MF Main file
        EF_ICC("00,02"), // EF Elementary file
        EF_IC("00,05"),
            DF_TACHOGRAPF("05,00"),  //DF Dedicated archive. One DF can contain other (EF or DF)
                EF_APPLICATION_IDENTIFICATION("05,01"),  // signature
                EF_CARD_CERTIFICATE("C1,00"),
                EF_CA_CERTIFICATE("C1,08"),
                EF_IDENTIFICATION("05,20"),			// signature
                EF_CARD_DOWNLOAD("05,0E"),
                EF_DRIVING_LICENSE_INFO("05,21"),	//signature
                EF_EVENTS_DATA("05,02"),			//signature
                EF_FAULTS_DATA("05,03"),			//signature
                EF_DRIVER_ACTIVITY_DATA("05,04"),	//signature
                EF_VEHICLES_USED("05,05"),			//signature
                EF_PLACES("05,06"),					//signature
                EF_CURRENT_USAGE("05,07"),			//signature
                EF_CONTROL_ACTIVITY_DATA("05,08"),	//signature
                EF_SPECIFIC_CONDITIONS("05,22");	//signature

    private final String id;

    Fid(String id){
        this.id = id;
    }

    public String getId(){return this.id;}
}
