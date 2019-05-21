/* 
* Developed by: Jacob Gidley, CSI 403
* This program will provide a RESTful service which accepts as a POST of JSON an ordered list of points represented as (x, y) coordinates. 
* The points define the perimeter of a polygon. It returns the number of points with integer (x, y) coordinates which are fully enclosed by the polygon. 
* Points on the polygon itself are not included in the count – the points to be included in the count must be wholly within the area bounded by the polygon.
*/

package csi403;


// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


// Extend HttpServlet class
public class PolyPointService extends HttpServlet {

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
        String output = new JsonPolyPoints().polygonPoints(jsonStr); 
        
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
			out.println("{ \"message\" : \"Input list does not contain enough points to form a polygon.\" }");
		}
		else if (output == "<boundError>")
		{
			out.println("{ \"message\" : \"Error: Some points are out of bounds (only points 0-18 allowed).\" }");
		}
		else
		{
			out.println("{ \"count\" : " +  output  + " }"); 
		}
    }
}

