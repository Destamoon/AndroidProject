package jtan325.contactmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 *This is the main activity class where magic happens.
 *The top left button on the actionbar opens up a dialog for sorting, the top right button
 *opens up a new screen for adding a contact.
 * 
 * @author jtan325
 *
 */

public class MainActivity extends Activity {

	

	private ListView contacts;
	private EditText searchBar;
	
	//field to keep track of what sorting method is currently selected, 0 for first name, 1 for last name, 2 for phone number
	private int sort=0;
	
	
	//fields used for the searchbar, textlength keeps track of the length of the searched text, currentList keeps track of the list to be displayed in the listview - 0 for contactList 
	//and 1 for searchList. contactsList is the default contact list and searchList is the contact list adjusted by a search
	int textlength=0;
	private int currentList=0;
	private ContactsList contactsList = ContactsList.getInstance();
	private List<Contact> searchList= new ArrayList<Contact>();
	
	//static database for all the classses to use
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
		
		//adding text changed listener for the search bar 
		searchBar =(EditText) findViewById(R.id.searchBar);
		searchBar.addTextChangedListener(new TextWatcher() {
			
			//method to be executed after text is changed, resets the current list to be displayed
			public void afterTextChanged(Editable s) {
				if (searchBar.getText().toString().equals("")) { 
					contacts.setAdapter(new CustomListAdapter (contactsList.getList()));
					currentList=0;
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Abstract Method of TextWatcher Interface.
			}

			//updating the contact list with the search field in real time. 
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				textlength = searchBar.getText().length();
				searchList.clear();
				
				for (int i = 0; i < contactsList.getList().size(); i++) {
					if (textlength <= contactsList.getContact(i).getFirstName().length()) {
						if (searchBar.getText().toString().equalsIgnoreCase((String) contactsList.getContact(i).getFirstName().subSequence(0, textlength))) {
							searchList.add(contactsList.getContact(i));
						}
					}
				}
				
				//setting searchList to be displayed
				contacts.setAdapter(new CustomListAdapter( searchList));
				currentList= 1;
			}
		});
		
		//making fast scroll available if the contact list is very large
		contacts.setFastScrollEnabled(true);
	
		//stops the keyboard from auto appearing on startup
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
		
		//updates the database and sets up the listView to be displayed
		updateContactList();
		setupContactListView();
		
		
		
		

	}

	//this method sets up the content for the listView on the main screen to display by adding contacts to the contact list from a 'fake' database
	public void setupContactListView() {

		ListAdapter listadapter = new CustomListAdapter(contactsList.getList());
	
		contacts.setAdapter(listadapter);
		contacts.setOnItemClickListener(new ListItemClickedListener());

	}

	
	//adapter for displaying fields of contact objects
	private class CustomListAdapter extends ArrayAdapter<Contact> {
		
		List<Contact> customList= new ArrayList<Contact>();
		
		CustomListAdapter(List<Contact> list) {
			super(MainActivity.this, android.R.layout.simple_list_item_1, list);
			customList = list;

		}

		
		//overwriting getView so we can display the first name of the contact on the main screen 
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View listItemView = inflater.inflate(R.layout.custom_list, null);
			
			TextView name = (TextView) listItemView.findViewById(R.id.list_item);
			
			name.setText(customList.get(position).getFirstName());
			
			return listItemView;
		}
	}
	
	
	
	
	
	//listener that detects when we click on a contact from the list view
	class ListItemClickedListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
			
			//storing the contact clicked as a static field
			if(currentList==0){
				ContactDetails.contactClicked = ContactsList.getInstance().getContact(clickedViewPosition);
			}else if(currentList==1){
				ContactDetails.contactClicked = searchList.get(clickedViewPosition);
			}
			
			
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
					 
					 sort=which;
					 contactsList.sort(sort);
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
		updateContactList();
		
		//setting the display list to contactList and clearing the search list and search bar
		currentList=0;
		searchList.clear();
		searchBar.setText("");
		setupContactListView();
		
		//sorting the contact list to the current sort method
		((BaseAdapter) contacts.getAdapter()).notifyDataSetChanged();
		contactsList.sort(sort);
		
	
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	//iterates through the entire database and updates the contactList by attempting to add new contacts that have been created 
	private void updateContactList() { 
		
		db = openOrCreateDatabase(dbconstants.DATABASE_NAME, MODE_PRIVATE, null); 
		Cursor rows = db.rawQuery("SELECT * FROM " + dbconstants.TABLE_NAME, null); 
		
		
		int id; 
		String firstName = "";
		String lastName = ""; 
		String mobile = ""; 
		String home = ""; 
		String work = ""; 
		String email = ""; 
		String address = ""; 
		String DOB = ""; 
		String photoPath="";
		
		
		//iterating and extracting contact info
		if (rows.moveToFirst()) { 
			do { 
				
				//getting contact fields and storing them
				id = rows.getInt(rows.getColumnIndexOrThrow(dbconstants.CONTACT_ID)); 
				firstName = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_FIRST)); 
				lastName = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_LAST)); 
				mobile = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_MOBILE)); 
				home = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_HOME)); 
				work = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_WORK)); 
				email = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_EMAIL)); 
				address = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_ADDRESS));
				DOB = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_DOB)); 
				photoPath = rows.getString(rows.getColumnIndexOrThrow(dbconstants.CONTACT_IMAGE)); 
				

				//making a test contact object with the fields extracted
				Contact tryAdd = new Contact(id, firstName, lastName, mobile, home, work, email, address, DOB, photoPath); 
				boolean toAdd = true; 
				
				//iterating through the contactList and comparing the test contact object with all the contact objects in the contactList
				//if the test contact does not equal any contact in the list, it must be a new contact so add to contactList
				for (Contact contact : contactsList.getList()) { 

					
					//duplicate contact
					if (contact.equals(tryAdd)) { 
						toAdd = false; 
					} 
				}  
				
				//contact is a new contact
				if (toAdd) { 
					contactsList.getList().add(tryAdd);  
				} 
			} while (rows.moveToNext()); 
		} 
		
		db.close(); 
		

	}
						
}
