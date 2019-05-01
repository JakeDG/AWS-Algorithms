/* 
* Developed by: Jacob Gidley, jgidley@albany.edu, CSI 403
* This class creates a serializer that formats strings into JSON format.
*/

package csi403;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Vector;
import java.util.List; 

public class JsonSerializer {

    public JsonSerializer() {
    }

	public String serialize(Object obj) {
        String str = null; 
        try 
		{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            str = mapper.writeValueAsString(obj);
        } 
		catch (Exception e) 
		{
            return "<error>"; 
        }
		
        return str;
	}
}

