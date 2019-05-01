/* 
* Developed by: Jacob Gidley, jgidley@albany.edu, CSI 403
* This class processes the incoming JSON input of accepts a graph of people where edges represent friendship relationships.  
* Return the suggested edges which would create direct friend relationships between two people who currently are at 
* most one friend away – two people who are “friends of a friend” (FOAFs).
*/

package csi403; 

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRelationships {

    public JsonRelationships() {
    }

    public String relships(String jsonStr) {
	
		OutList outList = new OutList(); // Output list object
		
        ObjectMapper mapper = new ObjectMapper(); // Create mapper to map JSON strings to the input list
		JsonSerializer serializer = new JsonSerializer(); // Serializer that formats strings into JSON format
		
		ArrayList<Person> people = new ArrayList<Person>(); // Stores a list of all the people in the input
		ArrayList<ArrayList<String>> recFriendList = new ArrayList<ArrayList<String>>(); // Stores a list of a list of friend recommendations
		
        // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try
		{
			InList inList = mapper.readValue(jsonStr,InList.class);	// Read values from JSON input
			
			// Copy inList to an array of relationship objects
			Relationship[] rels = inList.getInList();
			
			// If input list is empty then print a message
			if (rels.length == 0)
			{
				return "<empty>";
			}
			
			// Check to make sure that a person has only one friend
			for(Relationship r : inList.getInList())
			{
				if (r.getFriends().length != 2)
					return "<error>";
				
				// Create two new people with names supplied from relationship object
				Person pA = new Person(r.getFriends()[0]);
				Person pB = new Person(r.getFriends()[1]);
				
				Person temp; // Temp person
				
				// Add the two people to each others friends list
				pA.getFriendList().add(pB);
				pB.getFriendList().add(pA);
				
				// Check if person A not in the people array list 
				if ((temp = inList(pA, people)) == null)
				{
					people.add(pA); // Add person A to the people list
				}
				else
				{
					temp.getFriendList().add(pB); // Add person B to Person A's friends list
				}
				
				// Check if person B not in the people array list 
				if ((temp = inList(pB, people)) == null)
				{
					people.add(pB); // Add person A to the people list
				}
				else
				{
					temp.getFriendList().add(pA); // Add person A to Person B's friends list
				}
			}
			
			// Create an array of people from the people array list
			Person[] peopleArray = people.toArray(new Person[people.size()]);
			
			// Loop through all the people in the people list
			for(int i = 0; i < people.size(); i++)
			{
				
				// Loop through every person's friends list
				for(int j = 0; j < peopleArray[i].getFriendList().size(); j++)
				{
				
					// Loop through the friends of the friend of the current person
					for(int k = 0; k < peopleArray[i].getFriendList().get(j).getFriendList().size(); k++)
					{	
	
						// Check if current person is friends with their friend's friend
						if ((!peopleArray[i].getFriendList().get(j).getFriendList().get(k).equals(peopleArray[i])))
						{
							// Array list of the original ordering of the friends
							ArrayList<String> org = new ArrayList<String>();
							org.add(peopleArray[i].getFriendList().get(j).getFriendList().get(k).getName());
							org.add(peopleArray[i].getName());
					
							// Array list of the swapped original ordering of the friends
							ArrayList<String> swapped = new ArrayList<String>();
							swapped.add(peopleArray[i].getName());
							swapped.add(peopleArray[i].getFriendList().get(j).getFriendList().get(k).getName());
							
							// Check if the original or swapped relationship is in the list already (Ex. [Bob, Mary] == [Mary, Bob])
							if (!((recFriendList.contains(org)) || (recFriendList.contains(swapped))))
							{
								ArrayList<String> tempList = new ArrayList<String>();
								tempList.add(peopleArray[i].getName());
								tempList.add(peopleArray[i].getFriendList().get(j).getFriendList().get(k).getName());
								
								recFriendList.add(tempList); // Add to recommends lists
							}
						}
					}
				}
			}
		
			//outList.setOutList(recFriendList); // Create output list
		
			return serializer.serialize(recFriendList); // Return serialized output list of job names to the service
		}
        catch (Exception e) 
		{
			return "<error>";
        }
    }
	
	// Determines if a person is in the arraylist argument and if it is, then return it. Else return null. 
	public Person inList(Person p, ArrayList<Person> fList)
	{
		for (int i = 0; i < fList.size(); i++)
		{
			if (fList.get(i).equals(p))
				return fList.get(i);
		}
		
		return null;
	}
}
