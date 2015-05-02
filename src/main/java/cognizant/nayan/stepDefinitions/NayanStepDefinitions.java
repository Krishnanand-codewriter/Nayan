package cognizant.nayan.stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import cognizant.nayan.commons.BrowserTypes;
import cognizant.nayan.commons.Commons;
import cognizant.nayan.commons.ConcurrentRunner;
import cognizant.nayan.commons.Directories;
import cognizant.nayan.commons.ImageComparisonType;
import cognizant.nayan.commons.NayanProperties;
import cognizant.nayan.commons.RunType;
import cognizant.nayan.commons.Utils;
import cognizant.nayan.core.ImageDifference;
import cognizant.nayan.core.URLExtractor;
import cognizant.nayan.driver.BrowserDriver;
import cognizant.nayan.reporting.Reporter;
import cognizant.nayan.serialized.ImagesState;
import cognizant.nayan.serialized.ReadObject;
import cognizant.nayan.serialized.WriteObject;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author 416474
 *
 */

public final class NayanStepDefinitions{
	static WebDriver sDriver;
	BrowserDriver mBrowserDriver;
	BrowserDriver[] mBrowserDrivers;
	public static String sScenarioName,sBrowserName;
	static String[] sBrowserNames[];
	public static int sScenarioNum =0;
	NayanProperties mNayanProperties = new NayanProperties();
	Directories mDirectories;
	File mDocFile;
	String sScenario;
	List<File> mImgFiles = new ArrayList<File>();
	ImagesState mImgsState = new ImagesState();
	private String mBaseUrl;
	private Map<String, String> mUrlsMap;
	private static String sFilename;


	@Before
	public void getScenarioName(Scenario scenario) {
		sScenarioNum++;
		String tempStr = scenario.getId();
		sScenarioName = tempStr.substring(tempStr.indexOf(';')+1, tempStr.length());
		mNayanProperties.setScenarioName(sScenarioName.replace('-', '_'), sScenarioNum);
	}

	@Before 
	public void createDirectories() {
		mDirectories = new Directories();
		String scenarioName = getScenarioName();
		mDirectories.createBaseScenarioDirectory(scenarioName);
		mDirectories.createCurrentScenarioDirectory(scenarioName);
		mDirectories.createIncompatibleScenarioDirectory(scenarioName);
	}

	@Given("^I open a web browser through \"([^\"]*)\"$")
	public void I_open_a_web_browser_through(String arg1) throws Throwable {
		if(isNotIgnored()){
			String browser = null;
			if((arg1.equals(""))){
				browser = mNayanProperties.getDefaultBrowser();
			}else {
				browser = arg1;
			}
			boolean disableJavascript = mNayanProperties.isJavaScriptDisabled();
			BrowserTypes browserType = Utils.getBrowserType(browser);
			sBrowserName = Utils.getBrowserName(browser);
			mBrowserDriver = new BrowserDriver(browserType);
			mBrowserDriver.initDriver(disableJavascript);
		}
	}

	@Given("^I open a web browser through \"(.*?)\",\"(.*?)\"$")
	public void i_open_a_web_browser_through(String arg1, String arg2) throws Throwable {
		if(isNotIgnored()){
			String[] browsers = {arg1,arg2};
			ConcurrentRunner runner = new ConcurrentRunner(browsers);
			runner.execute();
		}

	}

	@When("^I enter the url \"([^\"]*)\"$")
	public void I_enter_the_url(String arg1) throws Throwable {
		if(isNotIgnored()){
			if(arg1.equals("") && mNayanProperties.getRunType().equals("Base_Image")){
				mBaseUrl = mNayanProperties.getBaseURL();
			}else if(arg1.equals("") && mNayanProperties.getRunType().equals("Current_Image")){
				mBaseUrl = mNayanProperties.getCurrentURL();
			}else{
				mBaseUrl = arg1;
			}

			mBrowserDriver = new BrowserDriver();
			mBrowserDriver = mBrowserDriver.getBrowserDriverInstance();
		}

	}

	@Deprecated
	@Then("^I should be navigated to the \"([^\"]*)\" page.$")
	public void I_should_be_navigated_to_the_page(String arg1) throws Throwable {	
		boolean isValidPage;
		String title = mBrowserDriver.getTitle();

		if(arg1.equals(title)){
			isValidPage = true;
		}else{
			isValidPage = false;
		}
		Assert.assertTrue(isValidPage);

	}
	
	@Deprecated
	@Then("^I take the screenshot as \"(.*?)\"$")
	public void i_take_the_screenshot_as(String imageName) throws Throwable {
		Thread.sleep(5000);
		sScenario = mNayanProperties.getScenarioName("Scenario"+sScenarioNum);
		mDocFile = new File(imageName.replace(" ", "_")+"_"+sBrowserName);
		mImgFiles.add(mDocFile);
		mBrowserDriver.captureDocument(sScenario, mDocFile);
	}

	@Then("^I quit$")
	public void i_quit() throws Throwable {
		if(isNotIgnored()){
			mBrowserDriver.quitBrowser();
		}
	}

	@Then("^I perform Image Comparison")
	public void i_perform_image_comparison() throws InterruptedException {
		if(isNotIgnored()){
			URLExtractor urlExtractor = new URLExtractor(mBaseUrl);
			urlExtractor.extractURLs(mImgsState);
			mUrlsMap = mImgsState.getUrlsMap();
			System.out.println(mUrlsMap.size());
			Set<String> keyUrls = mUrlsMap.keySet();
			Iterator<String> keyIterator = keyUrls.iterator();
			while (keyIterator.hasNext()) {
				String keyUrl = keyIterator.next();
				String keyText = mUrlsMap.get(keyUrl);
				String parsedKeyUrl = parseUrl(keyUrl);
				mBrowserDriver.getURL(parsedKeyUrl);
				mBrowserDriver.waitTillPageGetsLoaded();
				Thread.sleep(3000);
				if(!(Commons.isPageBroken(mBrowserDriver.getTitle()))){
					takeScreenShotAs(keyText);
				}
			}
			mBrowserDriver.quitBrowser();
		}	
	}

	private void takeScreenShotAs(String imageName) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

		}
		String scenarioName = getScenarioName();
		mDocFile = new File(imageName.replace(" ", "_")+"_"+sBrowserName);
		mImgsState.addtoImageFiles(mDocFile);
		mBrowserDriver.captureDocument(scenarioName, mDocFile);
	}

	@After
	public void compareImages() throws IllegalAccessException {
		if(!isNotIgnored()){
			for(File images: imgFiles()){
				String scenarioName = getScenarioName();
				ImageComparisonType imgCompType = Commons.getImageComparisonType();
				ImageDifference imDiff = new ImageDifference(images, scenarioName,imgCompType);
				try {
					imDiff.writeIncompatibleImage();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Error e) {
					e.printStackTrace();
				}
			}
			String date = Commons.getCurrentDate();
			String brandName = mNayanProperties.getBrandName();
			String folderName = brandName+"_"+date;
			String workbookname = Commons.getWorkbookName();
			String worksheetname = Commons.getWorksheetName();
			Reporter report = new Reporter(folderName, workbookname, worksheetname, sFilename);
			report.createReportFolder();
			report.createWorkbook();
			report.createSheet();
			report.developReport();
			report.writeWorkbook();
		} else {
			saveImageFiles();
			mImgFiles.clear();
			mImgsState.clearUrlsMap();
		}
	}

	private String getScenarioName() {
		String scenario = mNayanProperties.getScenarioName("Scenario"+sScenarioNum);
		String brandName = mNayanProperties.getBrandName();
		String locale = mNayanProperties.getLocale();
		StringBuilder builder = new StringBuilder();
		StringBuilder str = builder.append(scenario+"_"+brandName+"_"+locale);
		String scenarioName = str.toString();
		return scenarioName;
	}

	private List<File> imgFiles() {
		setFileName();	
		ReadObject<ImagesState> objState = new ReadObject<>(Commons.getRunType());
		ImagesState imgState = objState.readObjectState(sFilename);
		return imgState.getImageFiles(); //Reading object state
	}

	private void saveImageFiles() {
		setFileName();
		mNayanProperties.setFilename(sFilename);
		WriteObject<ImagesState> writeImagesState = new WriteObject<ImagesState>(mImgsState,Commons.getRunType() );
		writeImagesState.writeObjectState(sFilename); //Saving object state
	}

	private String parseUrl(String url) {
		String username = mNayanProperties.getProxyUser();
		String password = mNayanProperties.getProxyPassword();
		String parsedUrl = null;
		String[] urlSplitter = url.split("//");
		parsedUrl = urlSplitter[0]+"//"+username+":"+password+"@"+urlSplitter[1];
		return parsedUrl;
	}


	private void setFileName() {
		String brandname = mNayanProperties.getBrandName();
		String locale = mNayanProperties.getLocale();
		if(Commons.getRunType().equals(RunType.BASE)) {
			sFilename = brandname+mNayanProperties.getBaseVersion()+"_"+locale;
		}else if(Commons.getRunType().equals(RunType.CURRENT)) {
			sFilename = brandname+mNayanProperties.getCurrentVersion()+"_"+locale;
		}	
	}

	private Boolean isNotIgnored() {
		if(mNayanProperties.getRunType().contains("Incompatible")){
			return false;
		}else {
			return true;
		}
	}
}
