package com.example.magix;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

class LoadMultiCards extends AsyncTask<String, Integer, ArrayList<Drawable>> {
	boolean isCancelled;
	boolean DEBUG = true;
	ArrayList<Drawable> val;
	
	public LoadMultiCards(){};
	public LoadMultiCards (ArrayList<Drawable> val)
	{
		this.val = val;
	}
	
	protected void onPreExecute(){
    	isCancelled = false;
		if(val == null)
        	val = new ArrayList<Drawable>();
	}
	
    protected ArrayList<Drawable> doInBackground(String... str) {
        int count = str.length;        
        /*
         * If only passed one variable 
         * don't loop
         * To save memory
         */
        
        if(count == 1)
        	val.add(LoadImageFromWebOperations(str[0]) );
        else
        {
        	for (int i = 0; i < count; i++) {
        		val.add(LoadImageFromWebOperations(str[i]) );        	
        		// 	Escape early if cancel() is called
        		if (isCancelled) break;
        	}
        }
        return val;
    }

    protected void onProgressUpdate(Integer... progress) {
//        setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
//        showDialog("Downloaded " + result + " bytes");
    }
    protected void cancel()
    {
    	isCancelled = true;
    }
    private Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
        	if (DEBUG) Log.i( "LoadImageFromWebOperations" , val.size() + " dl'ing: " +url); 
        	
	        InputStream is = (InputStream) new URL(url).getContent();
	        return Drawable.createFromStream(is, "src name");
        }
        catch (Exception e) 
        {
	        System.out.println("Exc="+e);
	        return null;
        }
    }
}