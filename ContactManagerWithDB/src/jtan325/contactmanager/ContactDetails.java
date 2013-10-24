package jtan325.contactmanager;



import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is the activity that shows the contact's details. It shows the contact's image as well as all their fields.
 * 
 * @author jtan325
 *
 */

public class ContactDetails extends Activity {
	
	//static field for storing the contact that was clicked from the contactList
	protected static Contact contactClicked;
	

	private TextView mobile;
	private TextView home;
	private TextView work;
	private TextView email;
	private TextView address;
	private TextView DOB;
	private ImageView image;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_details);
		
		
		setupContactInfo();
		setTitle(contactClicked.getFirstName() + " " + contactClicked.getLastName());
		
		//customizing the home button on the actionbar
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.arrow3);
		

		
		
		
		
	}
	
	
	//this method sets up the contact details text to be displayed from a contact
	private void setupContactInfo(){
		
		
		mobile = (TextView)findViewById(R.id.mobile_field);
		home = (TextView)findViewById(R.id.home_field);
		work = (TextView)findViewById(R.id.work_field);
		email = (TextView)findViewById(R.id.email_field);
		address = (TextView)findViewById(R.id.address_field);
		DOB = (TextView)findViewById(R.id.DOB_field);
		image= (ImageView)findViewById(R.id.contactImage);

		//setting field names
		mobile.setText("Mobile: " + contactClicked.getMobile());
		home.setText("Home: " + contactClicked.getHome());
		work.setText("Work: " + contactClicked.getWork());
		email.setText("Emails: " + contactClicked.getEmail());
		address.setText("Address: " + contactClicked.getAddress());
		DOB.setText("DOB: " + contactClicked.getDOB());
		image.setImageBitmap(BitmapFactory.decodeFile(contactClicked.getPhotoPath()));
		
		//setting default image if contact has no photopath
		if (contactClicked.getPhotoPath().equals("")){
			image.setImageResource(R.drawable.aesthetic_2);
		}
		
	}
	
	
	//this method allows buttons from the actionbar to have functionality
	public boolean onOptionsItemSelected(MenuItem item) { 
		 switch (item.getItemId()) { 
		 
		 //clicking on the "edit" button redirects to the edit contact screen 
		 case R.id.edit: 
			 
			
			 Intent intent1 = new Intent(); 
			 intent1.setClass(ContactDetails.this, EditContact.class); 
			 startActivity(intent1); 
			 break;
			 
		 //clicking on the "back" button redirects to the main contact screen,
		 case android.R.id.home:
			 
			 Intent intent2 = new Intent(); 
			 intent2.setClass(ContactDetails.this, MainActivity.class); 
			 intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 startActivity(intent2);
			 break;
		 
		 
		 }
		 
		 
		 
		 return true; 
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_details, menu);
		return true;
	}
	
	
	//method is called when coming back from the Edit Contact screen
	public void onResume(){
		
		super.onResume();
		setTitle(contactClicked.getFirstName() + " " + contactClicked.getLastName());
		
		//contact details need to be freshed and reloaded if they have been editted
		mobile.setText("Mobile: " + contactClicked.getMobile());
		home.setText("Home: " + contactClicked.getHome());
		work.setText("Work: " + contactClicked.getWork());
		email.setText("Email: " + contactClicked.getEmail());
		address.setText("Address: " + contactClicked.getAddress());
		DOB.setText("DOB: " + contactClicked.getDOB());
		image.setImageBitmap(BitmapFactory.decodeFile(contactClicked.getPhotoPath()));
		
		if (contactClicked.getPhotoPath().equals("")){
			image.setImageResource(R.drawable.aesthetic_2);
		} 
		 
		
	}
	

}
