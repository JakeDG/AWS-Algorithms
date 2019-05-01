/* 
* Developed by: Jacob Gidley, jgidley@albany.edu, CSI 403
* This program receive as input an array of strings that it stores in a hash table using a hashing algorithm
* that puts the strings in the hash table based on the sum of all the lowercase letters in the string.
* It then returns and outputs the set of strings which collide in the hash table in the 
* order they appear in the input list.  
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

public class JsonStringHash 
{

    public JsonStringHash() 
	{
    }

    public String hash(String jsonStr) 
	{
	
        ObjectMapper mapper = new ObjectMapper();	// Create mapper to map JSON strings to the input list
		JsonSerializer serializer = new JsonSerializer();	// Serializer that formats strings into JSON format
		ArrayList<String> strList = new ArrayList<String>();	// Array list of output
		Hashtable<Integer, LinkedList<String>> nameTable = new Hashtable<Integer, LinkedList<String>>(); // Hashtable to store strings
		
		int hashKey; // Stores the value of the key given to a string from the hash function
		Integer key = new Integer(0); // Holds the key value of the table's elements
		Enumeration names; // Holds the enumeration value of the hash table's elements
		
        // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try
		{
			InList inList = mapper.readValue(jsonStr,InList.class);	// Read values from JSON input
			
			// Copy inList to an array of strings
			String[] strArray = inList.getInList();
			
			// If input list is empty then print a message
			if (strArray.length == 0)
			{
				return "<empty>";
			}
			
			// Hash each item in the input list
			for (int i = 0; i < strArray.length; i++)
			{
				hashKey = hashFunction(strArray[i]);
				
				// Collision detected, add to end if linked list in hash table the element.
				if (nameTable.containsKey(hashKey))
				{
					LinkedList<String> hashLinkList = nameTable.get(hashKey);
					hashLinkList.add("\"" + strArray[i] + "\"");
				}
				else 
				{
					// Create new linked list to store new element in hash table
					LinkedList<String> hashLinkList = new LinkedList<String>();
					hashLinkList.add("\"" + strArray[i] + "\"");
					nameTable.put(hashKey, hashLinkList);
				}
			}
			
			// Store the linked lists of collisions into a list
			names = nameTable.keys();
			while(names.hasMoreElements()) 
			{
				key = (Integer)names.nextElement(); // Get the element in the table
				
				List<String> hashNames = nameTable.get(key);
				
				if (nameTable.get(key).size() > 1) 
				{
                    strList.add(hashNames.toString());
                }
			}
			
			// Return list of output
			return strList.toString();
		}
        catch (Exception e) 
		{
			return "<error>";
        }
    }
	
	/* 
	* Hash function that returns the sum of the 
	* ASCII values of the lower-cased letters of the argument
	*/
	public int hashFunction(String str)
	{
		char chArray[];
		int sum = 0;
	
		chArray = str.toLowerCase().toCharArray(); // Convert string to lowercase and store as char Array
		
		// Sum up the lower-case ASCII values
		for(char c: chArray)
		{
			if (Character.isLetter(c))
			{
				sum += c; 
			}
		}
		
		return sum;
	}
}
