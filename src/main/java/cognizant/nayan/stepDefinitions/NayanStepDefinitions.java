package cognizant.nayan.stepDefinitions;

import java.io.File;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import cognizant.nayan.commons.BrowserTypes;
import cognizant.nayan.commons.Directories;
import cognizant.nayan.commons.Utils;
import cognizant.nayan.commons.Version;
import cognizant.nayan.driver.BrowserDriver;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class NayanStepDefinitions {
	static WebDriver driver;
	BrowserDriver browserDriver;
	public static String scenarioName;
	public static int scenarioNum =0;
	Utils utils;
	Version version = new Version();
	Directories directories;

	@Before
	public void getScenarioName(Scenario scenario){
		scenarioNum++;
		String tempStr = scenario.getId();
		scenarioName = tempStr.substring(tempStr.indexOf(';')+1, tempStr.length());
		version.setScenarioName(scenarioName.replace('-', '_'), scenarioNum);
	}

	@Before 
	public void createDirectories(){
		directories = new Directories();
		String scenario = version.getScenarioName("Scenario"+scenarioNum);
		directories.createBaseScenarioDirectory("Scenario_"+scenario);
		directories.createCurrentScenarioDirectory("Scenario_"+scenario);
		directories.createIncompatibleScenarioDirectory("Scenario_"+scenario);
	}

	@Given("^I open a web browser through \"([^\"]*)\"$")
	public void I_open_a_web_browser_through(String arg1) throws Throwable {
		utils = new Utils();
		BrowserTypes browserType = utils.browserType(arg1);
		browserDriver = new BrowserDriver(browserType);
		browserDriver.initDriver();
	}

	@When("^I enter the url \"([^\"]*)\"$")
	public void I_enter_the_url(String arg1) throws Throwable {

		browserDriver.typeURL(arg1);

	}

	@Then("^I should be navigated to the \"([^\"]*)\" page.$")
	public void I_should_be_navigated_to_the_page(String arg1) throws Throwable {	    
		boolean isValidPage;
		String title = browserDriver.getTitle();

		if(arg1.equals(title)){
			isValidPage = true;
		}else{
			isValidPage = false;
		}
		Assert.assertTrue(isValidPage);

	}
	
	@Then("^I take the screenshot as \"(.*?)\"$")
	public void i_take_the_screenshot_as(String arg1) throws Throwable {
		String scenario = "Scenario_"+version.getScenarioName("Scenario"+scenarioNum);
		File docFile = new File(arg1);
		browserDriver.captureDocument(scenario, docFile);
	}


}
