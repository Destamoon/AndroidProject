package jtan325.contactmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ContactsList contactsList = ContactsList.getInstance();

	private ListView contacts;
	
	
	
	
	static SQLiteDatabase db;

	
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setTitle("Contacts");
		setContentView(R.layout.activity_main);
		
		
		try {
			db = openOrCreateDatabase(dbconstants.DATABASE_NAME,
			MODE_PRIVATE, null);
			db.execSQL(dbconstants.DATABASE_CREATE);
			db.close();
			} catch (Exception e){ 
				e.printStackTrace();
			}
		
		
		
		//customizing the home button on the actionbar 
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.sort);
		

		contacts = (ListView) findViewById(R.id.main_contactList);
	
		updateDB();
		setupContactListView();
		
		
		
		

	}

	//this method sets up the content for the listView on the main screen to display by adding contacts to the contact list from a 'fake' database
	public void setupContactListView() {

		
//		contactsList.add(new Contact("Alistair", "Dumper", "02102755644"));
//		insert(new Contact("Alistair"));
//		
//		contactsList.add(new Contact("Abby"));
//		insert(new Contact("Abby"));
//		
//		contactsList.add(new Contact("Adam"));
//		insert(new Contact("Adam"));
		
		
		
		ListAdapter listadapter = new CustomListAdapter();
		
		

		contacts.setAdapter(listadapter);
		contacts.setOnItemClickListener(new ListItemClickedListener());

	}

	
	//adapter for displaying fields of contact objects
	private class CustomListAdapter extends ArrayAdapter<Contact> {

		CustomListAdapter() {
			super(MainActivity.this, android.R.layout.simple_list_item_1, contactsList.getList());

		}

		
		//overwriting getView so we can display the first name of the contact on the main screen 
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View listItemView = inflater.inflate(R.layout.custom_list, null);
			
			
			TextView name = (TextView) listItemView.findViewById(R.id.list_item);
			
			
			name.setText(contactsList.getList().get(position).getFirstName());
			
			return listItemView;

		}

	}
	
	
	
	
	
	//listener that detects when we click on a contact from the list view
	class ListItemClickedListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
			
			
			ContactDetails.contactClicked = ContactsList.getInstance().getContact(clickedViewPosition);
			Log.d(NewContact.TAG, "contact clicked = " + ContactDetails.contactClicked.getFirstName());
			
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ContactDetails.class);
			startActivity(intent);

		}

	}
	
	
	
	
	
	//this method allows buttons from the actionbar to have functionality
	 public boolean onOptionsItemSelected(MenuItem item) { 
		 
		 switch (item.getItemId()) { 
		 
		//clicking on the "new contact" button redirects to "new contact" screen 
		 case R.id.add_contact: 
			 
			
			 Intent intent = new Intent(); 
			 intent.setClass(MainActivity.this, NewContact.class); 
			 startActivity(intent); 
			 break;
			 
		 //clicking on the "sort" button opens up dialog box which lets user pick a sort method 	 
		 case android.R.id.home:
			 
			 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			 builder.setTitle("Sort contact list by:");
			 builder.setItems(new String[] { "First Name", "Last Name", "Phone Number" }, new DialogInterface.OnClickListener() {
							
				 public void onClick(DialogInterface dialog, int which) {
					 
					 contactsList.sort(which);
					 ((BaseAdapter) contacts.getAdapter()).notifyDataSetChanged();
							
				 }

			});
			 
			 
			builder.create().show();
			break;
		 
		 }
			  
		 
		 
		 return true; 
	}
	
	
	//updates contact list when returning from another screen
	public void onResume(){
		
		super.onResume();
		((BaseAdapter) contacts.getAdapter()).notifyDataSetChanged();
		updateDB();
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void insert(Contact contact){
		db = openOrCreateDatabase(dbconstants.DATABASE_NAME,MODE_PRIVATE, null);
		ContentValues values = new ContentValues();
		values.put(dbconstants.CONTACT_FIRST, contact.getFirstName());
		values.put(dbconstants.CONTACT_LAST, contact.getLastName());
		values.put(dbconstants.CONTACT_MOBILE, contact.getMobile());
		
		db.insert(dbconstants.TABLE_NAME, null, values);
		db.close();
		
		dbconstants.currentId++;
		
				
		
		
		
		
	}
	
	private void updateDB() { 
		db = openOrCreateDatabase(dbconstants.DATABASE_NAME, MODE_PRIVATE, null); 
		Cursor rows = db.rawQuery("SELECT * FROM " + dbconstants.TABLE_NAME, null); 
		int numRows = rows.getCount(); 
		int id; 
	
		String firstName = "";
		String lastName = ""; 
		String mobile = ""; 
		String home = ""; 
		String work = ""; 
		String email = ""; 
		String address = ""; 
		String DOB = ""; 
		
		
		
		if (rows.moveToFirst()) { 
			do { 
				
				id = rows.getInt(rows.getColumnIndexOrThrow(dbconstants.CONTACT_ID)); 
				 
				firstName = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_FIRST)); 
				lastName = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_LAST)); 
				mobile = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_MOBILE)); 
				home = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_HOME)); 
				work = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_WORK)); 
				email = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_EMAIL)); 
				
//				dob = rows.getString(rows.getColumnIndexOrThrow(dbconstants.COLUMN_DOB)); 
//				address = rows.getString(rows.getColumnIndexOrThrow(dbconstants.COLUMN_ADDRESS));
				
				Log.i("MainActivity", "ID - "+id +" is " + firstName);
				
				Contact tryAdd = new Contact(id, firstName, lastName, mobile, home, work, email, address, DOB); 
				boolean toAdd = true; 
				
				for (Contact contact : contactsList.getList()) { 
					
					Log.i("MainActivity", tryAdd.toString()); 
					
					if (contact.equals(tryAdd)) { 
						toAdd = false; 
						Log.i("MainActivity", "Contact " + tryAdd.toString() + " already exists"); 
					} 
					
					
					
				}  if (toAdd) { 
					contactsList.getList().add(tryAdd);  
					 
				} 
				
				
			} while (rows.moveToNext()); 
		} 
		Log.i("MainActivity", "contactList updated with database"); 
		db.close(); 
		
//		for (Contact contact : contactsList.getList()){
//			Log.i("MainActivity", contact.toString()); 
//		}
	}
						
}
