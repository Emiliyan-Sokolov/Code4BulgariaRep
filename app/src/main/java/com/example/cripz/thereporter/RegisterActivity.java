package com.example.cripz.thereporter;

import android.content.Context;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class RegisterActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_register);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class MainFunctionality {
		Context context = getApplicationContext();
		Toast noInternet = Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT);
		String firstName, surName, familyName, city, email, phoneNumber;

		void saveToCache(userInfo u) {
			try {
				FileOutputStream fos = context.openFileOutput("info", Context.MODE_PRIVATE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(u);
				oos.close();
				fos.close();
			} catch (Exception e) {
				String c = "GG no re";
			}
		}

		userInfo readFromCache(final Context context) {
			userInfo object = null;
			try {
				Looper.prepare();
				FileInputStream fis = context.openFileInput("info");
				ObjectInputStream ois = new ObjectInputStream(fis);

				object = (userInfo) ois.readObject();
			} catch (Exception e) {}

			return object;
		}

		protected Void doInBackground(String... params) {
			final Button button = (Button) findViewById(R.id.registerButtonRegisterActivity);
			button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					EditText firstNameEdit = (EditText)findViewById(R.id.firstNameText);
					String firstName = firstNameEdit.getText().toString();
					EditText surNameEdit = (EditText)findViewById(R.id.surnameNameText);
					String surName = surNameEdit.getText().toString();
					EditText familyNameEdit = (EditText)findViewById(R.id.familyNameText);
					String familyName = familyNameEdit.getText().toString();
					EditText cityEdit = (EditText)findViewById(R.id.cityText);
					String city = cityEdit.getText().toString();
					EditText emailEdit = (EditText)findViewById(R.id.inputEmailText);
					String email = emailEdit.getText().toString();
					EditText phoneNumberEdit = (EditText)findViewById(R.id.phoneNumberText);
					String phoneNumber = phoneNumberEdit.getText().toString();
					userInfo user = new userInfo(firstName, surName, familyName, city, email, phoneNumber);
				}
			});

			return null;
		}

		class userInfo {
			String firstName, surName, familyName, city, email, phoneNumber;

			public userInfo(String firstName, String surName, String familyName, String city, String email, String phoneNumber) {
				this.firstName = firstName;
				this.surName = surName;
				this.familyName = familyName;
				this.city = city;
				this.email = email;
				this.phoneNumber = phoneNumber;
			}

		}
	}
}
