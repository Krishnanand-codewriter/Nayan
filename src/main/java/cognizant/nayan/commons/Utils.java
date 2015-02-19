package cognizant.nayan.commons;


public class Utils {
	
	public BrowserTypes browserType(String browserType){
		if(browserType.toLowerCase().trim().contains("fire")){
			return BrowserTypes.FIREFOXDRIVER;
		}else if(browserType.toLowerCase().trim().contains("chrome")){
			return BrowserTypes.CHROMEDRIVER;
		}else if(browserType.toLowerCase().trim().contains("ie")){
			return BrowserTypes.IEDRIVERSERVER;
		}
		return null;
	}

}
