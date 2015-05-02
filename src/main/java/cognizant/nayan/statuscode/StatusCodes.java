package cognizant.nayan.statuscode;

public final class StatusCodes {
	
	//Status codes for base and current images
	public static final int BASE_IMAGE_FOUND = 800;
	public static final int BASE_IMAGE_NOT_FOUND = 801;
	public static final int CURRENT_IMAGE_FOUND = 900;
	public static final int CURRENT_IMAGE_NOT_FOUND = 901;
	
	//Status codes for base and current Url's
	public static final int BASE_URL_FOUND = 802;
	public static final int BASE_URL_NOT_FOUND = 803;
	public static final int CURRENT_URL_FOUND = 902;
	public static final int CURRENT_URL_NOT_FOUND = 903;
	
	//Active status code helps in identifying which image is not available
	public static int sActiveStatusCode;
	
	//Status code when both base and current images are not available. An absurd case!!
	public static final int BASE_CURRENT_IMAGES_NOT_FOUND = 007;
	
	/*Extremely Incompatible Images have no pixel in common. These are found when error messages are displayed in either
	 * base or current url's
	 */
	public static final int EXTREMELY_INCOMPATIBLE_IMAGES = 999;
	
	// Matched Images
	
	public static final int MATCHED_IMAGES = 0;
	
	//Mismatched images
	
	public static final int MISMATCHED_IMAGES = 9;
	
}
