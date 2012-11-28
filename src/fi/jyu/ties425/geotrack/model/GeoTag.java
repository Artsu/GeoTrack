package fi.jyu.ties425.geotrack.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GeoTag implements Parcelable {
	private String geoTag;
	private String timeObtained;
	
	public GeoTag() {;}
	/**
	 *
	 * Constructor to use when re-constructing object
	 * from a parcel
	 *
	 * @param in a parcel from which to read this object
	 */
	public GeoTag(Parcel in) {
		readFromParcel(in);
	}
	
	public String getGeoTag() {
		return geoTag;
	}
	public void setGeoTag(String geoTag) {
		this.geoTag = geoTag;
	}
	public String getTimeObtained() {
		return timeObtained;
	}
	public void setTimeObtained(String timeObtained) {
		this.timeObtained = timeObtained;
	}
	
	public GeoTag(String geoTag, String time) {
		this.geoTag = geoTag;
		this.timeObtained = time;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(geoTag);
		dest.writeString(timeObtained);
	}
	private void readFromParcel(Parcel in) {

		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		geoTag = in.readString();
		timeObtained = in.readString();
	}
	
	public int describeContents() {
		return 0;
	}
	
   public static final Parcelable.Creator CREATOR =
   	new Parcelable.Creator() {
           public GeoTag createFromParcel(Parcel in) {
               return new GeoTag(in);
           }

           public GeoTag[] newArray(int size) {
               return new GeoTag[size];
           }
       };
}
