package com.example.magix;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.widget.Toast;

public class Deck {
	//This is using drawable resources
	ArrayList<Integer> list;
	int current;
	Context con;
	
	public Deck(Context txt)
	{
		list = new ArrayList<Integer>();
		current = 0;
		con = txt;
	}
	public ArrayList<Integer> getDeck()
	{
		return list;
	}
	public void setDeck(ArrayList<Integer> list)
	{
		this.list = list;
	}
	public void clearCards()
	{
		list.clear();
	}
	public void addCard(int num)
	{
		Toast.makeText(con, "Card added", Toast.LENGTH_SHORT).show();	
		list.add(num);
	}
	public void removeCard(int num)
	{
		Toast.makeText(con, "Card removed", Toast.LENGTH_SHORT).show();
		list.remove(num);
	}
	public Integer currentCard()
	{
		if(list.size() == 0)
			return -1;
		else
			return list.get(current);
	}
	public Integer drawCard() {
		//get next picture
		if(list.size() == 0)
			return -1;
		if(list.size() == 1)
		{
			current = 0;
			return currentCard();
		}
		current++;
		if(current < list.size())
			return list.get(current);
		else
		{
			current = 0;
//			shuffleList();
			return list.get(current);
		}
	}
	public Integer getLastCard() {
		if(list.size() == 0)
			return -1;
		if(list.size() == 1)
		{
			current = 0;
			return currentCard();
		}
		current = current-1;
		if(current>=0)
			return list.get(current);
		else
		{
			current = list.size()-1;
			return list.get(current);
		}
	}
	
	public void shuffleList() {
		Toast.makeText(con, "Shuffling deck", Toast.LENGTH_SHORT).show();
	    int n = list.size();
	    Random random = new Random();
	    random.nextInt();
	    for (int i = 0; i < n; i++) {
	      int change = i + random.nextInt(n - i);
	      swap(list, i, change);
	    }
	}
	private void swap(ArrayList<Integer> a, int i, int change) {
	    Integer helper = a.get(i);
	    a.set(i, a.get(change));
	    a.set(change, helper);
	}
	  
	public int removeCurrent() {
		removeCard(current); //remove current id from deck
		while(current >= list.size())
			current--;
		return currentCard(); //grab the NEW current id
	}

}
