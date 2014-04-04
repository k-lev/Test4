package com.levenbergs.todoproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

	protected EditText mUsername;
	protected EditText mPassword;	
	protected EditText mEmail;
	protected Button mSignUpButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// must be called before set content view or else crash...
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_sign_up);
		
		// initialized the field in the signup activity, plus the button
		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mEmail = (EditText) findViewById(R.id.emailField);
		mSignUpButton = (Button) findViewById(R.id.signupButton);

		/*
		 * After button is clicked, check to make sure fields are 
		 * valid before creating new user;
		 */
		
		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName	= mUsername.getText().toString();
				String password	= mPassword.getText().toString();
				String email	= mEmail.getText().toString();
				
				// trim the white spaces before checking
				userName = userName.trim();
				password = password.trim();
				email = email.trim();
				
				// If field is not filled, warn user with a dialog, else create new user
				// (User might not see toast if distracted...)
				if(userName.isEmpty() || password.isEmpty() || email.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
					builder.setMessage(R.string.sign_up_error_message)
						.setTitle(R.string.sign_up_error_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else {
					// create new user
					//Set progress bar to run until Parse.com is done
					setProgressBarIndeterminateVisibility(true);

					ParseUser newUser = new ParseUser();
					newUser.setUsername(userName);
					newUser.setPassword(password);
					newUser.setEmail(email);
					newUser.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							// Remove progress bar; we have results to show
							setProgressBarIndeterminateVisibility(false);

							// If successful, proceed to MainListActivity
							if(e == null) {
								// success
								Intent intent = new Intent(SignUpActivity.this, MainListActivity.class);
								// set flags so user can't use back button to end up back in SignUpActivity
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else {
								AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
								builder.setMessage(e.getMessage())	
									.setTitle(R.string.sign_up_error_title)
									.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
								
							}
							
						}
					});
					
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
