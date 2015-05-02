package cognizant.nayan.statuscode;

public final class StatusCodeValue {
	
	public static final String BASE_IMAGE_FOUND = "Base Image is available";
	public static final String CURRENT_IMAGE_FOUND = "Current Image available for Base Image Comparison";
	public static final String BASE_IMAGE_NOT_FOUND = "Base Image not available";
	public static final String CURRENT_IMAGE_NOT_FOUND = "Current Image not available for Base Image Comparison";
	
	//I feel these status code values might be redundant but holding them till a
	//round of execution.
	public static final String BASE_URL_FOUND = "Base URL found";
	public static final String BASE_URL_NOT_FOUND = "Base URL not found";
	public static final String CURRENT_URL_FOUND = "Current URL found";
	public static final String CURRENT_URL_NOT_FOUND = "Current URL not found";
	
	public static final String BASE_CURRENT_IMAGES_NOT_FOUND = "Both base and current images not found,"
			+ "looks like we couldn't save base and current images for the respective url as the url was never navigated, an asbsurd case!!";
	
	public static final String EXTREMELY_INCOMPATIBLE_IMAGES = "Either base or current url has thrown error. Refer base image/url and current image/url";
	
	public static final String MATCHED_IMAGES = "Current Image matches with its base image";
	
	public static final String MISMATCHED_IMAGES = "Current Image does not match with its base image";
	
	public static final String ERROR = "Unknown Error";
}
