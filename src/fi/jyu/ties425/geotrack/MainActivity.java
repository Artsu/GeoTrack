package fi.jyu.ties425.geotrack;

import com.example.geotrack.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	private boolean trackingIsOn = false;
	
	private Button startStopButton;
	private ImageButton infoImageButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        final Button button = (Button) findViewById(R.id.startStopButton);
//        this.startStopButton = button;
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
     * This didn't seem to work even though it was in some tutorial
     * @param view
     */
    public void onClick(View view) {
    	Log.e(TAG, "asdasd" + view.getId());
    	switch (view.getId()) {
        	case R.id.infoImageButton:
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
	    	Intent i = new Intent(this, PointsInMapActivity.class);
	    	startActivityForResult(i, 0); //FIXME: mitä tulee requestCodeksi?
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
        	Intent i = new Intent(this, ListActivity.class);
        	
//            i.putExtra("Value1", "This value one for ActivityTwo ");
//            i.putExtra("Value2", "This value two ActivityTwo");
            
            startActivityForResult(i, 0); //FIXME: mitä tulee requestCodeksi?
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
}
