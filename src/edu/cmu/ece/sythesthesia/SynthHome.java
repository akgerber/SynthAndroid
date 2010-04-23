package edu.cmu.ece.sythesthesia;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SynthHome extends Activity {
	private String TAG = "SynthHome";
	
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    
    // Bluetooth-related globals
    private BluetoothAdapter bta;
    private BluetoothDevice remotebt;
    private BluetoothSocket bts;
    private OutputStream os;
    private OutputStreamWriter osw;
    private InputStream is;
    private InputStreamReader isr;
    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //magic
    
    //UI Components
    private TextView status;
	private Button sendButton;
	private Spinner scaleSpinner;
	private RadioButton major;
	private RadioButton minor;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Initialize UI elements
        try {
        	//Spinner
	        scaleSpinner = (Spinner) findViewById(R.id.scale_spinner);
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	        		this, R.array.scale_options, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        scaleSpinner.setAdapter(adapter);
	        //Button
	        sendButton = (Button) findViewById(R.id.send_button);
	    	sendButton.setOnClickListener(mSendButtonListener);
	    	//Status text
	        status = (TextView) findViewById(R.id.conn_status);
	        //Radio Buttons
	        major = (RadioButton) findViewById(R.id.RBMajor);
	        minor = (RadioButton) findViewById(R.id.RBMinor);
        } catch (NullPointerException E) {
        	//TODO null view error handling
        	//tell the user everything is sad and there ain't much to do 'bout it
        }
        
        //Initialize Bluetooth adapter
    	bta = BluetoothAdapter.getDefaultAdapter();
		if (bta == null) {
        	Toast.makeText(this, R.string.bt_error, 3);
		}


        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.scan:
    		Intent getDevIntent = new Intent(this, DeviceListActivity.class);
    		startActivityForResult(getDevIntent, REQUEST_CONNECT_DEVICE);
    		break;
    	case R.id.quit:
    		quit();
    		break;
    	}
    	return false;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	//Getting a Bluetooth device
    	case REQUEST_CONNECT_DEVICE:
    		//Activity succeeded
    		if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BluetoothDevice object
            	remotebt = bta.getRemoteDevice(address);
            	//Attempt to connect to the device
            	try {
	                bts = remotebt.createRfcommSocketToServiceRecord(uuid);
	                os = bts.getOutputStream();
	                is = bts.getInputStream();
	                bts.connect();
            	} catch (Exception e) {
        			Toast.makeText(this, R.string.bt_error, 5);            		
            	}
            	//Set status to success
            	status.setText(R.string.connected);
            //Activity failed
    		} else {
    			Toast.makeText(this, R.string.bt_error, 5);
    		}		
    	}
    }
    
    private void quit() {
    	System.exit(0);
    }
    
    private Byte getStateToSend() {
    	return (byte)0xff;
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