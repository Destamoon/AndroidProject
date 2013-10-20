package jtan325.contactmanager;



//Class that just contains constants used for the database
public class dbconstants {
	
	//database columns fields
	public final static String DATABASE_NAME = "Contacts.db";
	final static String TABLE_NAME = "contactsTable";
	final static String CONTACT_ID = "id";
	final static String CONTACT_FIRST = "firstName";
	final static String CONTACT_LAST = "lastName";
	final static String CONTACT_MOBILE = "mobile";
	final static String CONTACT_HOME = "home";
	final static String CONTACT_WORK = "work";
	final static String CONTACT_EMAIL = "email";
	final static String CONTACT_ADDRESS = "address";
	final static String CONTACT_DOB = "DOB";
	
	
	//database create query
	public static final String DATABASE_CREATE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
			+ CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
			+ CONTACT_FIRST + " TEXT,"
			+ CONTACT_LAST + " TEXT,"
			+ CONTACT_MOBILE + " TEXT,"
			+ CONTACT_HOME + " TEXT,"
			+ CONTACT_WORK + " TEXT,"
			+ CONTACT_EMAIL + " TEXT,"
			+ CONTACT_ADDRESS + " TEXT,"
			+ CONTACT_DOB + " TEXT);";
			
	
	
	//an incremented id field used to ensure each contact created has a unique id 
	protected static int currentId = 0;
	
	

}
