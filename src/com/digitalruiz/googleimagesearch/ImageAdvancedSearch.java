package com.digitalruiz.googleimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

public class ImageAdvancedSearch extends Activity {
	
	Button btDone;
	EditText etSite;
	Intent home;
	Spinner spinerImageSize;
	Spinner spinerColorFilter;
	Spinner spinerImageType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_advanced_search);
		 setupViews();
	}

	
	   public void setupViews(){
	  
	    	btDone = (Button) findViewById(R.id.btDone); 
	    	etSite = (EditText) findViewById(R.id.etSite);
	    	spinerImageSize = (Spinner) findViewById(R.id.spinerImageSize);
	    	spinerColorFilter = (Spinner) findViewById(R.id.spinerColorFilter);
	    	spinerImageType = (Spinner) findViewById(R.id.spinerImageType);
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
		home.putExtra("image_size", spinerImageSize.getSelectedItem().toString());
		home.putExtra("color_filter", spinerColorFilter.getSelectedItem().toString());
		home.putExtra("image_type", spinerImageType.getSelectedItem().toString());
		setResult(RESULT_OK, home);
	//	startActivity(home);
		finish();
		
	}

}
