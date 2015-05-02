package cognizant.nayan.driver;

import java.io.File;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import cognizant.nayan.commons.BrowserTypes;
import cognizant.nayan.commons.ImageType;
import cognizant.nayan.commons.Locators;
import cognizant.nayan.commons.NayanProperties;
import cognizant.nayan.commons.OSType;
import cognizant.nayan.commons.Utils;
import cognizant.nayan.io.ImageWriter;
import cognizant.nayan.stepDefinitions.NayanStepDefinitions;

/**
 * @author Krishnanand
 *
 */
public class BrowserDriver {
	
	private BrowserTypes mBrowsertype;
	private OSType mOsType;
	private static WebDriver sDriver;
	private NayanProperties mNayanProp = new NayanProperties();
	private ImageWriter mWriter;
	private static byte[] sScreenshot;
	
	public BrowserDriver(BrowserTypes driverType){
		this.mBrowsertype = driverType;
	}

	public BrowserDriver() {
		this.mBrowsertype = BrowserTypes.FIREFOXDRIVER;
	}

	public BrowserDriver getBrowserDriverInstance(){
		
		return this;
	}

	public WebDriver initDriver(boolean paramisJavaScriptDisabled){
		switch (mBrowsertype) {
		case CHROMEDRIVER:
			String chromeDriverPath = mNayanProp.getChromeDriverPath();
			System.setProperty("webdriver.chrome.sDriver", chromeDriverPath);
			sDriver = new ChromeDriver();
			maxWin();
			return sDriver;

		case FIREFOXDRIVER:
			FirefoxProfile javascriptProfile = new FirefoxProfile();
			javascriptProfile.setPreference("javascript.enabled", paramisJavaScriptDisabled);
			sDriver = new FirefoxDriver(javascriptProfile);
			maxWin();
			return sDriver;

		case IEDRIVERSERVER: 
			mOsType = new OSType();
			String iEDriverPath = mNayanProp.getIEDriverPath();
			if(mOsType.isOSWinType()){
				System.setProperty("webdriver.ie.sDriver", iEDriverPath);
				sDriver = new InternetExplorerDriver();
				maxWin();
				return sDriver;
			}
			break;

		default:

			break;
		}
		return sDriver;

	}

	public void getURL(String url){
		sDriver.get(url);
	}
	
	public void navigateUrl(String url) {
		sDriver.navigate().to(url);
	}

	public String getTitle(){
		String title = sDriver.getTitle();
		return title;
	}

	public String getCurrentURL(){
		String currentURL = sDriver.getCurrentUrl();
		return currentURL;
	}

	public WebElement getPageElement(String element, Locators elementLocator){
		WebElement pageElement = null;
		switch (elementLocator) {
		case ID: 
			pageElement =  sDriver.findElement(By.id(element));
			break;

		case NAME:
			pageElement = sDriver.findElement(By.name(element));
			break;
		case CLASS:
			pageElement = sDriver.findElement(By.className(element));
			break;
		case CSS:
			pageElement = sDriver.findElement(By.cssSelector(element));
			break;
		case XPATH:
			pageElement = sDriver.findElement(By.xpath(element));
			break;
		case LINK_TEXT:
			pageElement = sDriver.findElement(By.linkText(element));
			break;
		case PARTIAL_LINK_TEXT:
			pageElement = sDriver.findElement(By.partialLinkText(element));
			break;
		case TAG_NAME:
			pageElement = sDriver.findElement(By.tagName(element));
			break;
		default:
			break;
		}

		return pageElement;
	}
	
	public List<WebElement> getPageElements(String element, Locators elementLocator){

		List<WebElement> pageElement = null;
		switch (elementLocator) {
		case ID: 
			pageElement =  sDriver.findElements(By.id(element));
			break;

		case NAME:
			pageElement = sDriver.findElements(By.name(element));
			break;
		case CLASS:
			pageElement = sDriver.findElements(By.className(element));
			break;
		case CSS:
			pageElement = sDriver.findElements(By.cssSelector(element));
			break;
		case XPATH:
			pageElement = sDriver.findElements(By.xpath(element));
			break;
		case LINK_TEXT:
			pageElement = sDriver.findElements(By.linkText(element));
			break;
		case PARTIAL_LINK_TEXT:
			pageElement = sDriver.findElements(By.partialLinkText(element));
			break;
		case TAG_NAME:
			pageElement = sDriver.findElements(By.tagName(element));
			break;
		default:
			break;
		}

		return pageElement;
	}
	
	public String getWindowHandle(){
		
		return sDriver.getWindowHandle();
	}

	public Set<String> getWindowHandles(){
		return sDriver.getWindowHandles();
	}

	public void closeBrowser(){
		sDriver.close();
	}

	public void quitBrowser(){
		sDriver.quit();
	}

	public void captureDocument(String scenario, File docFile){
		String runType = mNayanProp.getRunType();
		ImageType imageType = Utils.getImageType(runType);
		switch (ImageType.valueOf(runType.toUpperCase())) {
		case BASE_IMAGE:
			if(NayanStepDefinitions.sBrowserName.toLowerCase().contains("fire")){
				File  imFile = ((TakesScreenshot)sDriver).getScreenshotAs(OutputType.FILE);
				mWriter = new ImageWriter(docFile);
				mWriter.writeImage(imageType, scenario, imFile);
			}else{
			sScreenshot = ((TakesScreenshot)sDriver).getScreenshotAs(OutputType.BYTES);
			
			mWriter = new ImageWriter(docFile, sScreenshot);
			mWriter.writeImage(imageType, scenario,null);
			}
			break;
		case CURRENT_IMAGE:
			
			if(NayanStepDefinitions.sBrowserName.toLowerCase().contains("fire")){
				File  imFile = ((TakesScreenshot)sDriver).getScreenshotAs(OutputType.FILE);
				mWriter = new ImageWriter(docFile);
				mWriter.writeImage(imageType, scenario, imFile);
			}else{
			sScreenshot = ((TakesScreenshot)sDriver).getScreenshotAs(OutputType.BYTES);
			
			mWriter = new ImageWriter(docFile, sScreenshot);
			mWriter.writeImage(imageType, scenario,null);
			}
			break;

		default:
			break;
		}
				
	}

	public void maxWin(){
		sDriver.manage().window().maximize();
	}
	
	public void waitTillPageGetsLoaded(){
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver sDriver) {
				return ((JavascriptExecutor)sDriver).executeScript("return document.readyState").equals("complete");
			}
		};

		WebDriverWait wait = new WebDriverWait(sDriver,10);
		wait.until(expectation);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	public boolean isAlertPresent(){
		WebDriverWait wait = new WebDriverWait(sDriver, 10);
		if(wait.until(ExpectedConditions.alertIsPresent())==null){
			return false;
		}else{
			return true;
		}
	}
	
	public void authenticateAlert(){
		try{
		String username = mNayanProp.getProxyUser();
		String password = mNayanProp.getProxyPassword();
		Credentials credentials = new UserAndPassword(username, password);
		Alert alert = sDriver.switchTo().alert();
		alert.authenticateUsing(credentials);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
