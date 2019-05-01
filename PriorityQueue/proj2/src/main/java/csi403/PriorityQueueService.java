/* 
* Developed by: Jacob Gidley, jgidley@albany.edu, CSI 403
* This program will provide a RESTful service which accepts as a POST of JSON a list of enqueue and dequeue statements onto an in-memory job queue. 
* Each job definition contains a name and a priority, with 0 being the best priority and positive integers representing lower priorities.
* After all enqueue and dequeue statements have been processed, the program will return the JSON representing the state of the queue (the list of job names, in priority order).
*/

package csi403;


// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


// Extend HttpServlet class
public class PriorityQueueService extends HttpServlet {

  // Standard servlet method 
    public void init() throws ServletException { 
        // Do any required initialization here - likely none
    }
    
    // Standard servlet method - we will handle a POST operation
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        doService(request, response); 
    }

    // Standard Servlet method
    public void destroy() { 
        // Do any required tear-down here, likely nothing.
    }

    // Standard servlet method - we will not respond to GET
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        // Set response content type and return an error message
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        // We can always create JSON by hand just concating a string.  
        out.println("{ 'message' : 'Use POST!'}");
    }
    
    // Our main worker method
    private void doService(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        // Get received JSON data from HTTP request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonStr = "";
		
        if(br != null){
            jsonStr = br.readLine();
        }
        
        // Create Json reader object and discern the class from the JSON message 
        String output = new JsonPriorityQueue().queue(jsonStr); 
        
        // Set response content type to be JSON
        response.setContentType("application/json");
		
        // Send back the name of the class as a JSON message
        PrintWriter out = response.getWriter();
		
		if (output == "<error>")
		{
			out.println("{ \"message\" : \"Malformed JSON\" }"); // Print error message
		}
		else if (output == "<empty>")
		{
			out.println("{ \"message\" : \"Input list is empty!\" }");
		}
		else
		{
			out.println("{ \"outList\" : " +  output  + " }"); 
		}
    }
}
