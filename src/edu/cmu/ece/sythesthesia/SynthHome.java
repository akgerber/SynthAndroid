package edu.cmu.ece.sythesthesia;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SynthHome extends Activity {
    /** Called when the activity is first created. */
	private String TAG = "SynthHome";
	
	private BluetoothAdapter mBluetoothAdapter;
	private Button sendButton;
	private Spinner scaleSpinner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Initialize scale spinner
        scaleSpinner = (Spinner) findViewById(R.id.scale_spinner);
        if (scaleSpinner != null) {
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	        		this, R.array.scale_options, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        scaleSpinner.setAdapter(adapter);
        } else {
        	//TODO: error handling
        }
        
        //initialize button
        sendButton = (Button) findViewById(R.id.send_button);
        if (sendButton != null) {
        	sendButton.setOnClickListener(mSendButtonListener);
        } else {
        	//TODO error handling
        }
        
        //Initialize Bluetooth adapter
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
        	Toast.makeText(this, R.string.bt_error, 3);
        	
        }
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	return true;
    }
    
    private Button.OnClickListener mSendButtonListener = new Button.OnClickListener(){
	    public void onClick(View v) {
	    	Log.v(TAG, "BodyTrackHome starting ZXing for barcode scan");	    	
	    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	    	intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
	    	startActivityForResult(intent,0);
	    }
    };
    
    private class mOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView parent, View view, int pos, long id) {
			//Do nothing
		}
		
		@Override
		public void onNothingSelected(AdapterView parent) {
			//Do nothing
		}
    }
}