package com.ISU.shoppingsidekick;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.Database.API.Food;

public class RecentActionsActivity extends Activity {

	ArrayList<String> listItems;
	ListView resultsListView;
	ArrayAdapter<String> adapter;
	ArrayList<Food> searchResults;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_actions);
		
		listItems = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		resultsListView = (ListView) findViewById(R.id.listView1);
		resultsListView.setAdapter(adapter);
		
//		resultsListView.setOnItemClickListener(new OnItemClickListener(){
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				String str = (String) resultsListView.getItemAtPosition(position);
//				if(!str.equals("No search results found")){
//					Food food = searchResults.get(position);
//					Intent i = new Intent(RecentActionsActivity.this, FoodResultsActivity.class);
//					i.putExtra("scanID", food.getID());
//					startActivity(i);
//				}
//			}
//		});
		
       
        
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FileOutputStream fout;
				try {
					fout = openFileOutput("recentsFile.txt",MODE_PRIVATE);
					OutputStreamWriter osw = new OutputStreamWriter(fout);
					osw.write("");
					osw.flush();
					osw.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listItems.clear();
				adapter.notifyDataSetChanged();
			}
		});
        
        	
        try{
        	InputStream ips = openFileInput("recentsFile.txt");
        	final ArrayList<String> array = new ArrayList<String>();
        	ArrayList<String> tmpArray = new ArrayList<String>();
       		if(ips!= null){
        		InputStreamReader inputStreamreader = new InputStreamReader(ips);
        		BufferedReader br = new BufferedReader(inputStreamreader);
        		String line;
        		while((line = br.readLine()) != null){
        			if(!tmpArray.contains(line)){
        				tmpArray.add(line);
        			}
        		}
        		int numRecents = tmpArray.size();
        		int larger = numRecents - 10;
        		if(numRecents <= 10){
        			for(int i = numRecents - 1; i >= 0; i--){
        				listItems.add(tmpArray.get(i));
        			}
        			if(listItems.size() > 1){
        				showButton();
        			}
//        			
        		}
        		else{
        			for(int j = numRecents - 1; j > larger - 1; j--){
        				listItems.add(tmpArray.get(j));
        			}
        		}
        		
        		
        		
        		
//        		ExecutorService pool = Executors.newFixedThreadPool(3);
//        		Callable task = new Callable(){
//    				@Override
//    				public Object call() throws Exception{
//    						DatabaseAPI database = new DatabaseAPI();
//    						Food food1 = database.getFoodItemByID(array[0]);
//    						Food food2 = database.getFoodItemByID(array[1]);
//    						Food food3 = database.getFoodItemByID(array[2]);
//    						Food food4 = database.getFoodItemByID(array[3]);
//    						Food food5 = database.getFoodItemByID(array[4]);
//    						Food food6 = database.getFoodItemByID(array[5]);
//    						Food food7 = database.getFoodItemByID(array[6]);
//    						Food food8 = database.getFoodItemByID(array[7]);
//    						Food food9 = database.getFoodItemByID(array[8]);
//    						Food food10 = database.getFoodItemByID(array[9]);
//    						String query = "select * from Food where ID = " + food1 + " or ID = " + food2 + " or ID = " + food3 + " or ID = " + food4 + " or ID = " + food5 + " or ID = " + food6 + " or ID = " + food7 + " or ID = " + food8 + " or ID = " + food9;
//    						ResultSet rs = database.customQuery(query);
//    						ArrayList<String> arr = new ArrayList<String>();
//    						while(rs.next()){
//    							arr.add(rs.getString("Name"));
//    						}
//    						return arr;
//    				}
//        		};
//        		Future<ArrayList<String>> future = pool.submit(task);
//        		ArrayList<String> arr;
//				try {
//					arr = future.get();
//					text1.setText(arr.get(0));
//	               	text2.setText(arr.get(1));
//	               	text3.setText(arr.get(2));
//	               	text4.setText(arr.get(3));
//	               	text5.setText(arr.get(4));
//	               	text6.setText(arr.get(5));
//	               	text7.setText(arr.get(6));
//	               	text8.setText(arr.get(7));
//	               	text9.setText(arr.get(8));
//	               	text10.setText(arr.get(9));
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
               	
       		}
        	
        	
        }
        catch(IOException e){
        	
        	e.printStackTrace();
        }
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recent_actions, menu);
		return true;
	}
	public void showButton()
	{
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable(){
			@Override
			public void run() {
				final Button clear = ((Button) findViewById(R.id.clear));
				clear.setVisibility(View.VISIBLE);
			}
		});
	}

}
