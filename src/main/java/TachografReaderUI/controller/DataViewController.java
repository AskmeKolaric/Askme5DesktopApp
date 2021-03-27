package TachografReaderUI.controller;

import TachografReaderUI.models.User;
import TachografReaderUI.service.AppService;
import TachografReaderUI.service.AppServiceImpl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

import TachografReaderUI.file.FileDDD;
import TachografReaderUI.file.driverCardBlock.subBlocks.APDUCommand;
import TachografReaderUI.models.Fid;
import TachografReaderUI.utils.OperationHelper;

import javax.smartcardio.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DataViewController {

    private Parent login;

    private final ObjectProperty<User> user = new SimpleObjectProperty<>();

    public final ObjectProperty<User> userProperty() {
        return this.user;
    }

    public final User getUser() {
        return this.userProperty().get();
    }

    public final void setUser(final User user) {
        this.userProperty().set(user);
    }


    static int typeOfTachographCardId;
    static int noOfEventsPerType;
    static int noOfFaultsPerType;
    static int activityStructureLength;
    static int noOfCardVehicleRecords;
    static int noOfCardPlaceRecords;

    TerminalFactory factory = TerminalFactory.getDefault();
    List<CardTerminal> terminals = null;
    CardTerminal terminal = null;
    CardChannel channel = null;
    Card card = null;
    long time;
    FileDDD fileDDD;
    String fileName;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");

    public ProgressBar progressBar;
    public TextField textField;
    public Button dugme;
    public Label text;

    private final AppService appService = new AppServiceImpl();


    public void initialize() throws CardException {
        try {
            terminals = factory.terminals().list();
        } catch (CardException e1) {
            text.setText(e1.getMessage());
        }
        System.out.println("Terminals: " + terminals);
        // get the first terminal
        if (terminals.size() > 1) {
            terminal = terminals.get(0);
            card = terminal.connect("*");
            channel = card.getBasicChannel();
            text.setText("Card: " + card);
            dugme.setDisable(false);
            // establish a connection with the card
        } else {
            text.setText("Card is not present");
            textField.setText("Please connect your card Reader");
        }
    }

    @FXML
    private void logout() {
        user.set(null);
        System.out.println("out");
    }

    @FXML
    public void readTachografCard() throws Exception {

        byte[] headerBlock = new byte[5];
        byte[] b = null;
        ResponseAPDU r = null;

        ByteArrayOutputStream fileTGD = new ByteArrayOutputStream();
        for (Fid fid : Fid.values()) {
            progressBar.setProgress(progressBar.getProgress() + 1);
            text.setText(fid.name());
            boolean empty = false;
            System.out.println(fid.getId());
            if (!fid.getId().equals("3f,00") && !fid.getId().equals("05,00")) {
                switch (fid.getId()) {
                    case "00,02":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        b = readSelectedFileOnce(channel, 25);
                        break;
                    case "00,05":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        b = readSelectedFileOnce(channel, 8);
                        break;
                    case "05,01":
                        channel.transmit(new CommandAPDU(0x00, 0xA4, 0x04, 0x0C, OperationHelper.hexToByteAr("ff,54,41,43,48,4f"), 0x00, 0x06));
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFileOnce(channel, 10);
                        break;
                    case "C1,00":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        b = readSelectedFileOnce(channel, 194);
                        break;
                    case "C1,08":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        b = readSelectedFileOnce(channel, 194);
                        break;
                    case "05,20":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFileOnce(channel, 143);
                        break;
                    case "05,0E":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFileOnce(channel, 4);
                        break;
                    case "05,21":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFileOnce(channel, 53);
                        break;
                    case "05,02":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, noOfEventsPerType * 24 * 6);
                        break;
                    case "05,03":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, noOfFaultsPerType * 24 * 2);
                        break;
                    case "05,04":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, activityStructureLength + 4);
                        break;
                    case "05,05":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, noOfCardVehicleRecords * 31 + 2);
                        break;
                    case "05,06":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        ;
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, noOfCardPlaceRecords * 10 + 1);
                        break;
                    case "05,07":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, 19);
                        break;
                    case "05,08":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, 46);
                        break;
                    case "05,22":
                        channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr(fid.getId()), 0x00, 0x02));
                        performHashFile(r, channel);
                        b = readSelectedFile(channel, 280);
                        break;
                }

//                empty = isByteArrayEmpty(b);
//
//                if (!empty) {
                byte[] htba = OperationHelper.hexToByteAr(fid.getId());
                byte[] sizeByte = ByteBuffer.allocate(4).putInt(b.length).array();
                headerBlock[0] = htba[0];       // id file byte 1
                headerBlock[1] = htba[1];       // id file byte 2
                headerBlock[2] = 0;             // tipy file data
                headerBlock[3] = sizeByte[2];   // size file byte 1
                headerBlock[4] = sizeByte[3];   // size file byte 2
                fileTGD.write(headerBlock);
                fileTGD.write(b);

//                        // add signature file
                if (!fid.getId().equals("00,02") && !fid.getId().equals("00,05")
                        && !fid.getId().equals("C1,00") && !fid.getId().equals("C1,08")) {

                    b = signature(r, channel);
                    sizeByte = ByteBuffer.allocate(4).putInt(b.length).array();
                    headerBlock[0] = htba[0];
                    headerBlock[1] = htba[1];
                    headerBlock[2] = 1;
                    headerBlock[3] = sizeByte[2];
                    headerBlock[4] = sizeByte[3];
                    fileTGD.write(headerBlock);
                    fileTGD.write(b);
                }
//                    }
            }

        }
        try {
            time = Calendar.getInstance().getTimeInMillis();
            fileDDD = new FileDDD(fileTGD.toByteArray());

            String result = fileDDD.getJson();
            System.out.println(result);

            fileName = "ASKmE5_C_" + dateFormat.format(time) + "_" + fileDDD.getCardBlockFile().getIdentification().getDriverCardHolderIdentification().getCardHolderName().getHolderFirstNames().charAt(0) + "_" + fileDDD.getCardBlockFile().getIdentification().getDriverCardHolderIdentification().getCardHolderName().getHolderSurname() + "_" + fileDDD.getCardBlockFile().getIdentification().getCardNumber().toString() + ".ddd";

            fileName = fileName.replace(" ", "");

            String desktop = System.getProperty("user.home") + "/Desktop/Taho/";
            File file = new File(desktop + fileName);

            //TODO check where the request should happen!?
            appService.uploadFile(
                    "Marko-Test-Askme5", //todo check where this is initialized
                    fileDDD.getCardBlockFile().getIdentification().getCardNumber().toString(),//TODO check card numer
                    dateFormat.format(time),
                    file); // TODO test this!?

            OutputStream stream = new FileOutputStream(file);
            saveFileAddLastDownload(channel, fileTGD, r, time, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFileAddLastDownload(CardChannel channel, ByteArrayOutputStream output, ResponseAPDU r, long time, OutputStream stream) {
        byte[] timeHex = getTimestampBytes(time);

        try {
            r = channel.transmit(new CommandAPDU(0x00, 0xA4, 0x04, 0x0C, OperationHelper.hexToByteAr("ff,54,41,43,48,4f"), 0x00, 0x06));
            r = channel.transmit(new CommandAPDU(0x00, APDUCommand.SELECT_FILE.getCommand(), 0x02, 0x0C, OperationHelper.hexToByteAr("05,0E"), 0x00, 0x02));
            if (r.getSW1() == 0x90) {
                r = channel.transmit(new CommandAPDU(0x00, APDUCommand.UPDATE_BINARY.getCommand(), 0x00, 0x00, timeHex, 0x00, 0x04));
                if (r.getSW1() == 0x90) {
                    try {
                        output.writeTo(stream);
                        System.out.print("NIce job \n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (CardException e1) {
            e1.printStackTrace();
        }
    }

    private boolean isByteArrayEmpty(byte[] array) {
        for (byte b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    private byte[] getTimestampBytes(long time) {
        String s = Integer.toHexString((int) (time / 1000));
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private byte[] readSelectedFileOnce(CardChannel channel, int lengthInt) throws Exception {
        ResponseAPDU rspLen;
        int p1 = 0x00;
        int p2 = 0x00;
        ByteArrayOutputStream idFileOut = new ByteArrayOutputStream();

        rspLen = channel.transmit(new CommandAPDU(0, APDUCommand.READ_BINARY.getCommand(), p1, p2, lengthInt));

        if (lengthInt == 10) {
            typeOfTachographCardId = rspLen.getData()[0];
            noOfEventsPerType = rspLen.getData()[3];
            noOfFaultsPerType = rspLen.getData()[4];
            activityStructureLength = rspLen.getData()[5] * 256 + Byte.toUnsignedInt((byte) rspLen.getData()[6]);
            noOfCardVehicleRecords = rspLen.getData()[7] * 256 + Byte.toUnsignedInt((byte) rspLen.getData()[8]);
            noOfCardPlaceRecords = rspLen.getData()[9];
            System.out.printf("File read typeOfTachographCardId (len %d) \n", typeOfTachographCardId);
            System.out.printf("File read noOfEventsPerType (len %d) \n", noOfEventsPerType);
            System.out.printf("File read noOfFaultsPerType (len %d) \n", noOfFaultsPerType);
            System.out.printf("File read activityStructureLength(len %d) \n", activityStructureLength);
            System.out.printf("File read noOfCardVehicleRecords (len %d) \n", noOfCardVehicleRecords);
            System.out.printf("File read noOfCardPlaceRecords (len %d) \n", noOfCardPlaceRecords);
        }

        if ((rspLen.getSW1() != ((byte) 0x90) || rspLen.getSW2() != 0x0)) {
            System.out.print(rspLen.getSW());
        }

        idFileOut.write(rspLen.getData());
        System.out.printf("File read (len %d)", idFileOut.toByteArray().length);

        return idFileOut.toByteArray();
    }

    private byte[] readSelectedFile(CardChannel channel, int lengthInt) throws Exception {

        ResponseAPDU rspLen;
        int restLength;
        boolean end = true;

        int p1 = 0x00;
        int p2 = 0x00;
        int size = 0x00;
        ByteArrayOutputStream idFileOut = new ByteArrayOutputStream();

        do {
            restLength = Math.min(lengthInt, 200);
            rspLen = channel.transmit(new CommandAPDU(0, APDUCommand.READ_BINARY.getCommand(), p1, p2, restLength));
            switch (rspLen.getSW1()) {
                case 0x90:
                    size += (restLength < 0) ? restLength & 255 : restLength;
                    byte[] offsetarray = ByteBuffer.allocate(4).putInt(size).array();
                    p1 = (offsetarray[2] < 0) ? offsetarray[2] >> 8 : offsetarray[2];
                    p2 = (offsetarray[3] < 0) ? offsetarray[3] & 0xff : offsetarray[3];
                    System.out.print(rspLen.getSW());
                    break;
                case 0x67:

                    break;
                case 0x6B:
                    end = false;
                    break;
                default:
                    break;
            }

            idFileOut.write(rspLen.getData());
            lengthInt -= restLength;
            if (lengthInt == 0) {
                end = false;
            }
        } while (end);

        System.out.printf("File read (len %d)", idFileOut.toByteArray().length);
        return idFileOut.toByteArray();
    }

    public void performHashFile(ResponseAPDU r, CardChannel channel) {
        try {
            r = channel.transmit(new CommandAPDU(0x80, APDUCommand.PERFORM_HASH_OF_FILE.getCommand(), 0x90, 0x00));
            System.out.printf("Hash is there (%d)", r.getSW1());
            switch (r.getSW1()) {
                case 0x90:
                    break;
                case 0x67: // dec 103
                    break;
                // normal process XX = number of bytes for response enabled
                case 0x61:
                    break;
                // incorrect parameters (out of EF)
                case 0x6a:
                    if (r.getSW2() == 0x86)
                        System.out.println("Parametros P1-P2 incorrectos");
                    break;
                // incorrect long, sw2 = exact long. If not return field data
                case 0x6c: //dec 108
                    //nuevaLong = Byte.valueOf(codigoError[1]);
                    if (r.getSW2() == 0x86)
                        System.out.println("Parametros P1-P2 incorrectos");
                    break;
                case 0x69: //dec 108

                    break;
                case 0x6b: //dec 107

                        /*
                        int div = (le < 0)? le.intValue() & 255: le.intValue() ;
                        size -= div;
                        le = Byte.valueOf((byte) (div / 2));
                        size += le;
                        if (le.byteValue() == (byte) 0) {
                            le = Byte.valueOf((byte) 1);
                            end = false;
                        }
                        offsetarray = ByteBuffer.allocate(4).putInt(size).array();
                         entero = Integer.valueOf(byteArraySize4ToInt(offsetarray) );
                        p1 = (offsetarray[2] < 0)? offsetarray[2] & 255: offsetarray[2];
                        p2 = (offsetarray[3] < 0)? offsetarray[3] & 255: offsetarray[3];
                        */
                    break;
                    /*
                    nuevaLong = Byte.valueOf((byte) ((abs(nuevaLong.intValue()) / 2) & 255));
                            if (nuevaLong.byteValue() == (byte) 0) {
                                ahora = true;
                                nuevaLong = Byte.valueOf((byte) 1);
                            }
                     */
                default:
                    break;
            }
        } catch (CardException e1) {
            e1.printStackTrace();
        }
    }

    public byte[] signature(ResponseAPDU r, CardChannel channel) {
        try {
            r = channel.transmit(new CommandAPDU(0x00, APDUCommand.PERFORM_SECURITY_OPERATION.getCommand(), 0x9e, 0x9a, 0x80));
            System.out.printf("Signature is there (%d)", r.getSW1());
            switch (r.getSW1()) {
                case 0x90:
                    break;
                case 0x67: // dec 103
                    break;
                // normal process XX = number of bytes for response enabled
                case 0x61:
                    /*
                     nuevaLong = Byte.valueOf(codigoError[1]);
                     */
                    break;
                // incorrect parameters (out of EF)
                case 0x6a:
                    if (r.getSW2() == 0x86)
                        System.out.println("Parametros P1-P2 incorrectos");
                    break;
                // incorrect long, sw2 = exact long. If not return field data
                case 0x6c: //dec 108
                    //nuevaLong = Byte.valueOf(codigoError[1]);
                    if (r.getSW2() == 0x86)
                        System.out.println("Parametros P1-P2 incorrectos");
                    break;
                case 0x69: //dec 108

                    break;
                case 0x6b: //dec 107

                    break;

                default:
                    break;
            }
        } catch (CardException e1) {
            e1.printStackTrace();
        }
        return r.getData();
    }
}
