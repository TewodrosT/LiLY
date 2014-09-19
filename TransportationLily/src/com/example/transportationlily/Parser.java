package com.example.transportationlily;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Parser {
	public static Vector<ScheduleList> parse(String content) throws JSONException {
		Vector<ScheduleList> schedules = new Vector<ScheduleList>();

		JSONArray jsonArray = new JSONArray(content);
		ScheduleList schedule;
		for (int i = 0; i < jsonArray.length(); i++) {
			schedule = new ScheduleList();
			JSONObject object = jsonArray.getJSONObject(i);
			Log.d("Data type", object.getString("ServiceGroup"));
			 schedule.setServiceGroup(object.getString("ServiceGroup"));
			 schedule.setID(object.getString("ID"));
			 schedule.setDestinationPoint(object.getString("Destination"));
			 schedule.setDateTime(object.getString("PickUpTime"));
		
			 schedules.add(schedule);
		}
		return schedules;
	}

}
