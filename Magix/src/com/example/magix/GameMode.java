package com.example.magix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GameMode {
	Deck deck;
	Deck_v2 deck2;
	Context con;
	int archEnemyCount = 45;
	int planeChaseCount = 50;
	int vanguardCount = 50;
	boolean DEBUG = false;

	public GameMode(Deck deck, Context app) {
		this.deck = deck;
		con = app;
	}
	public GameMode(Deck_v2 deck2, Context app) {
		this.deck2 = deck2;
		con = app;
	}
	/*
	 * Modified pickAMode 
	 * to download only one 
	 * set of cards for this project
	 */
	public void pickAMode(int dummyval) {
		LoadCards();
//		pickAMode(1);
	}
	public void LoadCards(){
		if(deck2!=null)
		{
			ArrayList<String> sites = loadCards(4);
			deck2.setDeck2(sites);
		}
	}
	/*
	 * End of Modified Area
	 */
	public void pickAMode() {
		final Dialog popup = new Dialog(con);
		popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
		popup.setContentView(R.layout.choice);
		popup.show();
			
		final ImageView plane = (ImageView) popup.findViewById(R.id.plane);
		plane.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{									
				LoadPlaneChase();
				popup.dismiss();
			}
		});
		ImageView arch = (ImageView) popup.findViewById(R.id.arch);
		arch.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{									
				LoadArchenemy();
				popup.dismiss();
			}
		});
		ImageView van = (ImageView) popup.findViewById(R.id.van);
		van.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{									
				LoadVanguard();
				popup.dismiss();
			}
		});
	}	

	protected void LoadPlaneChase() 
	{
		if(deck !=null)
		{	//this area for using drawable id's 
			LoadArchenemy();
			/* Uncomment this when pictures in ***
			ArrayList<Integer> setup = deck.getDeck();
			for(int i = 1; i<= planeChaseCount; i++)
			{
					setup.add(getID("p",i));
			}
			deck.setDeck(setup);
			*/
		}
		else if(deck2 != null)
		{
			ArrayList<String> sites = loadCards(1);
			deck2.clearCards();
			deck2.setDeck2(sites);
		}
	}
	protected void LoadArchenemy() 
	{
		if(deck !=null)
		{	//read above 
			ArrayList<Integer> setup = deck.getDeck();
			for(int i = 1; i< archEnemyCount; i++)
			{
					setup.add(getID("a",i));
			}
			deck.setDeck(setup);
		}
		else if(deck2 != null)
		{
			ArrayList<String> sites = loadCards(2);
			deck2.clearCards();
			deck2.setDeck2(sites);
		}
	}
	protected void LoadVanguard() 
	{
		if(deck!=null)
		{ //read above
			LoadArchenemy();
			/* Uncomment this when pictures in***
			ArrayList<Integer> setup = deck.getDeck();
			for(int i = 1; i< vanguardCount; i++)
			{
					setup.add(getID("v",i));
			}
			deck.setDeck(setup);
			*/
		}
		else if(deck2 != null)
		{
			ArrayList<String> sites = loadCards(3);
			deck2.clearCards();
			deck2.setDeck2(sites);
		}
	}


	private int getID(String type, int num)
	{
		String concat = "";
		if(num==0)
			concat = type;
		else
			concat = type+num;
		num = con.getResources().getIdentifier(concat , "drawable", con.getPackageName());
		return num;
	}
	private ArrayList<String> loadCards(int i) {
		/***************
		 * 1 is for planechase
		 * 2 for archenemy
		 * 3 for vanguard
		 * 4 for project cards
		 */
		String file;
		
		ArrayList<String> args = new ArrayList<String>();
		if(i == 1)
			file = "plane.txt";
		else if(i==2)
			file = "arch.txt";
		else if(i==3)
			file = "van.txt";
		else if(i==4)
			file = "cardz.txt";
		else 
			return null;
		//File aFile = new File(file);
		//Log.v("File", "File path is: "+aFile.exists());
		Log.v("File", "Looking for..."+file);
//		Log.v("File Path", "Path: "+aFile.getAbsolutePath());
		AssetManager am = con.getAssets();
	    try {
	    	InputStream in = am.open(file);
	    	BufferedReader input =  new BufferedReader(new InputStreamReader(in));
	    	try {
	    		String line = null; 
	    		while (( line = input.readLine()) != null)
	    		{
	        			args.add(line);
	        			if(DEBUG) Log.v("Loading...", "Item: "+line);
	    		}
	    	}
	    	finally {
	    		input.close();
	    	}
	    }
	    catch (IOException ex){
	    	ex.printStackTrace();
	    }
	    
		return args;
	}
}

//THIS IS ALL HOW I ORIGINALLY PLANNED TO DO THIS
//DID NOT WORK PROPERLY - MEMORY LEAKS COULDNT BE DEALTH WITH
//*********************************************************
//private Drawable createDImg(String type, int num) //this compresses images
//{
//	String pathToImage = "/drawable/";
//	if(num==0)
//		pathToImage += type;
//	else
//		pathToImage += type+num;
//	
//	int inSample = 2;
//
//	BitmapFactory.Options opts = new BitmapFactory.Options();
//	opts.inSampleSize = inSample;
//
//	Bitmap b = BitmapFactory.decodeFile(pathToImage, opts); // this bitmap will be 1/8 the size of the original
//	Drawable d =new BitmapDrawable(con.getResources(), b);
////	Log.e("Testing", "What did i get?:"+d.toString());
//	return d;
//}
//protected void LoadPlaneChase() {
//ArrayList<Drawable> setup = deck.getDeck();
//for(int i = 1; i<= planeChaseCount; i++)
//{
//		Drawable dd = createImg("p",i);
//		if(dd!=null)
//			setup.add(dd);
//}
//deck.setDeck(setup);
//}
//
//protected void LoadArchenemy() {
//ArrayList<Drawable> setup = deck.getDeck();
//for(int i = 1; i< archEnemyCount; i++)
//{
//		Drawable dd = createImg("a", i);
//		if(dd!=null)
//			setup.add(dd);
//}
//deck.setDeck(setup);
//
//}
//private Drawable createImg(String type, int num) //character to identify then num e.g. "a1"
//{
//String concat = "";
//if(num==0)
//	concat = type;
//else
//	concat = type+num;
//Drawable dd = null;
//try{
//	num = con.getResources().getIdentifier(concat , "drawable", con.getPackageName());
//	dd = con.getResources().getDrawable( num );
//}catch(Error e){e.printStackTrace();}
//
//return dd;
//}
//protected void tester() {
//ArrayList<Drawable> setup = deck.getDeck();
//String mDrawableName = "m3";
//Drawable dd = createImg(mDrawableName,0);
//if(dd!=null)
//setup.add(dd);
//
//String tester = "planechase2";
//dd = createImg(tester, 0);
//if(dd!=null)
//setup.add(dd);
//
//dd = createImg("a",1);
//if(dd!=null)
//setup.add(dd);
//
//int tst = 10;
//tester = "a";
//dd = createImg(tester, tst);
//if(dd!=null)
//setup.add(dd);
//Drawable d = createImg(tester,tst);
//if(d!=null)
//setup.add(d);
//Log.d("Bitmap image", 	"Is: "+dd.toString());
//Log.d("Found drawable", "Is: "+d.toString());
//
//
//deck.setDeck(setup);
//}