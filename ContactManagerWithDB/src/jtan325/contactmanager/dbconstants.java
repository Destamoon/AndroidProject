package jtan325.contactmanager;

public class dbconstants {
	
	public final static String DATABASE_NAME = "Contacts.db";
	final static String TABLE_NAME = "contactsTable";
	final static String CONTACT_ID = "id";
	final static String CONTACT_FIRST = "firstName";
	final static String CONTACT_LAST = "lastName";
	final static String CONTACT_MOBILE = "mobile";
	final static String CONTACT_HOME = "home";
	final static String CONTACT_WORK = "work";
	final static String CONTACT_EMAIL = "email";
	
	public static final String DATABASE_CREATE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
			+ CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
			+ CONTACT_FIRST + " TEXT,"
			+ CONTACT_LAST + " TEXT,"
			+ CONTACT_MOBILE + " TEXT,"
			+ CONTACT_HOME + " TEXT,"
			+ CONTACT_WORK + " TEXT,"
			+ CONTACT_EMAIL + " TEXT);";
			
	
	
	
	protected static int currentId = 0;
	
	

}
