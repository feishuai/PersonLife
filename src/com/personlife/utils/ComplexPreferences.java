package com.personlife.utils;

import java.lang.reflect.Type;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;

public class ComplexPreferences {

	private static ComplexPreferences complexPreferences;
	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private static ObjectMapper mapper = new ObjectMapper();
	Type typeOfObject = new TypeToken<Object>() {
	}.getType();

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
		if(object == null){
			throw new IllegalArgumentException("object is null");
		}
		
		if(key.equals("") || key == null){
			throw new IllegalArgumentException("key is empty or null");
		}
		
		try {
			editor.putString(key, mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void commit() {
		editor.commit();
	}

	public <T> T getObject(String key, TypeReference valueTypeRef) {
	
		String json = preferences.getString(key, null);
		if (json == null) {
			return null;
		} else {
			try{
				return mapper.readValue(json, valueTypeRef);
			} catch (Exception e) {
				throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");				
			}
		}
	}
}
