/* 
* Developed by: Jacob Gidley, CSI 403
* This class creates a person object that will have the following properties: name and an array list of friends.
* Also, the class contains a overloaded equals() method in order to compare person objects.
*/

package csi403; 

import java.io.Serializable;
import java.util.*; 

public class Person implements Serializable
{	
	private String name;
	private ArrayList<Person> friendList = new ArrayList<Person>();
	
	// Default Constructor
	public Person() {};
	
	// Constructor with name and friend list
	public Person(String name, ArrayList<Person> fList) {
		this.name = name;
		this.friendList = fList;
	}
	
	// Constructor with name only
	public Person(String name)
	{
		this.name = name;
	}
	
	// Get methods
	public String getName() { return this.name; }
	public ArrayList<Person> getFriendList() { return friendList; }
	
	// Overridden equals method so people objects can be compared
	@Override
	public boolean equals(Object o) 
	{
		if (this == o)
		{
			return true;
		}
		if (o == null)
		{
			return false;
		}
		
		if (getClass() != o.getClass())
		{
			return false;
		}
		
		Person p = (Person)o;
		if (friendList == null) 
		{
			if (p.friendList != null)
			{
				return false;
			}
		} 
		if (name == null) 
		{
			if (p.name != null)
			{
				return false;
			}
		} else if (!name.equals(p.name))
		{
			return false;
		}
		
		return true;
	}
}