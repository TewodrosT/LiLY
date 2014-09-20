package com.example.transportationlily;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AskRideActivity extends ActionBarActivity {

	TextView userProfile;
	ListView listView;
	Context mainContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		mainContext = this;
		setContentView(R.layout.activity_ask_ride);
		userProfile=(TextView) findViewById(R.id.userProfile);
		String name= getIntent().getStringExtra("name");
		String from=getIntent().getStringExtra("from");
		//System.out.println(from+"xxx");
		String to=getIntent().getStringExtra("to");
		String phoneNumber=getIntent().getStringExtra("contact");
		userProfile.setText(Html.fromHtml(name+"<br/>"+from+"<br/>"+to+"<br/>"+phoneNumber));
		//System.out.println();
		listView = (ListView) findViewById(R.id.offerList);
		new Downloder().execute("http://www.tutbereket.net/beha1/TransportTest.json");
		
	}
    
	public void changeStatus (View view){
		Button b = (Button) view;
		b.setText("Waiting...");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ask_ride, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class Downloder extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String content = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				System.out.println(params[0]);
				// get the first argument passed to the execute method
				HttpGet httpGet = new HttpGet(params[0]);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "iso-8859-1"), 8);
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					stringBuffer.append(line);
				}
				inputStream.close(); // free memory

				content = stringBuffer.toString();
				// System.out.println(content);
				return content;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//userProfile.setText(result);
			Vector<ScheduleList> scheduleList;
			try {
				scheduleList = Parser.parse(result);
				listView.setAdapter(new ScheduleArrayAdapter(mainContext,scheduleList));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// tTextView.setText(result);
		}

	}
	
	
	private class ScheduleArrayAdapter extends ArrayAdapter<ScheduleList>{
		Vector<ScheduleList>scheduleList;
		Context context;
		public ScheduleArrayAdapter(Context context, Vector<ScheduleList> resource) {
			super(context, R.layout.schedules,resource);
			this.scheduleList=resource;
			this.context= context;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflator= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view= inflator.inflate(R.layout.schedules, parent,false);
			TextView tv1= (TextView)view.findViewById(R.id.TextView01);
			TextView tv2= (TextView)view.findViewById(R.id.TextView02);
			TextView tv3= (TextView)view.findViewById(R.id.TextView03);
			TextView tv4= (TextView)view.findViewById(R.id.TextView04);
			
			tv1.setText(scheduleList.get(position).getServiceGroup());
			tv2.setText(scheduleList.get(position).getID());
			tv3.setText(scheduleList.get(position).getDestinationPoint());
			tv4.setText(scheduleList.get(position).getDateTime());
			return view;
		}
		
		
		
	}

}