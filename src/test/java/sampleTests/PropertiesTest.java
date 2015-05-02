package sampleTests;


import org.junit.Test;
import cognizant.nayan.commons.NayanProperties;

public class PropertiesTest {

	@Test
	public void test() {
		NayanProperties version = new NayanProperties();
		version.setScenarioName("Hey", 1);
		System.out.println(version.getBaseVersion());
	}

}
