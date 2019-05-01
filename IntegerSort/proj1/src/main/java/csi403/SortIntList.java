/* Jacob Gidley, CSI 403
* This program will accept a JSON array of integers and sort that array using bubble sort. 
* It will also calculate the time it takes in milliseconds for the sorting algorithm to finish. 
* Then it will output the sorted integer array as JSON message.
*/

package csi403;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.json.*;
import java.util.*;


// Extend HttpServlet class
public class SortIntList extends HttpServlet 
{

  // Standard servlet method 
  public void init() throws ServletException
  {
    // Do any required initialization here - likely none
  }

  // Standard servlet method - we will handle a POST operation
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    doService(request, response); 
  }

  // Standard servlet method - we will not respond to GET
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    // Set response content type and return an error message
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.println("{ \"message\" : \"Use POST!\"}");
  }


  // Our main worker method
  // Parses messages e.g. {"inList" : [5, 32, 3, 12]}
  // Returns the list reversed.   
  private void doService(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
    // Get received JSON data from HTTP request
    BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    String jsonStr = "";
	
	// Set response content type to be JSON
	response.setContentType("application/json");
			
	// Send back the response JSON message
	PrintWriter out = response.getWriter();
	
	try
	{
		if(br != null)
		{
			jsonStr = br.readLine();
		  
			// Create JsonReader object
			StringReader strReader = new StringReader(jsonStr);
			JsonReader reader = Json.createReader(strReader);

			// Get the singular JSON object (name:value pair) in this message.    
			JsonObject obj = reader.readObject();
			
			// From the object get the array named "inList"
			JsonArray inArray = obj.getJsonArray("inList");
			
			int[] numArray = new int[inArray.size()]; // Holds the integers from the JSON input array
			long startTime, endTime, duration; // Hold values needed to calculate the sorting algorithm time
		
			// Check if list is empty
			if (numArray.length != 0)
			{
				// Copy the data in the list to an int array
				for (int i = 0; i < inArray.size(); i++) 
				{
					numArray[i] = ((JsonNumber)inArray.getJsonNumber(i)).intValueExact();
				}
				
				startTime = System.currentTimeMillis(); // Get current time in milliseconds
				bubbleSort(numArray);	// Sort the array
				endTime = System.currentTimeMillis();
				duration = endTime - startTime; // Get the time it took the sort to complete in milliseconds
				
				// Copy the sorted integer array back into a JSON array
				JsonArrayBuilder outArrayBuilder = Json.createArrayBuilder();
				for (int j = 0; j < numArray.length; j++)
				{
					outArrayBuilder.add(numArray[j]);
				}
				
				// Output the sorted array in JSON format
				out.println("{ \"outList\" : " + outArrayBuilder.build().toString() + ",\n\"algorithm\" : \"Bubble Sort\",\n\"timeMS\" : " + duration + " }"); 
			}
			else
			{
				out.println("{ \"message\" : \"List is empty!\" }");
			}
		}
	}
	catch (Exception e)
	{
		out.println("{ \"message\" : \"Malformed JSON\" }"); // Print error message
	}
  }
  
  // Bubble sorts an array of integers
  public void bubbleSort(int[] nArray)
  {
	int temp;
	for (int i = 0; i < nArray.length-1; i++)
		for(int j = 0; j < nArray.length-i-1; j++)
		{
			if(nArray[j] > nArray[j+1])
			{
				temp = nArray[j];
				nArray[j] = nArray[j+1];
				nArray[j+1] = temp;
			}
		}
  }
  
  // Standard Servlet method
  public void destroy()
  {
    // Tear-down goes here here
  }
}

