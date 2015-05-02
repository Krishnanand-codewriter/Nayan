package cognizant.nayan.commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


/**
 * @author 416474
 *
 */
public class Commons{
	
	
	private static NayanProperties sNayanProp = new NayanProperties();
	private static Map<String, Bool> sImgCompatibility = new HashMap<>();
	private static Map<String, String> sIncompatibleUrls = new HashMap<>();
	private static Map<String, String> sRemarks = new HashMap<>();
	private static Map<String, Integer> sStatusCodesMap = new HashMap<>();
	
	public void SleepTime(long millisec){
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		//System.out.println(currentDate);
		return currentDate;
	}
	
	public BufferedImage convertByteArToBufferedImage(byte[] imageArray){
		BufferedImage buffImg = null;
		ByteArrayInputStream in = new ByteArrayInputStream(imageArray);
		try {
			buffImg = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffImg;
	}
	
	public byte[] convertBufferedImageToByteAr(BufferedImage image) throws IOException{
		ByteArrayOutputStream opStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", opStream);
		opStream.flush();
		byte[] imageInByte = opStream.toByteArray();
		opStream.close();
		return imageInByte;
		
	}
	
	public byte[] convertFileToByteAr(File file) throws FileNotFoundException{
		byte[] fileToByte = null;
		@SuppressWarnings("resource")
		FileInputStream fIn = new FileInputStream(file);
		ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
		byte[] buf = new byte[(int) file.length()];
		try {
			for(int readnum; (readnum=fIn.read(buf))!=-1;){
				byteOs.write(buf, 0, readnum);
			}
		} catch (Exception e) {
		}
		fileToByte = byteOs.toByteArray();
				
		
		return fileToByte;
	}
	
	public static boolean isPageBroken(String title) {
		if (title.equalsIgnoreCase("Oops! No results found!")) {
			return true;
		} else if (title.matches("4[0-9]{2}.+")) {
			return true;
		} else if (title.matches("3[0-9]{2}.+")) {
			return true;
		} else if (title.equalsIgnoreCase("Problem loading page")) {
			return true;
		} else if (title.equalsIgnoreCase("Slim Application Error")) {
			return true;
		} else if (title
				.equalsIgnoreCase("Error Occured While Processing Request")) {
			return true;
		} else if(title.equalsIgnoreCase("Internal Server Error")){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isAllLinksIncluded() {
		NayanProperties nayanProp = new NayanProperties();
		String allLinksIncluded = nayanProp.getIncludeAllLinks();
		switch (Bool.valueOf(allLinksIncluded)) {
		case YES:
			return true;
		case NO:
			return false;
		default:
			return true;
		}
	}
	
	public static RunType getRunType() {
		if(sNayanProp.getRunType().equals("Base_Image")){
			return RunType.BASE;
		}else if(sNayanProp.getRunType().equals("Current_Image")){
			return RunType.CURRENT;
		}else{
			return RunType.CURRENT;
		}
	}
	
	public static void addFilesCompatibility(String fileName, Bool boolType) {
		sImgCompatibility.put(fileName, boolType);
	}
	
	public static Map<String, Bool> getFilesCompatibility() {
		return sImgCompatibility;
	}
	
	public static void addToIncompatibleUrls(String fileName, String path) {
		sIncompatibleUrls.put(fileName, path);
	}
	
	public static Map<String, String> getIncompatibleUrls() {
		return sIncompatibleUrls;
	}
	
	public static void addToRemarks(String fileName, String remark) {
		sRemarks.put(fileName, remark);
	}
	
	public static Map<String, String> getRemarksMap(){
		return sRemarks;
	}
	
	public static void addToStatusCodes(String paramFilename, Integer paramStatusCode) {
		sStatusCodesMap.put(paramFilename, paramStatusCode);
	}
	
	public static Map<String, Integer> getStatusCodes() {
		return sStatusCodesMap;
	}
	
	public static String getWorkbookName() {
		String reportName = sNayanProp.getWorkbookname();
		String tempReportName = null;
		if(reportName.equals("")) {
			tempReportName = sNayanProp.getBrandName();
			return tempReportName;
		}else {
			return reportName;
		}
	}
	
	public static String getWorksheetName() {
		String sheetName = sNayanProp.getWorksheetname();
		String tempSheetName = null;
		if(sheetName.equals("")) {
			tempSheetName = sNayanProp.getLocale();
			return tempSheetName;
		} else {
			return sheetName;
		}
	}

	public static ImageComparisonType getImageComparisonType() {
		String imgCompTypeProp = sNayanProp.getImageComparisonType();
		if(imgCompTypeProp.contains("Region")) {
			return ImageComparisonType.REGION_HIGLIGHTER;
		} else if(imgCompTypeProp.toUpperCase().contains("RGB")) {
			return ImageComparisonType.RGB_PIXEL_COMPARATOR;
		} else {
			return ImageComparisonType.PIXEL_COMPARATOR;
		} 
	}

}
