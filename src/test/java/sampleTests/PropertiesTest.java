package sampleTests;


import org.junit.Test;

import cognizant.nayan.commons.Version;

public class PropertiesTest {

	@Test
	public void test() {
		Version version = new Version();
		version.setScenarioName("Hey", 1);
		System.out.println(version.getBaseVersion());
	}

}
