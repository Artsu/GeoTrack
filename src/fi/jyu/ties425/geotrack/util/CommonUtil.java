package fi.jyu.ties425.geotrack.util;

import android.location.Address;
import android.os.Parcelable;
import fi.jyu.ties425.geotrack.model.GeoTag;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static String getLocationFromAddressLines(List<Address> addresses) {
		if (addresses == null || addresses.size() > 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
			if (i == 0) {
				String addressLine = addresses.get(0).getAddressLine(i);
				sb.append(addressLine);
			} else if (i == 1) {
				String postalCodeAndCity = addresses.get(0).getAddressLine(i);
				// remove postal code
				postalCodeAndCity = postalCodeAndCity.replaceAll("\\d+", "").trim();
				sb.append(", ").append(postalCodeAndCity);
			}
		}
		return sb.toString().trim();
	}
}
