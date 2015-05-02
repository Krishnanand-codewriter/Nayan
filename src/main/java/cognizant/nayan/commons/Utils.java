package cognizant.nayan.commons;


/**
 * @author 416474
 *
 */
public class Utils {
	
	public static BrowserTypes getBrowserType(String browserType){
		if(browserType.toLowerCase().trim().contains("fire")){
			return BrowserTypes.FIREFOXDRIVER;
		}else if(browserType.toLowerCase().trim().contains("chrome")){
			return BrowserTypes.CHROMEDRIVER;
		}else if(browserType.toLowerCase().trim().contains("ie")){
			return BrowserTypes.IEDRIVERSERVER;
		}
		return null;
	}
	
	public static String getBrowserName(String browserType){
		if(browserType.toLowerCase().trim().contains("fire")){
			return "Firefox";
		}else if(browserType.toLowerCase().trim().contains("chrome")){
			return "Chrome";
		}else if(browserType.toLowerCase().trim().contains("ie")){
			return "IE";
		}
		return null;
	}
	
	public static ImageType getImageType(String runType){
		if(runType.toLowerCase().trim().contains("base")){
			return ImageType.BASE_IMAGE;
		}else if(runType.toLowerCase().trim().contains("current")){
			return ImageType.CURRENT_IMAGE;
		}else if(runType.toLowerCase().trim().contains("incompatible")){
			return ImageType.INCOMPATIBLE_IMAGE;
		}
		return null;
	}
	
	public static int getUserDefinedColor() {
		
		
		
		return 0;
		
	}

}
