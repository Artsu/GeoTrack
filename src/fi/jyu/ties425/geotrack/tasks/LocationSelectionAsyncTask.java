package fi.jyu.ties425.geotrack.tasks;


import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import fi.jyu.ties425.geotrack.util.MyLocation;

/**
 * @author Kim Foudila
 */
public class LocationSelectionAsyncTask extends AsyncTask<Void, Void, Location> {

	protected static final String TAG = LocationSelectionAsyncTask.class.getSimpleName();
	Location loc = null;
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
	protected Location doInBackground(Void... voids) {
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

				loc.setLatitude(latitude);
				loc.setLongitude(longitude);

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
		return loc;
	}

}