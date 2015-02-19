package cognizant.nayan.driver;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cognizant.nayan.commons.BrowserTypes;
import cognizant.nayan.commons.Commons;
import cognizant.nayan.commons.ImageType;
import cognizant.nayan.commons.Locators;
import cognizant.nayan.commons.OSType;
import cognizant.nayan.commons.Version;
import cognizant.nayan.io.ImageWriter;

public class BrowserDriver {
	
	BrowserTypes browsertype;
	OSType osType;
	WebDriver driver;
	Commons commons;
	Version version = new Version();
	ImageWriter writer;
	
	public BrowserDriver(BrowserTypes driverType){
		this.browsertype = driverType;
	}

	public BrowserDriver() {
		this.browsertype = BrowserTypes.FIREFOXDRIVER;
	}

	public void initDriver(){
		switch (browsertype) {
		case CHROMEDRIVER:
			System.setProperty("webdriver.chrome.driver", "/home/krishnanandb/Documents/Selenium/chromedriver");
			driver = new ChromeDriver();
			maxWin();
			break;

		case FIREFOXDRIVER:
			driver = new FirefoxDriver();
			maxWin();
			break;

		case IEDRIVERSERVER: 
			osType = new OSType();
			if(osType.isOSWinType()){
				System.setProperty("webdriver.ie.driver", "/src/main/resources/driverServers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				maxWin();
			}
			break;

		default:

			break;
		}

	}

	public void typeURL(String url){
		driver.get(url);
	}

	public String getTitle(){
		String title = driver.getTitle();
		return title;
	}

	public String getCurrentURL(){
		String currentURL = driver.getCurrentUrl();
		return currentURL;
	}

	public WebElement getPageElement(String element, Locators elementLocator){
		WebElement pageElement = null;
		switch (elementLocator) {
		case ID: 
			pageElement =  driver.findElement(By.id(element));
			break;

		case NAME:
			pageElement = driver.findElement(By.name(element));
			break;
		case CLASS:
			pageElement = driver.findElement(By.className(element));
			break;
		case CSS:
			pageElement = driver.findElement(By.cssSelector(element));
			break;
		case XPATH:
			pageElement = driver.findElement(By.xpath(element));
			break;
		case LINK_TEXT:
			pageElement = driver.findElement(By.linkText(element));
			break;
		case PARTIAL_LINK_TEXT:
			pageElement = driver.findElement(By.partialLinkText(element));
			break;
		case TAG_NAME:
			pageElement = driver.findElement(By.tagName(element));
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
			pageElement =  driver.findElements(By.id(element));
			break;

		case NAME:
			pageElement = driver.findElements(By.name(element));
			break;
		case CLASS:
			pageElement = driver.findElements(By.className(element));
			break;
		case CSS:
			pageElement = driver.findElements(By.cssSelector(element));
			break;
		case XPATH:
			pageElement = driver.findElements(By.xpath(element));
			break;
		case LINK_TEXT:
			pageElement = driver.findElements(By.linkText(element));
			break;
		case PARTIAL_LINK_TEXT:
			pageElement = driver.findElements(By.partialLinkText(element));
			break;
		case TAG_NAME:
			pageElement = driver.findElements(By.tagName(element));
			break;
		default:
			break;
		}

		return pageElement;
	}
	
	public String getWindowHandle(){
		
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles(){
		return driver.getWindowHandles();
	}

	public void closeBrowser(){
		driver.close();
	}

	public void quitBrowser(){
		driver.quit();
	}

	public void captureDocument(String scenario, File docFile){
		String runType = version.getRunType();
		switch (ImageType.valueOf(runType.toUpperCase())) {
		case BASE_IMAGE:
			byte[] screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			writer = new ImageWriter(docFile, screenShot);
			writer.writeImage(ImageType.BASE_IMAGE, scenario);
			break;

		default:
			break;
		}
				
	}

	public void maxWin(){
		driver.manage().window().maximize();
	}
}
