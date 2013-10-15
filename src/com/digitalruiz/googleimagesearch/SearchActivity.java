package com.digitalruiz.googleimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {
	//private static final String REQUEST_CODE = "REQUEST_CODE";
	EditText etQuery;
	GridView gvResults;
	Button btSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	private int REQUEST_CODE;
	String site;
	

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
       imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
        		Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
        		ImageResult imageResult = imageResults.get(position);
       // 		i.putExtra("url", imageResult.getFullUrl());
        		i.putExtra("result", imageResult);
        		startActivity(i);
       }
      }); 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
        }
    
    public void onAdvanzed(MenuItem menu){
    	Intent advanzed = new Intent(getApplicationContext(), ImageAdvancedSearch.class);
    	startActivityForResult(advanzed, REQUEST_CODE);
    	
    }
    
 //   @Override
  //   protected void onActivityResult(int requestCode, int resultCode, Intent home){
  // 	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
   // 		String site = home.getExtras().getString("site");
   //		Toast.makeText(this,  site, Toast.LENGTH_LONG).show();
   		
    		
  //  	}
   // }
   
    public void setupViews(){
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults  = (GridView) findViewById(R.id.gvResults);
    	btSearch = (Button) findViewById(R.id.btSearch); 
    	site = getIntent().getStringExtra("site");
    	if (site == null){
    		site = "";
    	}
    	
    }
    
 	
    public void onImageSearch(View v){
    	String query = etQuery.getText().toString();
    	Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
    	AsyncHttpClient client = new AsyncHttpClient();
    	final String myurl = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
    			"start=" + 0 + "&as_sitesearch="+ Uri.encode(site) + "&v=1.0&q=" + Uri.encode(query);
    	Toast.makeText(this, "url is " + myurl, Toast.LENGTH_LONG).show();
//    	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
//    			"start=" + 0 + "&v=1.0&q=" + Uri.encode(query),
    	client.get(myurl,
    				new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONObject response) {
    			JSONArray imageJsonResults = null;
    			try {
    				imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    				imageResults.clear();
    				imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    				Log.d("DEBUG", imageResults.toString());
    				Log.d("DEBUG", myurl);
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			
    		}
    	}); 
    	
  	  }
    
    

    
    

    
}
