package com.example.magix;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

class LoadCard extends AsyncTask<String, Integer, Drawable> {
	boolean isCancelled;
	
    protected Drawable doInBackground(String... str) {
    	isCancelled = false;
        int count = str.length;
        Drawable val = null;
        for (int i = 0; i < count; i++) {
        	val = LoadImageFromWebOperations(str[i]);
            // Escape early if cancel() is called
            if (isCancelled) break;
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
        	Log.i( "LoadImageFromWebOperations" , url); 
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