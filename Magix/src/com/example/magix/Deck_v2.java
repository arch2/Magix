package com.example.magix;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

public class Deck_v2 {
	//this is for pulling strings of sites for the images
	ArrayList<Drawable> slist;
	int current;
	Context con;
	
	public Deck_v2(Context txt)
	{
		slist = new ArrayList<Drawable>();
		current = 0;
		con = txt;
	}	
	
	//***********************
	//USING STRINGS FOR SITES
	//***********************
	public ArrayList<Drawable> getDeck2()
	{
		return slist;
	}
	public void setDeck2(ArrayList<String> slist)
	{
		try {
			getCards(slist, 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	public void addCard2(Drawable card)
	{
			Toast.makeText(con, "Card added", Toast.LENGTH_SHORT).show();		
			slist.add(card);
	}
	public void removeCard2(int card)
	{
		Toast.makeText(con, "Card removed", Toast.LENGTH_SHORT).show();		
		slist.remove(card);		
	}
	public void clearCards()
	{
		slist.clear();
	}
	//-=-null terminator returns-=-
	public Drawable getCurrentCard2()
	{
		if(slist.size() == 0)
			return null;
		else
			return slist.get(current);
	}
	private Drawable loadCard(String val)
	{
		//should no longer need this.
		LoadCard lc = new LoadCard();
		Drawable result = null;
		try {
			result = lc.execute(val).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Drawable drawCard(){
		return drawCard2();
	}
	public Drawable getLastCard(){
		return getLastCard2();
	}
	public Drawable drawCard2() {
		//get next picture
		if(slist.size() == 0)
			return null;
		if(slist.size() == 1)
		{
			current = 0;
			return getCurrentCard2();
		}
		current++;
		if(current < slist.size())
			return slist.get(current);
		else
		{
			current = 0;
			return slist.get(current);
		}
	}	
	public Drawable getLastCard2() {
		if(slist.size() == 0)
			return null;
		if(slist.size() == 1)
		{
			current = 0;
			return getCurrentCard2();
		}
		current = current-1;
		if(current>=0)
			return slist.get(current);
		else
		{
			current = slist.size()-1;
			return slist.get(current);
		}
	}
	public void shuffleList() {
		Toast.makeText(con, "Shuffling deck", Toast.LENGTH_SHORT).show();
	    int n = slist.size();
	    Random random = new Random();
	    random.nextInt();
	    for (int i = 0; i < n; i++) {
	      int change = i + random.nextInt(n - i);
	      swap(slist, i, change);
	    }
	}
	private void swap(ArrayList<Drawable> a, int i, int change) {
	    Drawable helper = a.get(i);
	    a.set(i, a.get(change));
	    a.set(change, helper);
	}
	  
	public Drawable removeCurrent() {
		removeCard2(current); //remove current id from deck
		while(current >= slist.size())
			current--;
		return getCurrentCard2(); //grab the NEW current id
	}
	private void getCards(ArrayList<String> slist2) throws InterruptedException, ExecutionException {
		LoadMultiCards lc = new LoadMultiCards();
		
		//pass all the strings for the sites the images are on
		String[] ary = slist2.toArray(new String[slist2.size()] ); 
		Log.d("Test", "Size:"+ary.length+" and val1:"+ary[0]);
		lc.execute(ary);		
		//wait for the list to fill up
		slist = lc.get();
		Toast.makeText(con, "Cards loaded", Toast.LENGTH_SHORT).show();
	}
	private void getCards(ArrayList<String> slist2, int dummyval) throws InterruptedException, ExecutionException 
	{
		int Lsize = slist2.size();
		for(int i =0; i<Lsize; i++)
		{
			//create a thread for each image string to download
			executeDownloads(slist2.get(i));
		}
		/*
		 * Took out this busy waiting
		 * because it hogged the memory
		 */
//		int check = slist2.size();		
//		while(check!=slist.size())
//		{
//			//busy waiting for cards to download
//		}
		//the size of slist should change to be the same size as the string arraylist
		Toast.makeText(con, "Cards loading", Toast.LENGTH_SHORT).show();
	}
	public void executeDownloads (String val)
	{
		LoadMultiCards lc = new LoadMultiCards(slist);
		lc.execute(val);
	}
	public Drawable getCard(String card) throws InterruptedException, ExecutionException {
		LoadCard lc = new LoadCard();
		lc.execute(card);
		return lc.get();
	}
}
