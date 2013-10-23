package jtan325.contactmanager;



import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetails extends Activity {
	


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
	
	
	//this method sets up the contact details text to be displayed from a contact, first it makes the dummy contact and then 
	//it sets all the textViews with the correct text to display
	private void setupContactInfo(){
		

		
		
		mobile = (TextView)findViewById(R.id.mobile_field);
		home = (TextView)findViewById(R.id.home_field);
		work = (TextView)findViewById(R.id.work_field);
		email = (TextView)findViewById(R.id.email_field);
		address = (TextView)findViewById(R.id.address_field);
		DOB = (TextView)findViewById(R.id.DOB_field);
		image= (ImageView)findViewById(R.id.contactImage);

		
		mobile.setText("Mobile: " + contactClicked.getMobile());
		home.setText("Home: " + contactClicked.getHome());
		work.setText("Work: " + contactClicked.getWork());
		email.setText("Emails: " + contactClicked.getEmail());
		address.setText("Address: " + contactClicked.getAddress());
		DOB.setText("DOB: " + contactClicked.getDOB());
		image.setImageBitmap(BitmapFactory.decodeFile(contactClicked.getPhotoPath()));
		
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
	
	
	public void onResume(){
		
		super.onResume();
		
		setTitle(contactClicked.getFirstName() + " " + contactClicked.getLastName());
		
		mobile.setText("Mobile: " + contactClicked.getMobile());
		home.setText("Home: " + contactClicked.getHome());
		work.setText("Work: " + contactClicked.getWork());
		email.setText("Emails: " + contactClicked.getEmail());
		address.setText("Address: " + contactClicked.getAddress());
		DOB.setText("DOB: " + contactClicked.getDOB());
		image.setImageBitmap(BitmapFactory.decodeFile(contactClicked.getPhotoPath()));
		
	}
	

}
