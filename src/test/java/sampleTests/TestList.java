package sampleTests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestList {

	public static void main(String[] args) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("Scenario1", "Value1");
	map.put("Scenario1", "Value2");
	
	Set<String> itr = map.keySet();
	Iterator<String> itr1 = itr.iterator();
	while(itr1.hasNext()){
		System.out.println(itr1.next());
	}
	
	System.out.println(map.get("Scenario1"));
		
	}
	
	
	
}
