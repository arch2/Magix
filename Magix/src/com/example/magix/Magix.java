package com.example.magix;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Magix extends Activity {	
	
	/*
	 * These two variables are used
	 * when working with images stored
	 * on the device itself
	 */
	Deck deck;
	Deck saved;
	/*
	 * These two variables are used
	 * when working with images
	 * stored online
	 */
	Deck_v2 saved2;
	Deck_v2 deck2;
	/*
	 * GameMode takes into fact 
	 * which type of deck it is using
	 * by checking which one it is passed
	 * and works with them as such
	 */
	GameMode mode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*
    	 * This is where the program starts
    	 */
        super.onCreate(savedInstanceState);
        
        /*
         * Remove title bar
         * And notification bar
         */
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);
        /*
         * Using images from the device's id's
         */       
        //imagesOnDevice(); 
        /*
         * Using images from links to websites
         */
        imagesOnline();
    }

	private void imagesOnDevice() {
	    deck = new Deck(this);
	    saved = new Deck(this);
	    mode = new GameMode(deck, this);
	    createListenersForImagesOnDevice();
	    messager();
	}
	private void imagesOnline() {
		deck2 = new Deck_v2(this);
        saved2 = new Deck_v2(this);        
        mode = new GameMode(deck2, this);
        createListenersForImagesOnline();
        messager();
	}
	public void messager()
	{
		Toast.makeText(this, "Please wait for the images to load.", Toast.LENGTH_SHORT).show();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void createListenersForImagesOnDevice() {   
    	//this method basically just tells buttons on the screen what to do when pressed
    	final ImageView img = (ImageView) findViewById(R.id.img);
    	ImageView nxt = (ImageView) findViewById(R.id.next);
    	nxt.setOnClickListener(new OnClickListener()
    	{
	    	public void onClick(View v)
			{				
	    		img.setImageResource(deck.drawCard());
			}
	    });
    	
		ImageView prev = (ImageView) findViewById(R.id.prev);
		prev.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View v)
			{
				img.setImageResource(deck.getLastCard());
			}
    	});
		
		ImageView current = (ImageView) findViewById(R.id.current);
		current.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{		
				//this button shuffles the deck
				deck.shuffleList();
			}
		});
		
		//named it slides because naming it view might be dangerous
		final Button slides = (Button) findViewById(R.id.view);
		slides.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				popup(saved);
			}
		});
		final Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				saved.addCard(deck.currentCard());
			}
		});
		
		Button gmode = (Button) findViewById(R.id.game);
		gmode.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				mode.pickAMode();
			}
		});
	}
    private void createListenersForImagesOnline() {    	
    	//same as above except using a different type of "deck" (using images online instead of images on device)
    	final ImageView img = (ImageView) findViewById(R.id.img);
    	ImageView nxt = (ImageView) findViewById(R.id.next);
    	nxt.setOnClickListener(new OnClickListener()
    	{
	    	public void onClick(View v)
			{				
	    		img.setImageDrawable(deck2.drawCard());
			}
	    });
    	
		ImageView prev = (ImageView) findViewById(R.id.prev);
		prev.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View v)
			{
				img.setImageDrawable(deck2.getLastCard());
			}
    	});
		
		ImageView current = (ImageView) findViewById(R.id.current);
		current.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{		
				//this button shuffles the deck
					deck2.shuffleList();
			}
		});
		
		//named it slides because naming it view might be dangerous
		final Button slides = (Button) findViewById(R.id.view);
		slides.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				popup(saved2);
			}
		});
		final Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				saved2.addCard2(deck2.getCurrentCard2());
			}
		});
		
		Button gmode = (Button) findViewById(R.id.game);
		gmode.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				mode.pickAMode();
			}
		});
	}
    public void popup(final Deck save) { //popup the saved items
		final Dialog popup = new Dialog(this);
//		popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
		popup.setContentView(R.layout.slidecards);
		popup.show();
		
		final ImageView currentSlide = (ImageView) popup.findViewById(R.id.simg);
		
		ImageView snext = (ImageView) popup.findViewById(R.id.snext);
		snext.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{					
				int id = save.drawCard();
				if(id > -1)
					currentSlide.setImageResource(id);
				else
					popup.dismiss();
			}
		});
		ImageView sprev = (ImageView) popup.findViewById(R.id.sprev);
		sprev.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{		
				int id = save.getLastCard();
				if(id == -1)
					popup.dismiss();
				else
					currentSlide.setImageResource(id);					
			}
		});
		Button del = (Button) popup.findViewById(R.id.delete);
		del.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{						
				//removes current and grabs next id
				int id = save.removeCurrent();
				if(id == -1)
					popup.dismiss();
				else
					currentSlide.setImageResource(id); 					
			}
		});
		
		Button close = (Button) popup.findViewById(R.id.sclose);
		close.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{				
				popup.dismiss();
			}
		});
		
		//** make sure there are cards saved. ***
		if(save.getDeck().size() >0)
			currentSlide.setImageResource(save.drawCard());
		else
		{
			Toast.makeText(this, "No images saved", Toast.LENGTH_SHORT).show();
			popup.dismiss();
		}			
	}
    public void popup(final Deck_v2 save) { //popup the saved items
		final Dialog popup = new Dialog(this);
		popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
		popup.setContentView(R.layout.slidecards);
		popup.show();
		
		final ImageView currentSlide = (ImageView) popup.findViewById(R.id.simg);
		
		ImageView snext = (ImageView) popup.findViewById(R.id.snext);
		snext.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{					
				Drawable id = save.drawCard();
				if(id == null)
					popup.dismiss();					
				else
					currentSlide.setImageDrawable(id);
			}
		});
		ImageView sprev = (ImageView) popup.findViewById(R.id.sprev);
		sprev.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{		
				Drawable id = save.getLastCard();
				if(id == null)
					popup.dismiss();
				else
					currentSlide.setImageDrawable(id);					
			}
		});
		Button del = (Button) popup.findViewById(R.id.delete);
		del.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{						
				//removes current and grabs next id
				Drawable id = save.removeCurrent();
				if(id == null)
					popup.dismiss();
				else
					currentSlide.setImageDrawable(id); 					
			}
		});
		
		Button close = (Button) popup.findViewById(R.id.sclose);
		close.setOnClickListener( new OnClickListener()
		{
			public void onClick(View v)
			{				
				popup.dismiss();
			}
		});
		
		//** make sure there are cards saved. ***
		if(save.getDeck2().size() >0)
			currentSlide.setImageDrawable(save.drawCard());
		else
		{
			Toast.makeText(this, "No images saved", Toast.LENGTH_SHORT).show();
			popup.dismiss();
		}			
	}   
    
}
