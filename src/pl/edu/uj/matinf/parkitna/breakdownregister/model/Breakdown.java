package pl.edu.uj.matinf.parkitna.breakdownregister.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;

public class Breakdown implements Serializable, Parcelable {

	private static final long serialVersionUID = 120145357848000830L;

	public static final TitleComparator TITLE_COMPARATOR = new TitleComparator();
	public static final EntryDateComparator ENTRY_DATE_COMPARATOR = new EntryDateComparator();
	public static final SolutionDateComparator SOLUTION_DATE_COMPARATOR = new SolutionDateComparator();
	public static final StatusComparator STATUS_COMPARATOR = new StatusComparator();

	public String id;
	public String title;
	public String description;
	public String status;
	public String addedDate;
	public String solutionDate;
	public String longitude;
	public String latitude;
	public ArrayList<String> photoPaths;
	public long timestamp;

	public Breakdown() {
	}

	public Breakdown(String id, String title, String description,
			String status, String entryDate, String solutionDate,
			String longitude, String latitude, ArrayList<String> photoPaths, long timestamp) {

		this(title, description, entryDate, longitude, latitude, photoPaths);
		this.id = id;
		this.status = status;
		if (solutionDate == null)
			this.solutionDate = "";
		else
			this.solutionDate = solutionDate;
		this.timestamp = timestamp;
	}

	public Breakdown(String title, String description, String entryDate,
			String longitude, String latitude, ArrayList<String> pathToPhotos) {
		this.title = title;
		this.description = description;
		this.addedDate = entryDate;
		this.longitude = longitude;
		this.latitude = latitude;
		this.photoPaths = pathToPhotos;
	}

	public Breakdown(Breakdown src) {
		id = src.id;
		title = src.title;
		description = src.description;
		status = src.status;
		addedDate = src.addedDate;
		solutionDate = src.solutionDate;
		longitude = src.longitude;
		latitude = src.latitude;
		photoPaths = new ArrayList<String>(src.photoPaths);
	}

	private static class TitleComparator implements Comparator<Breakdown> {
		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			return lhs.title.compareTo(rhs.title);
		}
	}

	private static class EntryDateComparator implements Comparator<Breakdown> {

		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			return lhs.addedDate.compareTo(rhs.addedDate);
		}

	}

	private static class SolutionDateComparator implements
			Comparator<Breakdown> {

		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			if (lhs.solutionDate.equals(""))
				return 1;
			if (rhs.solutionDate.equals(""))
				return -1;
			return lhs.solutionDate.compareTo(rhs.solutionDate);
		}
	}

	private static class StatusComparator implements Comparator<Breakdown> {

		@Override
		public int compare(Breakdown lhs, Breakdown rhs) {
			return lhs.status.compareTo(rhs.status);
		}

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(status);
		dest.writeString(addedDate);
		dest.writeString(solutionDate);
		dest.writeString(longitude);
		dest.writeString(latitude);
		dest.writeList(photoPaths);
	}

	public Breakdown(Parcel in) {
		this.id = in.readString();
		this.title = in.readString();
		this.description = in.readString();
		this.status = in.readString();
		this.addedDate = in.readString();
		this.solutionDate = in.readString();
		this.longitude = in.readString();
		this.latitude = in.readString();
		this.photoPaths = new ArrayList<String>();
		in.readList(this.photoPaths, null);
	}

	public static final Parcelable.Creator<Breakdown> CREATOR = new Parcelable.Creator<Breakdown>() {
		public Breakdown createFromParcel(Parcel in) {
			return new Breakdown(in);
		}

		public Breakdown[] newArray(int size) {
			return new Breakdown[size];
		}
	};

	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject)
			return true;
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		Breakdown other = (Breakdown) otherObject;
		return id.equals(other.id) && title.equals(other.title)
				&& description.equals(other.description)
				&& status.equals(other.status)
				&& addedDate.equals(other.addedDate)
				&& solutionDate.equals(other.solutionDate)
				&& longitude.equals(other.longitude)
				&& latitude.equals(other.latitude)
				&& photoPaths.equals(other.photoPaths);
	}

	public boolean equalsWithoutId(Breakdown otherFailure) {
		return title.equals(otherFailure.title)
				&& description.equals(otherFailure.description)
				&& status.equals(otherFailure.status)
				&& addedDate.equals(otherFailure.addedDate)
				&& solutionDate.equals(otherFailure.solutionDate)
				&& longitude.equals(otherFailure.longitude)
				&& latitude.equals(otherFailure.latitude)
				&& photoPaths.equals(otherFailure.photoPaths);
	}

}
