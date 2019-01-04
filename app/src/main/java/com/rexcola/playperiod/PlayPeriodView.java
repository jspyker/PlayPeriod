package com.rexcola.playperiod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PlayPeriodView extends SurfaceView implements SurfaceHolder.Callback
{
    public PlayPeriodView(Context inContext, AttributeSet attrs) {
        super(inContext, attrs);
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

    }

    MainActivity gameContext = null;
    
    public void addGameContext(MainActivity myGameContext)
    {
    	gameContext = myGameContext;
    }
    
    public void surfaceCreated(SurfaceHolder holder) {
    	if (gameContext != null)
    	{
    		gameContext.showGuess(0);
    	}
    }

    public void surfaceDestroyed(SurfaceHolder holder) 
    {
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
    		int height) 
    {
    	if (gameContext != null)
    	{
    		gameContext.showGuess(0);
    	}
    }
}
