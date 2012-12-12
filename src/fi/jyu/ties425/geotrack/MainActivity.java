package fi.jyu.ties425.geotrack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.geotrack.R;

import fi.jyu.ties425.geotrack.model.GeoTag;
import fi.jyu.ties425.geotrack.tasks.LocationSelectionAsyncTask;
import fi.jyu.ties425.geotrack.util.CommonUtil;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
	
	private TrackerThread trackerThread;
	
	private Button startStopButton;
	private ImageButton infoImageButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button button = (Button) findViewById(R.id.startStopButton);
        this.startStopButton = button;
//        
//        final ImageButton imageButton = (ImageButton) findViewById(R.id.infoImageButton);
//        this.infoImageButton = imageButton;
        
//        infoImageButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            	
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Starts or stops the tracking depending on whether the tracking is on
     * 
     * @param view
     */
    public void startOrStopTracking(View view) {
    	if (trackingIsOn) {
    		trackingIsOn = false;
    		
    		startStopButton.setText("Start tracking");
    		trackerThread.stopTracking();
    	} else {
    		trackingIsOn = true;
        	
    		startStopButton.setText("Stop tracking");
        	trackerThread = new TrackerThread();
        	trackerThread.startTracking();
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
	    	
//    		GeoTag data[] = new GeoTag[] {
//    				new GeoTag("asdf", "1"),
//    				new GeoTag("assdfsdfsdf", "2"),
//    				new GeoTag("asasddf", "3"),
//    				new GeoTag("das", "4"),
//    				new GeoTag("aasdsdf", "5") };
    		Parcelable[] output = CommonUtil.parseGeoTagArrayToParcelableArray(tags.toArray(new GeoTag[0]));
    		
    		intent.putExtra("data", output);
	    	startActivityForResult(intent, 0); //FIXME: mit� tulee requestCodeksi?
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

//    		GeoTag data[] = new GeoTag[] {
//    				new GeoTag("asdf", "1"),
//    				new GeoTag("assdfsdfsdf", "2"),
//    				new GeoTag("asasddf", "3"),
//    				new GeoTag("das", "4"),
//    				new GeoTag("aasdsdf", "5") };
    		Parcelable[] output = CommonUtil.parseGeoTagArrayToParcelableArray(tags.toArray(new GeoTag[0]));
    		
    		intent.putExtra("data", output);
            startActivityForResult(intent, 0); //FIXME: mit� tulee requestCodeksi?
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
	
    private class TrackerThread extends Thread {
    	private volatile Thread trackerThread;
    	private List<GeoTag> tags;
    	
    	public void run() {
            Thread thisThread = Thread.currentThread();
            while (true) {
                try {
                	LocationSelectionAsyncTask geoTagGetter = new LocationSelectionAsyncTask(
                			getApplicationContext(), 
                			true, 
                			true, 
                			true);
                	try {
						tags.add(geoTagGetter.execute().get()); //Crashaa koko roskan
					} catch (ExecutionException e) {
						Log.e(TAG, e.getMessage());
					}
                    thisThread.sleep(60000 * 3);
                } catch (InterruptedException e){
                	return;
                }
            }
        }
    	
    	public void startTracking() {
    		tags = new ArrayList<GeoTag>();
            trackerThread = new Thread(this);
            trackerThread.start();
        }

        public void stopTracking() {
            Thread tmpThread = trackerThread;
            trackerThread = null;
            tmpThread.interrupt();
        }
    }
	
}
