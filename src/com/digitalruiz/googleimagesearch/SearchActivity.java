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
	ArrayList<ImageResult> imageResults2 = new ArrayList<ImageResult>();
	ArrayList<ImageResult> imageResults3 = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	private int REQUEST_CODE;
	String site;
	String image_size;
	String color_filter;
	String image_type;
	int start;
	int rsz;
	int offset;
	
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
    
    
    
    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent home){
   	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		site = home.getExtras().getString("site");
    		image_size = home.getExtras().getString("image_size");
    		color_filter = home.getExtras().getString("color_filter");
    		image_type = home.getExtras().getString("image_type");
   		Toast.makeText(this,  site, Toast.LENGTH_LONG).show();
   		
    		
   	}
    }
   
    public void setupViews(){
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults  = (GridView) findViewById(R.id.gvResults);
    	btSearch = (Button) findViewById(R.id.btSearch); 
    /**	site = getIntent().getStringExtra("site");
    	if (site == null){
    		site = "";
    	}
    	image_size = getIntent().getStringExtra("image_size");
    	if (image_size == null){
    		image_size = "";
    	}
    	color_filter = getIntent().getStringExtra("color_filter");
    	if (color_filter == null){
    		color_filter = "";
    	}
    	image_type = getIntent().getStringExtra("image_type");
    	if (image_type == null){
    		image_type = "";
    	} */
    	
    }
    
    public void getClient(){
      	String query = etQuery.getText().toString();
        // 	Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
         	AsyncHttpClient client = new AsyncHttpClient();
         	final String myurl = "https://ajax.googleapis.com/ajax/services/search/images?rsz=" + rsz +
         			"&start=" + start + "&imgsz=" + Uri.encode(image_size) + "&imgcolor=" + Uri.encode(color_filter) + "&imgtype=" + Uri.encode(image_type) + "&as_sitesearch=" + Uri.encode(site) + "&v=1.0&q=" + Uri.encode(query);
        // 	final String myurl2 = "https://ajax.googleapis.com/ajax/services/search/images?rsz=4&" +
        // 			"start=" + 9 + "&imgsz=" + Uri.encode(image_size) + "&imgcolor=" + Uri.encode(color_filter) + "&imgtype=" + Uri.encode(image_type) + "&as_sitesearch=" + Uri.encode(site) + "&v=1.0&q=" + Uri.encode(query);
         //	Toast.makeText(this, "url is " + myurl, Toast.LENGTH_LONG).show();
        // 	Toast.makeText(this, "url is " + myurl2, Toast.LENGTH_LONG).show();
//         	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
//         			"start=" + 0 + "&v=1.0&q=" + Uri.encode(query),
         	
    	client.get(myurl,
    				new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONObject response) {
    			JSONArray imageJsonResults = null;
    			try {
    				imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    			//	imageResults.clear();
    			//	imageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
    				imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    				Log.d("DEBUG", imageResults.toString());
    				Log.d("DEBUG", myurl);
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			
    		}
    	}); 
    	
    	}

    
    
 	
    public void onImageSearch(View v){
    	if (site == null){
    		site = "";
    	}
    	if (image_size == null){
    		image_size = "";
    	}
    	if (color_filter == null){
    		color_filter = "";
    	}
    	if (image_type == null){
    		image_type = "";
    	}
    	
    	imageResults.clear();
    	
    	rsz = 6;
    	start = 0;
    	rsz = 6;
    	getClient();
    	start = start + rsz;
    	getClient();
    	
  	  }
    
    
    public void onLoadMore(View v){
    	if (site == null){
    		site = "";
    	}
    	if (image_size == null){
    		image_size = "";
    	}
    	if (color_filter == null){
    		color_filter = "";
    	}
    	if (image_type == null){
    		image_type = "";
    	}
    	
    	imageResults.clear();
    	
    	rsz = 6;
    	start = start + rsz;
    	getClient(); 	
    	start = start + rsz;
    	getClient();
    
  	  }
   
    

    
}
