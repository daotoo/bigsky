package com.ISU.shoppingsidekick;

import com.ISU.shoppingsidekick.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SplashActivity extends Activity {

	private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        
        //final SplashActivity activity = this;
        thread=new Thread(){
            @Override
            public void run(){
                try
                {
                	synchronized(this){
                		wait(2000);
                	}
                }catch(InterruptedException e){};
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                finish();
                SplashActivity.this.startActivity(intent);
            }
        };
        thread.start();
    }
}