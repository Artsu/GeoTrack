package fi.jyu.ties425.geotrack.model;

public class GeoTag {
	private String geoTag;
	private String timeObtained;
	
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

}
