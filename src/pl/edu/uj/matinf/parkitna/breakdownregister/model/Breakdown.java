package pl.edu.uj.matinf.parkitna.breakdownregister.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import pl.edu.uj.matinf.parkitna.breakdownregister.RandomString;
import android.os.Parcel;
import android.os.Parcelable;

public class Breakdown implements Serializable, Parcelable {

	private static final long serialVersionUID = 120145357848000830L;

	public static final TitleComparator TITLE_COMPARATOR = new TitleComparator();
	public static final AddedDateComparator ADDED_DATE_COMPARATOR = new AddedDateComparator();
	public static final SolutionDateComparator SOLUTION_DATE_COMPARATOR = new SolutionDateComparator();
	public static final StateComparator STATE_COMPARATOR = new StateComparator();

	public static final String STATE_FIXED = "fixed";  
	public static final String STATE_NOT_FIXED = "not fixed";  
	
	private long id;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getStateFixed() {
		return STATE_FIXED;
	}

	public static String getStateNotFixed() {
		return STATE_NOT_FIXED;
	}

	public long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getState() {
		return state;
	}

	public long getAddedDateInMilis() {
		return addedDateInMilis;
	}

	public long getSolutionDateInMilis() {
		return solutionDateInMilis;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public ArrayList<String> getPhotoPathList() {
		return photoPathList;
	}

	public long getModifiedAt() {
		return modifiedAt;
	}

	private String identifier = new RandomString(8).nextString();
	private String title = "";
	private String description = "";
	private String state = STATE_NOT_FIXED;
	private long addedDateInMilis = 0;
	private long solutionDateInMilis = 0;
	private double longitude = 0.0;
	private double latitude = 0.0;
	private ArrayList<String> photoPathList;
	private long modifiedAt = new Date().getTime();

	
	public static class Builder{
		public long id;
		public String identifier = new RandomString(8).nextString();
		public String title = "";
		public String description = "";
		public String state = STATE_NOT_FIXED;
		public long addedDateInMilis = 0;
		public long solutionDateInMilis = 0;
		public double longitude = 0.0;
		public double latitude = 0.0;
		public ArrayList<String> photoPathList;
		public long modifiedAt = new Date().getTime();
		
		public Breakdown create(){
			return new Breakdown(
					id, identifier, title, description,
					state, addedDateInMilis, solutionDateInMilis, 
					longitude, latitude, photoPathList, modifiedAt);
		}
	}
	
	
	public Breakdown() {
	}

	public Breakdown(long id, String identifier, String title, String description,
			String state, long addedDateInMilis, long solutionDateInMilis,
			double longitude, double latitude, ArrayList<String> photoPathList, long modifiedAt) {
			this.id = id;
			this.identifier = identifier;
			this.title = title;
			this.description = description;
			this.state = state;
			this.addedDateInMilis = addedDateInMilis;
			this.solutionDateInMilis = solutionDateInMilis;
			this.longitude = longitude;
			this.latitude = latitude;
			this.photoPathList = new ArrayList<String>(photoPathList);
			this.modifiedAt = modifiedAt;
	}


	public Breakdown(Breakdown other) {
		identifier = other.identifier;
		title = other.title;
		description = other.description;
		state = other.state;
		addedDateInMilis = other.addedDateInMilis;
		solutionDateInMilis = other.solutionDateInMilis;
		longitude = other.longitude;
		latitude = other.latitude;
		photoPathList = new ArrayList<String>(other.photoPathList);
		modifiedAt = other.modifiedAt;
	}

	public void setId(long id){
		this.id = id;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public void setFixedDateInMilis(long fixedDateInMilis) {
		this.solutionDateInMilis = fixedDateInMilis;
	}

	public void setModifiedAt(long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	private static class TitleComparator implements Comparator<Breakdown> {
		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			return lhs.title.compareTo(rhs.title);
		}
	}

	private static class AddedDateComparator implements Comparator<Breakdown> {

		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			return (int) (lhs.addedDateInMilis - rhs.addedDateInMilis);
		}
	}

	private static class SolutionDateComparator implements Comparator<Breakdown> {

		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			return (int) (lhs.solutionDateInMilis - rhs.solutionDateInMilis);
		}
	}

	private static class StateComparator implements Comparator<Breakdown> {

		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			return lhs.state.compareTo(rhs.state);
		}

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(identifier);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(state);
		dest.writeLong(addedDateInMilis);
		dest.writeLong(solutionDateInMilis);
		dest.writeDouble(longitude);
		dest.writeDouble(latitude);
		dest.writeList(photoPathList);
		dest.writeLong(modifiedAt);
	}

	public Breakdown(Parcel in) {
		id = in.readLong();
		identifier = in.readString();
		title = in.readString();
		description = in.readString();
		state = in.readString();
		addedDateInMilis = in.readLong();
		solutionDateInMilis = in.readLong();
		longitude = in.readDouble();
		latitude = in.readDouble();
		photoPathList = new ArrayList<String>();
		in.readList(photoPathList, null);
	}

	public static final Parcelable.Creator<Breakdown> CREATOR = new Parcelable.Creator<Breakdown>() {
		public Breakdown createFromParcel(Parcel in) {
			return new Breakdown(in);
		}

		public Breakdown[] newArray(int size) {
			return new Breakdown[size];
		}
	};
}
