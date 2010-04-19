package edu.cmu.ece.sythesthesia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SynthHome extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner scale_spinner = (Spinner) findViewById(R.id.scale_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		this, R.array.scale_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (scale_spinner == null)
        	Utilities.showDialog("balls", this);
        else {
	        scale_spinner.setAdapter(adapter);
        }
    }
    
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