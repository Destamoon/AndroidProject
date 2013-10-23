package jtan325.contactmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class NewContact extends Activity {
	
	public final static String TAG = "TestLog";
	
	private ContactsList contactsList = ContactsList.getInstance();
	private EditText firstName;
	private EditText lastName;
	private EditText mobile;
	private EditText home;
	private EditText work;
	private EditText email;
	private EditText address;
	private EditText DOB;
	
	private static int RESULT_LOAD_IMAGE = 1;
	private ImageView imageView;
	private String photoPath = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
		
		firstName = (EditText) findViewById(R.id.newFirstName);
		lastName = (EditText) findViewById(R.id.newLastName);
		mobile = (EditText) findViewById(R.id.newMobile);
		home = (EditText) findViewById(R.id.newHome);
		work = (EditText) findViewById(R.id.newWork);
		email = (EditText) findViewById(R.id.newEmail);
		address = (EditText) findViewById(R.id.newAddress);
		DOB = (EditText) findViewById(R.id.newDOB);
		imageView = (ImageView) findViewById(R.id.new_image);
		
		//imageview needs to be able to be changed on click
		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//new action taking you to gallery to select a picture
				Intent i = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						 
						startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
			
			
			
		});
		
		//customizing the home button on the actionbar
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.arrow3);
		
		setTitle("New Contact");
		
		
	}

	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	         
	        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	            Cursor cursor = getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            photoPath = cursor.getString(columnIndex);
	            cursor.close();
	            
	            imageView.setImageBitmap(BitmapFactory.decodeFile(photoPath));
	        }
	        
	 }
	
	
	
	 
	
	//this method allows buttons from the actionbar to have functionality
	public boolean onOptionsItemSelected(MenuItem item) { 
		 switch (item.getItemId()) { 
		 
		//clicking on the "save" button saves the new content and redirects to the main screen, however in this prototype it is just adding
		//a dummy contact 'Jordan'
		 case R.id.save_button: 
			 
			 
			 //saving new contact with only firstName field
			 String saveFirstName = firstName.getText().toString();
			 String saveLastName = lastName.getText().toString();
			 String saveMobile = mobile.getText().toString();
			 String saveHome = home.getText().toString();
			 String saveWork = work.getText().toString();
			 String saveEmail = email.getText().toString();
			 String saveAddress=address.getText().toString();;
			 String saveDOB=DOB.getText().toString();;
			 
			 Contact newContact = new Contact (dbconstants.currentId, saveFirstName, saveLastName, saveMobile, saveHome, saveWork, saveEmail, saveAddress, saveDOB, photoPath);
			 
			 
			 Log.d(TAG, "Attempting to save: " + saveFirstName);
			 
			 MainActivity.db = openOrCreateDatabase(dbconstants.DATABASE_NAME,MODE_PRIVATE, null);
			 ContentValues values = new ContentValues();
			 values.put(dbconstants.CONTACT_FIRST, newContact.getFirstName());
			 values.put(dbconstants.CONTACT_LAST, newContact.getLastName());
			 values.put(dbconstants.CONTACT_MOBILE, newContact.getMobile());
			 values.put(dbconstants.CONTACT_HOME, newContact.getHome());
			 values.put(dbconstants.CONTACT_WORK, newContact.getWork());
			 values.put(dbconstants.CONTACT_EMAIL, newContact.getEmail());
			 values.put(dbconstants.CONTACT_ADDRESS, newContact.getAddress());
			 values.put(dbconstants.CONTACT_DOB, newContact.getDOB());
			 values.put(dbconstants.CONTACT_IMAGE, newContact.getPhotoPath());
			
				
			 MainActivity.db.insert(dbconstants.TABLE_NAME, null, values);
			 MainActivity.db.close();
			 
			 dbconstants.currentId++;
			 
			 finish();
			 break;
		 
		 //clicking on the "back" button redirects to the main screen
		 case android.R.id.home:
			 
			 Intent intent = new Intent(); 
			 intent.setClass(NewContact.this, MainActivity.class); 
			 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 startActivity(intent); 
			 break;
			 
	
		 }
		 return true; 
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_contact, menu);
		return true;
	}

}
