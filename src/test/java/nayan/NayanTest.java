package nayan;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
glue = {"cognizant.nayan.stepDefinitions","nayan"},
features = "/home/krishnanandb/eclipsewsp/Nayan/src/main/resources",
plugin = {"pretty", "html:target/cucumber-html-report"})
public class NayanTest {

}
