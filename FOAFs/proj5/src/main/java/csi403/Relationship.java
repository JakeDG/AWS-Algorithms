/* 
* Developed by: Jacob Gidley, CSI 403
* This class creates a relationship object that will have the following property: A string array called friends.
*/

package csi403; 

import java.io.Serializable;
import java.util.*; 

public class Relationship implements Serializable
{
	private String[] friends; // Stores an array of strings that are friends
	
	// Default constructor
	public Relationship() {};
	
	// Get methods
	public String[] getFriends() { return this.friends; }
}














