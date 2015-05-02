package nayan;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
@RunWith(Cucumber.class)
@CucumberOptions(
glue = {"cognizant.nayan.stepDefinitions","nayan"},
features = "src\\main\\resources",
plugin = {"pretty", "html:target/cucumber-html-report"})
public class NayanTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
