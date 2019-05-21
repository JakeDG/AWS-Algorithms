/* 
* Developed by: Jacob Gidley, CSI 403
* This class processes the incoming JSON input of jobs and creates a priority queue of job objects. 
* Each job definition contains a name and a priority, with 0 being the best priority and positive integers representing lower priorities.
* After all enqueue and dequeue statements have been processed, the program will return the JSON representing the state of the queue (the list of job names, in priority order).
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

public class JsonPriorityQueue {

    public JsonPriorityQueue() {
    }

    public String queue(String jsonStr) {
	
        ObjectMapper mapper = new ObjectMapper();	// Create mapper to map JSON strings to the input list
		JsonSerializer serializer = new JsonSerializer();	// Serializer that formats strings into JSON format
		OutList outList = new OutList();	// Output list object
		ArrayList<String> jobList = new ArrayList<String>();	// Array list of jobs
		Queue<Job> jpQueue = new PriorityQueue<Job>();	// Priority queue of jobs
		
        // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try
		{
			InList inList = mapper.readValue(jsonStr,InList.class);	// Read values from JSON input
			
			// Copy inList to an array of job objects
			Job[] jobs = inList.getInList();
			
			// If input list is empty then print a message
			if (jobs.length == 0)
			{
				return "<empty>";
			}
			
			// Add elements from jobs array to the priority queue
			for(int i = 0; i < jobs.length; i++)
			{
				try
				{
					if (jobs[i].getCMD().equals("enqueue") && !jobs[i].getName().equals(null) && jobs[i].getPri() >= 0)
					{
						jpQueue.add(jobs[i]);	// Add job to the queue
					}
					else if (jobs[i].getCMD().equals("dequeue"))	// Check for dequeue command and if the queue still has elements to pop off
					{
						if (jpQueue.size() > 0)	// Check if queue is empty
						{
							jpQueue.remove();	// Pop the lowest priority job off the queue
						}
					}
					else
					{
						return "<error>";
					}
				}
				catch (Exception e)
				{
					return "<error>";
				}
			}
			
			// Create list of remaining jobs in the queue
			for (Job j : jpQueue)
			{
				jobList.add(j.getName());
			}
			
			outList.setOutList(jobList); // Create output list
		
			return serializer.serialize(jobList); // Return serialized output list of job names to the service
		}
        catch (Exception e) 
		{
			return "<error>";
        }
    }
}
