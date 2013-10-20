package jtan325.contactmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



//The ContactsList uses the singleton pattern to ensure that there is only 1 contact list being instantiated
//and that all classes are using the same contact list

public class ContactsList {
	
	private List<Contact> contactList;
	private static ContactsList instance= null;
	
	
	
	//singleton contactsList object  
	public static ContactsList getInstance(){
		
		if (instance==null) {
			instance = new ContactsList();
			
		}
		return instance;
		
		
	}
	
	//private constructor
	private ContactsList() {
		
		contactList= new ArrayList<Contact>();
	
		
	}
	
	
	//adds a contact to the contact list
	public void add(Contact contact){
		
		contactList.add(contact);
		
	}
	
	//deletes a contact from the contact list
	public void delete(Contact contact){
		
		contactList.remove(contact);
		
	}
	
	//returns the size of the contact list
	public int size(){
		
		int size = contactList.size();
		return size;
	}
	
	
	//allows the sorting of the contact list by first name, last name and mobile number
	//uses the static comparator classes from Contact
	public void sort(int position){
		
		switch (position){
		
		case 0:
			Collections.sort(contactList, new Contact.firstNameComparator());
			break;
		
		case 1:
			Collections.sort(contactList, new Contact.lastNameComparator());
			break;	
			
		case 2:
			Collections.sort(contactList, new Contact.phoneComparator());
			break;	
		
		
		}
		
	}
	
	//returns the contact list as a list object
	public List<Contact> getList(){
		return contactList;
	}
	
	
	//returns a particular contact by position in the contact list
	public Contact getContact(int position){
		return contactList.get(position);
	}
	
	
	
	
}
