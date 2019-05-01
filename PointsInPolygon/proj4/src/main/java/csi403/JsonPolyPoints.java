/* 
* Developed by: Jacob Gidley, jgidley@albany.edu, CSI 403
* This class processes the incoming JSON input of points on a polygon and returns the number of points with integer (x, y) coordinates enclosed by the polygon.
*/

package csi403; 

import java.io.*;
import java.util.*;
import java.awt.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPolyPoints {

    public JsonPolyPoints() {
    }

    public String polygonPoints(String jsonStr) {
	
        ObjectMapper mapper = new ObjectMapper();	// Create mapper to map JSON strings to the input list
		JsonSerializer serializer = new JsonSerializer();	// Serializer that formats strings into JSON format
		Polygon p = new Polygon();
		int totalPnts = 0;
		
        // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try
		{
			InList inList = mapper.readValue(jsonStr,InList.class);	// Read values from JSON input
			
			// Copy inList to an array of point objects
			Point[] points = inList.getInList();
			
			// If input list is empty then print a message
			if (points.length < 3)
			{
				return "<empty>";
			}
			
			// Check if all points are in the bounds of a 19x19 grid square
			for(int i = 0; i < points.length; i++)
			{
				if (points[i].getX() < 0 || points[i].getY() < 0 || points[i].getX() > 18 || points[i].getY() > 18)
					return "<boundError>";
			}
			
			/* 
			* Add points from points array to the polygon 
			*/
			for(int i = 0; i < points.length; i++)
			{
				for(int j = i+1; j < points.length; j++)
				{	
					p.addPoint(i,j); // Add the point to the polygon
				}
			}
			
			/* Inspect every possible point in the 19x19 grid square*/
			for(int i = 0; i <= 18; i++)
			{
				for(int j = 0; j <= 18; j++)
				{
					Point pnt = new Point(); // Create a temp point - ex. (i = 0, j = 0) => (0,0)
					pnt.setX(i);
					pnt.setY(j);
					
					if (contains(pnt, points)) // Check if point (i, j) is inside polygon
						totalPnts++; // Add to total points
				}
			}
			
			Integer count = new Integer(totalPnts); // Convert int to integer object for serialization
			
			return serializer.serialize(count); // Return serialized total of points in the polygon to the service
		}
        catch (Exception e) 
		{
			return "<error>";
        }
    }
	
	/* Tests all of the points on the grid to see if they are in the polygon */
	public boolean contains(Point test, Point[] points) 
	{
      int i;
      int j;
      boolean result = false;
	 
      for(i = 0, j = points.length - 1; i < points.length; j = i++) 
	  {
		// Check of on a polygon line segment
		if (pointOnSegment(points[i],points[j],test)) // Check if point is on a line segment of the polygon
		{
			return false;
		}
		
		// Check for intersections
		if (((points[i].getY() > test.getY()) != (points[j].getY() > test.getY())) &&
        (test.getX() < (points[j].getX() - points[i].getX()) * (test.getY() - points[i].getY()) / (points[j].getY() - points[i].getY()) + points[i].getX()))
		{
          result = !result;
		}
      }
      return result;
    }
	
	/* Tests to see if a point is located on a line of the polygon */
	public boolean pointOnSegment(Point a, Point b, Point c) 
	{
		double dist, acDist, bcDist;
		 
		dist = Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2)); // Distance of the polygon's line segment start and end points

		acDist = Math.sqrt(Math.pow(c.getX() - a.getX(), 2) + Math.pow(c.getY() - a.getY(), 2)); // Distance between a point on the grid and one of the polygons starting points of a line segment
		bcDist = Math.sqrt(Math.pow(c.getX() - b.getX(), 2) + Math.pow(c.getY() - b.getY(), 2)); // Distance between a point on the grid and one of the polygons points ending points of a line segment
		
		if (dist == (acDist + bcDist)) // If (distance(A, C) + distance(B, C) == distance(A, B)) then the point, C, is on the line, mathematically speaking.
			return true;
		return false;
	}	
}
