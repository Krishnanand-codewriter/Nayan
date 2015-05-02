package cognizant.nayan.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krishnanand
 *
 */
public class PixelList{
	
	private static List<String> sPixelList = new ArrayList<String>();
	private static String[] sPixelArray;
	
	
	public void storePixelCoords(String pixelCoords){
		sPixelList.add(pixelCoords);
	}
	
	public List<String> getPixelList(){
		
		return sPixelList;
	}
	
	public String[] getPixelListAsArray(){
		
		sPixelArray = sPixelList.toArray(new String[sPixelList.size()]);
		
		return sPixelArray;
		
	}
	
	public void viewPixelList(){
		System.out.println(sPixelList);
	}

}
