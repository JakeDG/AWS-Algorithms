/* 
* Developed by: Jacob Gidley, CSI 403
* This class is an object that will store an array list of an array list of strings that should be serialized before they are returned to the service.
*/

package csi403;

import java.util.*;
public class OutList 
{
	private ArrayList<ArrayList<String>> outList;
	public OutList() {}
	public void setOutList(ArrayList<ArrayList<String>> outList) {this.outList = outList;}
	public ArrayList<ArrayList<String>> getOutList() {return outList;}
}

