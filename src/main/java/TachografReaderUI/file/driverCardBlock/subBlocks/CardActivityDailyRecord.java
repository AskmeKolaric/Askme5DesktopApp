/**
 * 
 */
package TachografReaderUI.file.driverCardBlock.subBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import TachografReaderUI.file.driverCardBlock.DriverCardSizes;
import TachografReaderUI.helpers.BCDString;
import TachografReaderUI.helpers.Number;
import TachografReaderUI.helpers.RealTime;

public class CardActivityDailyRecord {		
	private int activityPreviousRecordLength; //2 byte
	private int activityRecordLength; // 2 byte 
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="UTC")
	private Date activityRecordDate; // 4 byte
	private int activityDailyPresenceCounter; // 2 size
	private int activityDayDistance; // 2 size
	private ArrayList <ActivityChangeInfo> activityChangeInfo; // size 2

	public CardActivityDailyRecord() {}

	public CardActivityDailyRecord(byte[] bytes){
		
		int start=0;
		this.activityPreviousRecordLength= Number.getShort_16(Arrays.copyOfRange(bytes, start, start+= DriverCardSizes.ACTIVITYPREVIUSRECORDLENGTH.getMax()));
		this.activityRecordLength= Number.getShort_16(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.ACTIVITYRECORDLENGTH.getMax()));
		this.activityRecordDate = RealTime.getRealTime(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.ACTIVITYRECORDDATE.getMax()));
		//DailyPresenceCounter adpc=new DailyPresenceCounter(Arrays.copyOfRange(bytes, start, start+=2));
		String adpc= BCDString.BCDtoString(Arrays.copyOfRange(bytes, start, start+= DriverCardSizes.ACTIVITYDAILYPRESENCECOUNTER.getMax()));
		this.activityDailyPresenceCounter = Integer.parseInt(adpc);
		//this.activityDailyPresenceCounter = Number.getShort_16(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.ACTIVITYDAILYPRESENCECOUNTER.getMax()));
		//Distance d=new Distance(Arrays.copyOfRange(bytes, start, start+=2));
		this.activityDayDistance=Number.getShort_16(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.ACTIVITYDAYDISTANCE.getMax()));
		this.activityChangeInfo=new ArrayList<ActivityChangeInfo>();


		Calendar c= Calendar.getInstance();
		Date d;
		long num=0;
		for (int i=start; i<this.activityRecordLength;i+=2){
			ActivityChangeInfo activityChangeInfo=new ActivityChangeInfo(Arrays.copyOfRange(bytes, start, start+=DriverCardSizes.ACTIVITYCHANGEINFO.getMin()));
			this.activityChangeInfo.add(activityChangeInfo);
			c.setTime(this.activityRecordDate);
		}
				
	
		
	}

	public void setActivityDailyPresenceCounter(int activityDailyPresenceCounter) {
		this.activityDailyPresenceCounter = activityDailyPresenceCounter;
	}

	public void setActivityDayDistance(int activityDayDistance) {
		this.activityDayDistance = activityDayDistance;
	}

	public int getActivityPreviousRecordLength() {
		return activityPreviousRecordLength;
	}

	public void setActivityPreviousRecordLength(int activityPreviousRecordLength) {
		this.activityPreviousRecordLength = activityPreviousRecordLength;
	}

	public int getActivityRecordLength() {
		return activityRecordLength;
	}

	public void setActivityRecordLength(int activityRecordLength) {
		this.activityRecordLength = activityRecordLength;
	}

	public Date getActivityRecordDate() {
		return activityRecordDate;
	}

	public void setActivityRecordDate(Date activityRecordDate) {
		this.activityRecordDate = activityRecordDate;
	}


	public int getActivityDailyPresenceCounter() {
		return activityDailyPresenceCounter;
	}

	public void setActivityDailyPresenceCounter(short activityDailyPresenceCounter) {
		this.activityDailyPresenceCounter = activityDailyPresenceCounter;
	}

	public int getActivityDayDistance() {
		return activityDayDistance;
	}


	public void setActivityDayDistance(short activityDayDistance) {
		this.activityDayDistance = activityDayDistance;
	}

	public ArrayList<ActivityChangeInfo> getActivityChangeInfo() {
		return activityChangeInfo;
	}

	public void setActivityChangeInfo(
			ArrayList<ActivityChangeInfo> activityChangeInfo) {
		this.activityChangeInfo = activityChangeInfo;
	}

	@Override
	public String toString() {
		return "\nCardActivityDailyRecord [activityPreviousRecordLength="
				+ activityPreviousRecordLength + ", activityRecordLength="
				+ activityRecordLength + ", activityRecordDate="
				+ activityRecordDate + ", activityDailyPresenceCounter="
				+ activityDailyPresenceCounter + ", activityDayDistance="
				+ activityDayDistance + ", activityChangeInfo="
				+ activityChangeInfo.toString() + "]";
	}
	
}
