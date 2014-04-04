package com.levenbergs.todoproject;

import android.app.Application;

import com.parse.Parse;

/*
 * This is the entry class for the application.  This is run first.
 * Set things up in here.  (Make sure manifest has been appropriately adjusted.)
 */
public class ToDoListProjectApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "8GS5CmISFCMv7b5uHRiP5h4j21btO9KrvAzVJe6v",
				"UAyVVO0U6RVSMcmGyJTFJZd45dPkwzSQQKAMnSZP");
	}
}
