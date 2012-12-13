package fi.jyu.ties425.geotrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.geotrack.R;

import fi.jyu.ties425.geotrack.model.GeoTag;
import fi.jyu.ties425.geotrack.util.CommonUtil;
import fi.jyu.ties425.geotrack.util.MyLocation;

/**
 * The main activity for GeoTrack app. 
 * GeoTrack tracks user\'s movement and shows his/her movement in handy ways. 
 * 
 * @author Ari-Matti Nivasalo, Kim Foudila
 */
public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	private boolean trackingIsOn = false;
	
	private List<GeoTag> tags = new ArrayList<GeoTag>();
	
	private Button startStopButton;
	private ImageButton infoImageButton;

	public long lastSearched = System.currentTimeMillis();

	private Timer timer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button button = (Button) findViewById(R.id.startStopButton);
        this.startStopButton = button;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Starts or stops the tracking depending on whether the tracking is on.
     * 
     * @param view
     */
    public void startOrStopTracking(View view) {
    	if (trackingIsOn) {
    		trackingIsOn = false;
    		startStopButton.setText("Start tracking");
			timer.cancel();
		} else {
			trackingIsOn = true;
			startStopButton.setText("Stop tracking");
			timer = new Timer();
			// tries to get a new GeoTag every 60 seconds.
			timer.schedule(new TimerTask() {
				public void run() {
					new LocationSelectionAsyncTask(getApplicationContext(),
							true, true, true).execute((Void[]) null);
				}
			}, 0, 60 * 1000);
		}
	}
    
    /**
     * Opens map activity view
     * @param view
     */
    public void openMapActivity(View view) {
    	if (trackingIsOn) {
    		showDialog("Stop the tracking first to view your route.");
    	} else {
	    	Intent intent = new Intent(this, PointsInMapActivity.class);
	    	// parse tags into an Parcelable[] so that they can be given in the intent's extra
    		Parcelable[] output = CommonUtil.parseGeoTagArrayToParcelableArray(tags.toArray(new GeoTag[0]));
    		intent.putExtra("data", output);
	    	startActivityForResult(intent, 0);
    	}
    }
    
    /**
     * Opens list activity view
     * @param view
     */
    public void openListActivity(View view) {    	
    	if (trackingIsOn) {
    		showDialog("Stop the tracking first to view your route.");
    	} else {
        	Intent intent = new Intent(this, ListActivity.class);
	    	// parse tags into an Parcelable[] so that they can be given in the intent's extra
        	Parcelable[] output = CommonUtil.parseGeoTagArrayToParcelableArray(tags.toArray(new GeoTag[0]));
    		intent.putExtra("data", output);
            startActivityForResult(intent, 0);
    	}

    }
    
    /**
     * Shows info dialog box
     * @param view
     */
    public void showInfoDialog(View view) {
    	showDialog(
    			"Created by:\n" +
        		"Ari-Matti Nivasalo,\n" +
        		"Kim Foudila");
    }

	private void showDialog(String dialogText) {
		// prepare the alert box                   
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
         
        // set the message to display
        alertbox.setMessage(dialogText);
         
        // add a neutral button to the alert box and assign a click listener
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            // click listener on the alert box
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
         
        // show it
        alertbox.show();
	}

	public GeoTag[] getTags() {
		return tags.toArray(new GeoTag[0]);
	}

	public void setTags(List<GeoTag> tags) {
		this.tags = tags;
	}	

	/**
	 * An asynchronous task making use of Android's AsyncTask class.
	 * Executes a background task which tries to fetch the location.
	 * Adds the result location into the list.
	 * @author kimf
	 *
	 */
    private class LocationSelectionAsyncTask extends AsyncTask<Void, Void, GeoTag> {

    	protected final String TAG = LocationSelectionAsyncTask.class.getSimpleName();
    	GeoTag geoTag = null;
    	private boolean useGps;
    	private boolean useNetwork;
    	private boolean preferGps;
    	private Context context;

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

    				// fetches an address for the given coordinates
					Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
					List<Address> addresses = null;
					try {
						addresses = geoCoder.getFromLocation(latitude, longitude, 1);
					} catch (IOException e) {
						Log.e(TAG, "Error getting addresses by location", e);
					}
					geoTag = new GeoTag();
					geoTag.setGeoTag(latitude + "-" + longitude);
					geoTag.setDescr(CommonUtil.getLocationFromAddressLines(addresses));
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
			if (result != null) {
				lastSearched = System.currentTimeMillis();
				tags.add(result);
				Toast.makeText(context, "Obtained a new geo tag: " + result.getDescr(), Toast.LENGTH_LONG).show();
			}
    		Log.d(TAG, "tags content: " + tags);
    	}
    }
	
}


