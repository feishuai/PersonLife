package com.personlife.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ComplexPreferences {

	private static ComplexPreferences complexPreferences;
	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private static ObjectMapper mapper = new ObjectMapper();

	private ComplexPreferences(Context context, String namePreferences, int mode) {
		this.context = context;
		if (namePreferences == null || namePreferences.equals("")) {
			namePreferences = "complex_preferences";
		}
		preferences = context.getSharedPreferences(namePreferences, mode);
		editor = preferences.edit();
	}

	public static ComplexPreferences getComplexPreferences(Context context,
			String namePreferences) {

		if (complexPreferences == null) {
			complexPreferences = new ComplexPreferences(context,
					namePreferences, context.MODE_PRIVATE);
		}

		return complexPreferences;
	}

	public void putObject(String key, Object object) {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}

		if (key.equals("") || key == null) {
			throw new IllegalArgumentException("key is empty or null");
		}

		try {
			Log.i("sharepreferences get" + key,
					mapper.writeValueAsString(object));
			editor.putString(key, mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void commit() {
		editor.commit();
	}

	public static void putObject(Context context, String key, Object object) {
		ComplexPreferences pre = getComplexPreferences(context,
				Constants.SharePrefrencesName);
		pre.putObject(key, object);
		pre.commit();
	}

	public <T> T getObject(String key, TypeReference valueTypeRef) {

		String json = preferences.getString(key, null);
		if (json != null)
			Log.i("sharepreferences get" + key, json);
		if (json == null) {
			return null;
		} else {
			try {
				return mapper.readValue(json, valueTypeRef);
			} catch (Exception e) {
				throw new IllegalArgumentException("Object storaged with key "
						+ key + " is instanceof other class");
			}
		}
	}

	public static <T> T getObject(Context context, String key,
			TypeReference valueTypeRef) {
		ComplexPreferences pre = getComplexPreferences(context,
				Constants.SharePrefrencesName);
		return pre.getObject(key, valueTypeRef);
	}

}
