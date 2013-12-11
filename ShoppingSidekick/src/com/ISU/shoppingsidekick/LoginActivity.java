package com.ISU.shoppingsidekick;

import java.util.Date;
import java.util.List;

import com.Database.API.Account;
import com.Database.API.CalendarItem;
import com.Database.API.DatabaseAPI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//Scan button     
        Button goToHomeBtn = (Button) findViewById(R.id.LoginButton);
        goToHomeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String username = ((EditText) findViewById(R.id.LoginTextUsername)).getText().toString();
				final String password = ((EditText) findViewById(R.id.LoginTextPassword)).getText().toString();
		        Thread thread=new Thread(){
		            @Override
		            public void run(){
		            	hideMessage();
		            	DatabaseAPI d = new DatabaseAPI();
						if(d.checkUserLogin(username, password))
						{
							
							Intent i = new Intent(LoginActivity.this, HomeActivity.class);
							final Account a = d.getAccountInfoByUserID(username);
							i.putExtra("account", a);
							createNotification(a);
							makeToast();
							startActivity(i);
						}
						else{
						showMessage();
						}
		            }
		        };
		        thread.start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void showMessage()
	{
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable(){

			@Override
			public void run() {
				final TextView errorMessage = ((TextView) findViewById(R.id.loginErrorMessage));
				errorMessage.setVisibility(View.VISIBLE);
			}
		});
	}
	
	public void hideMessage()
	{
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable(){

			@Override
			public void run() {
				final TextView errorMessage = ((TextView) findViewById(R.id.loginErrorMessage));
				errorMessage.setVisibility(View.GONE);
			}
		});
	}
	
	public void makeToast()
	{
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable(){

			@Override
			public void run() {
				Toast toast = Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}
	
	public void createNotification(final Account a)
	{
		// Gets an instance of the NotificationManager service
	     final NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    
    Thread thread = new Thread(){
        @Override
        public void run(){
				DatabaseAPI db = new DatabaseAPI();
				List<CalendarItem> items = db.getUsersItems(a.getUserID());
				for(int i = 0; i < items.size(); i++)
				{
					String content = "";
					CalendarItem item = items.get(i);
					Date dateStarted = item.getDateStarted();
					Date dateExpired = item.getDateExpired();
					Date currentDate = new Date();
					double dateExpiredTime = dateExpired.getTime();
					double dateStartedTime = dateStarted.getTime();
					double currentDateTime = currentDate.getTime();
					double time2 = dateExpiredTime - dateStartedTime;
					double time3 = dateExpiredTime - currentDateTime;
					double time4 = (double) (time3 / time2);
					double time5 = (time4) * ((double) 100.0);
					if(dateExpired.before(currentDate))
					{
						content = item.getFood().getName() + " has expired.";
						db.removeCalendarItem(a.getUserID(), item.getID());
					}
					else if(time5 <= 10.0)
					{
						content = item.getFood().getName() + " will expire soon!"; 
					}
					else
					{
						continue;
					}
					Intent notification = new Intent(LoginActivity.this, CalendarActivity.class);
					notification.putExtra("account", a);
					PendingIntent pI = PendingIntent.getActivity(LoginActivity.this, 0, notification, Notification.FLAG_ONLY_ALERT_ONCE);
			        NotificationCompat.Builder mBuilder =
			        	    new NotificationCompat.Builder(LoginActivity.this)
			        	    .setSmallIcon(R.drawable.clock)
			        	    .setContentTitle("Expiration!")
			        	    .setContentText(content)
			        	    .setDefaults(Notification.DEFAULT_VIBRATE)
			        	    .setDefaults(Notification.DEFAULT_LIGHTS)
			        	    .setDefaults(Notification.DEFAULT_SOUND)
			        	    .setAutoCancel(true)
			        	    .setContentIntent(pI);
			        mNotifyMgr.notify(i, mBuilder.build());
				}
        }
    };
    thread.start();
		
		
	}
	}

