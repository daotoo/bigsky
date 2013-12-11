package com.ISU.shoppingsidekick;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.Database.API.Account;
import com.Database.API.CalendarItem;
import com.Database.API.DatabaseAPI;
import com.Database.API.Expiration;
import com.Database.API.Food;
import com.Database.API.Price;
import com.Database.API.Review;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarInfo extends Activity {
	
	ArrayList<CalendarItem> itemsList;
	ArrayList<Food> foodItems = new ArrayList<Food>();
	ArrayList<String> foodNames = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		final Account a = (Account) getIntent().getExtras().get("account");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_info);
		
		Intent calDate = getIntent();
		Bundle date = null;
		date = calDate.getExtras();
		ArrayAdapter<String> adapter;
		
		TextView dateView = (TextView) findViewById(R.id.dateView);
		TextView foodStatement = (TextView) findViewById(R.id.foodStatement);
		final ListView resultsListView = (ListView) findViewById(R.id.foodItemsList);	
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodNames);
		resultsListView.setAdapter(adapter);
		resultsListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String str = (String) resultsListView.getItemAtPosition(position);
				if(!str.equals("No search results found")){
					Food food = foodItems.get(position);
					Intent i = new Intent(CalendarInfo.this, FoodResultsActivity.class);
					i.putExtra("scanID", food.getID());
					i.putExtra("account", a);
					startActivity(i);
				}
			}
		});
		
		final String dateClicked = date.getString("date");
		ExecutorService pool = Executors.newFixedThreadPool(3);
		Callable task = new Callable(){
			@Override
			public Object call() throws Exception{
				return getFoodItems(dateClicked);
			}
				
			private ArrayList<Food> getFoodItems(String clicked){
				DatabaseAPI db = new DatabaseAPI();
				clicked = dateClicked;
				final Account a = (Account) getIntent().getExtras().get("account");
				ArrayList<CalendarItem> calItems;
				calItems = (ArrayList<CalendarItem>) db.getUsersItems(a.getUserID());
				for(int i = 0; i < calItems.size(); i++){
					if(calItems.get(i).getDateExpired().toString().contains(clicked)){
					foodItems.add(calItems.get(i).getFood());
					}
				}
				return foodItems;
			}
			
		};
		Future<ArrayList<Food>> future = pool.submit(task);
		ArrayList<Food> fI = foodItems;
		
		try {
			fI = future.get();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateView.setText("You have selected the date: " + dateClicked);
		if(foodItems.size() > 0)
		foodStatement.setText("The following items expire on this day:");
		else
			foodStatement.setText("Nothing expires today.");
		for(int i = 0; i < fI.size(); i++){
		if(fI.get(i).getID() != null){
			populateResultsList();
			
		}
	}			
	}
	
	public void populateResultsList(){
		foodNames.clear();
		for(int i = 0; i < foodItems.size(); i++){
			foodNames.add(foodItems.get(i).getName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_info, menu);
		return true;
	}
	  

}
