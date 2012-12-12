package fi.jyu.ties425.geotrack.tasks;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import fi.jyu.ties425.geotrack.model.GeoTag;
import fi.jyu.ties425.geotrack.util.CommonUtil;
import fi.jyu.ties425.geotrack.util.MyLocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Kim Foudila
 */
public class LocationSelectionAsyncTask extends AsyncTask<Void, Void, GeoTag> {

	protected static final String TAG = LocationSelectionAsyncTask.class.getSimpleName();
	GeoTag geoTag = null;
	private boolean useGps;
	private boolean useNetwork;
	private boolean preferGps;
	private Context context;

	private LocationSelectionAsyncTask() {
		// hide
	}

	public LocationSelectionAsyncTask(Context context, boolean useGps, boolean useNetwork, boolean preferGps) {
		this.context = context;
		this.useGps = useGps;
		this.useNetwork = useNetwork;
		this.preferGps = preferGps;
	}

	@Override
	protected GeoTag doInBackground(Void... voids) {
		Looper.prepare();
		final Looper myLooper = Looper.myLooper();
		// Callback class
		final MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

			@Override
			public void gotLocation(android.location.Location location) {
				if (location == null) {
					if (myLooper != null) {
						myLooper.quit();
					}
					return;
				}
				double latitude = location.getLatitude();
				double longitude = location.getLongitude();
				Log.d(TAG, "latitude: " + latitude + ", longitude: " + longitude);

				Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
				List<Address> addresses = null;
				try {
					addresses = geoCoder.getFromLocation(latitude, longitude, 1);
				} catch (IOException e) {
					Log.e(TAG, "Error getting addresses by location", e);
				}
				geoTag.setGeoTag(CommonUtil.getLocationFromAddressLines(addresses));
				geoTag.setLat(latitude);
				geoTag.setLon(longitude);

				if (myLooper != null) {
					myLooper.quit();
				}
			}
		};
		final MyLocation myLocation = new MyLocation();
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				myLocation.getLocation(context, locationResult, 20000, useGps, useNetwork, preferGps);
			}
		});
		Log.d(TAG, "Start fetching location.");
		Looper.loop();
		Log.d(TAG, "Finished fetching location.");
		return geoTag;
	}

	@Override
	protected void onPostExecute(GeoTag result) {
		// TODO whatever we want to do with the geotag
		// we can also make this AsyncTask an inner class in the calling activity
	}
}