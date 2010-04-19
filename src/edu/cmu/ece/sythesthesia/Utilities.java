package edu.cmu.ece.sythesthesia;

import android.app.Activity;
import android.app.AlertDialog;

public class Utilities {
	
	public static void showDialog(String message, Activity act) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(act);
	    builder.setMessage(message);
	    builder.setPositiveButton("OK", null);
	    builder.show();
	}
}
