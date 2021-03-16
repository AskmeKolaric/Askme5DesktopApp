package TachografReaderUI.models;

import java.util.HashMap;

public class CardStructure {
    
    private HashMap<String, String> structure;

    public CardStructure() {
    }

    public HashMap<String, String> getStructure() {
        structure = new HashMap<>();
        structure.put("00,02", "Card ICC identification");
        structure.put("00,05", "Card chip identification");
        structure.put("05,01", "Application identification");
        structure.put("C1,00", "Card certificate");
        structure.put("C1,08", "CA certificate");
        structure.put("05,20", "Card and card holder identification");
        structure.put("05,0E", "Card download");
        structure.put("05,21", "Driving license information");
        structure.put("05,02", "Events data");
        structure.put("05,03", "Faults data");
        structure.put("05,04", "Driver activity data");
        structure.put("05,05", "Vehicles used");
        structure.put("05,06", "Places");
        structure.put("05,07", "Current usage");
        structure.put("05,08", "Control activity data");
        structure.put("05,22", "Specific conditions");
        return structure;
    }
}
