package com.example.transportationlily;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class OfferRideActivity extends ActionBarActivity {

	TextView userProfile2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offer_ride);
		userProfile2=(TextView) findViewById(R.id.userProfile2);
		String name= getIntent().getStringExtra("name");
		String from=getIntent().getStringExtra("from");
		//System.out.println(from+"xxx");
		String to=getIntent().getStringExtra("to");
		String phoneNumber=getIntent().getStringExtra("contact");
		userProfile2.setText(Html.fromHtml(name+"<br/>"+from+"<br/>"+to+"<br/>"+phoneNumber));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.offer_ride, menu);
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
}