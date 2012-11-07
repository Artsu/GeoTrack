package com.example.geotrack;

import java.util.logging.Logger;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
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
    
    public void onClick(View view) {
    	Log.e(TAG, "asdasd" + view.getId());
    	switch (view.getId()) {
        case R.id.infoImageButton:

       }
    }
    
    public void showInfoDialog(View view) {
    	// prepare the alert box                   
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
         
        // set the message to display
        alertbox.setMessage("Created by:\n" +
        		"Ari-Matti Nivasalo,\n" +
        		"Kim Foudila");
         
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
