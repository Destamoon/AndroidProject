package jtan325.contactmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	
	public void add(Contact contact){
		
		contactList.add(contact);
		
		
		
	}
	
	public void delete(Contact contact){
		
		contactList.remove(contact);
		
		
	}
	
	public int size(){
		
		int size = contactList.size();
		return size;
	}
	
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
	
	
	public List<Contact> getList(){
		return contactList;
	}
	
	public Contact getContact(int position){
		return contactList.get(position);
	}
	
	
	
	
}
