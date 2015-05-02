package cognizant.nayan.commons;

import org.openqa.selenium.Beta;


//import cognizant.nayan.driver.BrowserDriver;

/**
 * @author 416474
 *
 */
@Beta
public class ParallelExecutor implements Runnable {
//	private String driverType;
	Utils utils = new Utils();
	
	public ParallelExecutor(String driverType) {
	//	this.driverType = driverType;
	}
	
	

	public void run() {
	//	BrowserTypes browserType = utils.getBrowserType(driverType);
		//BrowserDriver driver = new BrowserDriver(browserType);
		//driver.initDriver();	
	}
	

}
