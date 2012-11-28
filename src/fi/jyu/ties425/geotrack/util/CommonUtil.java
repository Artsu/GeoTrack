package fi.jyu.ties425.geotrack.util;

import android.os.Parcelable;
import fi.jyu.ties425.geotrack.model.GeoTag;

public class CommonUtil {

	public static GeoTag[] parseParcelableArrayToGeoTagArray(Parcelable[] tmpData) {
		GeoTag[] data = new GeoTag[tmpData.length];
		for (int j=data.length-1; j>=0; --j) {
			data[j] = (GeoTag) tmpData[j];
		}
		return data;
	}

	public static Parcelable[] parseGeoTagArrayToParcelableArray(GeoTag[] data) {
		Parcelable[] output = new Parcelable[data.length];
		for (int j=data.length-1; j>=0; --j) {
		    output[j] = data[j];
		}
		return output;
	}

}
