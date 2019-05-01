/* 
* Developed by: Jacob Gidley, jgidley@albany.edu, CSI 403
* This class creates a job object that will have the following properties: command, name, and priority.
* Also, the class contains a overloaded comparator in order for jobs to be sorted in a priority queue.
*/

package csi403; 

import java.io.Serializable;
import java.util.List; 

public class Job implements Serializable, Comparable<Job>
{
	private String cmd;	// Holds the command of the job
    private String name;	// Holds the name of the job
	private int pri;	// Holds the priority of the job
	
	// Default
	public Job() {};
	
	// Get methods
	public String getCMD() { return this.cmd; }
	public String getName() { return this.name; }
	public int getPri(){ return this.pri; }
	
	// Comparator to compare job objects in a priority queue
	public int compareTo(Job j)
	{
		if (pri > j.getPri())
		{
			return 1;
		}
		else if (pri < j.getPri())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}














