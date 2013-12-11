package com.ISU.shoppingsidekick;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.Database.API.Account;
import com.Database.API.DatabaseAPI;
import com.Database.API.Food;

/**
 * Food Finder page that allows the user to search for a particular food item
 * or food group in our database
 * @author Jeremy Curtiss
 *
 */

public class FoodFinderActivity extends Activity {

	EditText searchField;
	ListView resultsListView;
	Button searchBtn;
	ArrayList<Food> searchResults;
	ArrayAdapter<String> adapter;
	ArrayList<String> listItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Account a = (Account) getIntent().getExtras().get("account");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_finder);
		listItems = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		resultsListView = (ListView) findViewById(R.id.searchResultsList);
		resultsListView.setAdapter(adapter);
		resultsListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String str = (String) resultsListView.getItemAtPosition(position);
				if(!str.equals("No search results found")){
					Food food = searchResults.get(position);
					Intent i = new Intent(FoodFinderActivity.this, FoodResultsActivity.class);
					i.putExtra("scanID", food.getID());
					i.putExtra("account", a);
					startActivity(i);
				}
			}
		});
		//Search button and search field
        searchBtn = (Button) findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ExecutorService pool = Executors.newFixedThreadPool(3);
				final String st = getSearchFieldText();
		        Callable task = new Callable(){
					@Override
					public Object call() throws Exception {
						return getSearchResults(st);
					}
					
					private ArrayList<Food> getSearchResults(String searchText){
						if(!searchText.equals("")){
							DatabaseAPI d = new DatabaseAPI();
							ArrayList<Food> setFromName = d.getFoodByFuzzyNameMatch(searchText);
							ArrayList<Food> setFromGroup = d.getFoodByFuzzyFoodGroupMatch(searchText);
							
							return removeDuplicates(setFromName, setFromGroup);
						} else {
							return new ArrayList<Food>();
						}
					}
					
					private ArrayList<Food> removeDuplicates(ArrayList<Food> arr1, ArrayList<Food> arr2){
						arr1.addAll(arr2);
						HashSet hs = new HashSet();
						hs.addAll(arr1);
						arr1.clear();
						arr1.addAll(hs);
						return arr1;
					}
		        };
		        Future<ArrayList<Food>> future = pool.submit(task);
		        try {
					searchResults = future.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
		        if(searchResults.size() > 0)
		        	populateResultsList();
		        else{
		        	listItems.clear();
		        	listItems.add("No search results found");	
		        }
		        adapter.notifyDataSetChanged();
			}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_finder, menu);
		return true;
	}

	/**
	 * Gets the text entered in the searchField
	 * @return String of text from searchField
	 */
	private String getSearchFieldText(){
		String st = "";
		searchField = (EditText)findViewById(R.id.searchField);
		if(searchField.length() > 0)
			return searchField.getText().toString().trim();
		else
			st = "";
		return st;
	}

	public void populateResultsList(){
		listItems.clear();
		for(int i = 0; i < searchResults.size(); i++){
			listItems.add(searchResults.get(i).getName());
		}
	}
}
