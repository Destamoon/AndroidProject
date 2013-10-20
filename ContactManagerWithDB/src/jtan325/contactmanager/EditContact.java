package jtan325.contactmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditContact extends Activity {
	
	private ContactsList contactsList = ContactsList.getInstance();
	
	
	
	
	
	private EditText firstName;
	private EditText lastName;
	private EditText mobile;
	private EditText home;
	private EditText work;
	private EditText email;
	private EditText address;
	private EditText DOB;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		setTitle("Edit");
		
		setupContactInfo();
		
		//customizing the home button on the actionbar
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.arrow3);
		
		
		
		firstName =(EditText) findViewById(R.id.first_name);
		firstName.setFocusable(false);
		
		firstName.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				firstName.setFocusableInTouchMode(true);
				
			}
		});
		
		
		
		
	}
	
	
	private void setupContactInfo(){
		
		//		displayee = new Contact("Adam", "Strange","+64 21072123", "09 535318","2682826", "adams@gmail.com","121 Udys Road","15/08/1993");
		
		firstName =(EditText) findViewById(R.id.first_name);
		lastName =(EditText) findViewById(R.id.last_name);
		mobile = (EditText)findViewById(R.id.mobile_edit);
		home = (EditText)findViewById(R.id.home_edit);
		work = (EditText)findViewById(R.id.work_edit);
		email = (EditText)findViewById(R.id.email_edit);
		address = (EditText)findViewById(R.id.address_edit);
		DOB = (EditText)findViewById(R.id.DOB_edit);

		
		firstName.setText(ContactDetails.contactClicked.getFirstName());
		lastName.setText(ContactDetails.contactClicked.getLastName());
		mobile.setText(ContactDetails.contactClicked.getMobile());
		home.setText(ContactDetails.contactClicked.getHome());
		work.setText(ContactDetails.contactClicked.getWork());
		email.setText(ContactDetails.contactClicked.getEmail());
		address.setText(ContactDetails.contactClicked.getAddress());
		DOB.setText(ContactDetails.contactClicked.getDOB());
		
	}
	
	
	
	
	 //this method allows buttons from the actionbar to have functionality
	 public boolean onOptionsItemSelected(MenuItem item) { 
		 switch (item.getItemId()) { 
		 
		//clicking on the "save" button saves all edited details and redirects to the contact details screen, in this prototype the save functionality has not been implemented
		 case R.id.save: 
			 
			 
			 String saveFirstName = firstName.getText().toString();
			 String saveLastName = lastName.getText().toString();
			 String saveMobile = mobile.getText().toString();
			 String saveHome = home.getText().toString();
			 String saveWork = work.getText().toString();
			 String saveEmail = email.getText().toString();
			 
			
			 
		
			 
			 Log.d(NewContact.TAG, "Attempting to save editted details: " + saveFirstName);
			 
			 MainActivity.db = openOrCreateDatabase(dbconstants.DATABASE_NAME,MODE_PRIVATE, null);
			 ContentValues values = new ContentValues();
			 values.put(dbconstants.CONTACT_FIRST, saveFirstName);
			 values.put(dbconstants.CONTACT_LAST, saveLastName);
			 values.put(dbconstants.CONTACT_MOBILE, saveMobile);
			 values.put(dbconstants.CONTACT_HOME, saveHome);
			 values.put(dbconstants.CONTACT_WORK, saveWork);
			 values.put(dbconstants.CONTACT_EMAIL, saveEmail);
			
			 Log.d(NewContact.TAG, "Successfully created contents value for "+ ContactDetails.contactClicked.getFirstName());
				
			 MainActivity.db.update(dbconstants.TABLE_NAME, values, dbconstants.CONTACT_FIRST+ " = ?", new String[] {ContactDetails.contactClicked.getFirstName()});
			 
			 MainActivity.db.close();
			 
//			 Log.d(NewContact.TAG, "Database is updated");
			 
			 ContactDetails.contactClicked.setFirstName(saveFirstName);
			 ContactDetails.contactClicked.setLastName(saveLastName);
			 ContactDetails.contactClicked.setMobile(saveMobile);
			 ContactDetails.contactClicked.setHome(saveHome);
			 ContactDetails.contactClicked.setWork(saveWork);
			 ContactDetails.contactClicked.setEmail(saveEmail);
			 
			  
			 finish();
			 break;
			 
			 
		 //clicking on the "back" button redirects to the contact details screen 
		 case android.R.id.home: 
			 
			
			 onBackPressed();
			 break;
		 
		 //clicking on the "delete" button opens up a dialogue box prompting the user and then deleting the contact if the accept.
		 //The delete functionality has not been implemented completely, it only deletes the last contact in the list as opposed to the selected contact.
		 case R.id.delete:
			 
			
					 
			 AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditContact.this);
						
			 dialogBuilder.setTitle("Are you sure you want to delete this contact?");
			 dialogBuilder.setMessage("This action will permanently remove the contact");
						
			 dialogBuilder.setNegativeButton("Cancel", null);
			 dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							
						
				 public void onClick(DialogInterface dialog, int which) {
								
					 
					MainActivity.db = openOrCreateDatabase(dbconstants.DATABASE_NAME,MODE_PRIVATE, null);
					Log.d(NewContact.TAG, dbconstants.CONTACT_ID + " ='" + ContactDetails.contactClicked.getId()+ "'");
					boolean deleted = MainActivity.db.delete(dbconstants.TABLE_NAME, dbconstants.CONTACT_ID + " ='" + ContactDetails.contactClicked.getId()+ "'", null) >0; 
					//Also remove it from the _contactList being displayed 
					
					String name = ContactDetails.contactClicked.toString();
					Log.d(NewContact.TAG, "Successfully deleted " +name+" from database");
					
					
					
					contactsList.delete(ContactDetails.contactClicked);
					MainActivity.db.close();
					 
								
					Intent a = new Intent(EditContact.this,MainActivity.class);
					a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(a);
								
					}
				 
				});
			 
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			break;
							
		 }
		 return true; 
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

}
