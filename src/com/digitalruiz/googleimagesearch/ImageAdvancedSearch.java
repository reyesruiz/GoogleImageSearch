package com.digitalruiz.googleimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class ImageAdvancedSearch extends Activity {
	
	Button btDone;
	EditText etSite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_advanced_search);
		 setupViews();
	}

	
	   public void setupViews(){
	  
	    	btDone = (Button) findViewById(R.id.btDone); 
	    	etSite = (EditText) findViewById(R.id.etSite);
	    }
	    
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_advanced_search, menu);
		return true;
	}
	
	public void onDone(View v){
		String site = etSite.getText().toString();
		Intent home = new Intent(getApplicationContext(), SearchActivity.class);
		home.putExtra("site", site);
		setResult(RESULT_OK, home);
		finish();
		
	}

}
