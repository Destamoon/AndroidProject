package jtan325.contactmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * This class represents the main list of contacts. The ContactsList uses the singleton pattern to 
 * ensure that there is only 1 contact list being instantiated and that all classes are using the same contact list.
 * 
 * @author jtan325
 *
 */

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
	//uses private comparator classes from further below
	public void sort(int position){
		
		switch (position){
		
		//compare by first name
		case 0:
			Collections.sort(contactList, new firstNameComparator());
			break;
		
		//compare by last name
		case 1:
			Collections.sort(contactList, new lastNameComparator());
			break;	
		
		//compare by phone number	
		case 2:
			Collections.sort(contactList, new phoneComparator());
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
	
	
	
	
	//first name comparator
	private class firstNameComparator implements Comparator<Contact>{

		@Override
		public int compare(Contact first, Contact second) {
			
			return first.getFirstName().compareTo(second.getFirstName());
		}
	}
	
	
	//last name comparator
	private class lastNameComparator implements Comparator<Contact>{

		@Override
		public int compare(Contact first, Contact second) {
			
			return first.getLastName().compareTo(second.getLastName());
		}
	}
	
	
	//phone number comparator
	private class phoneComparator implements Comparator<Contact>{

		@Override
		public int compare(Contact first, Contact second) {
			
			return first.getMobile().compareTo(second.getMobile());
		}
	}
	
	
	
}
