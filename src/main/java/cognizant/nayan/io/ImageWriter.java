package cognizant.nayan.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cognizant.nayan.commons.ImageType;
import cognizant.nayan.commons.ImagesType;
import cognizant.nayan.commons.OSType;
import cognizant.nayan.commons.Version;

public class ImageWriter {

	FileOutputStream fOut;
	String baseImageVersion, currentImageVersion, incompatibleImageVersion;
	static String scenario;
	File imageFile;
	byte[] imageArr;
	List<File> imageFiles;
	List<byte[]> imagesArr;
	String fileLocation;
	String currentDir = System.getProperty("user.dir");
	OSType osType = new OSType();
	Version version = new Version();

	public ImageWriter(File imageFile, byte[] imageArr){
		this.imageFile = imageFile;
		this.imageArr = imageArr;
	}

	public ImageWriter(List<File> imageFiles, List<byte[]> imagesArr){
		this.imageFiles = imageFiles;
		this.imagesArr = imagesArr;
	}

	public ImageWriter(File imageFile, String fileLocation){
		this.imageFile = imageFile;
		this.fileLocation = fileLocation;
	}

	public ImageWriter(List<File> imageFiles, String fileLocation){
		this.imageFiles = imageFiles;
		this.fileLocation = fileLocation;
	}

	public void writeImage(ImageType imageType, String scenario){
		baseImageVersion = version.getBaseVersion();
		currentImageVersion = version.getCurrentVersion();
		incompatibleImageVersion = version.getIncompatibleVersion();
		//scenario = version.getScenarioName(scenario);
		switch(imageType){
		case BASE_IMAGE:
			if(osType.isOSLinuxType()){
				try{
					fOut = new FileOutputStream(currentDir+"/src/main/resources/base_Images/Scenario_Directory_V"+baseImageVersion+"/"+scenario+"/"+imageFile+".png");
					fOut.write(imageArr);
					fOut.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					fOut = new FileOutputStream(currentDir+"\\src\\main\\resources\\base_Images\\"+imageFile);
					fOut.write(imageArr);
					fOut.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case CURRENT_IMAGE:
			if(osType.isOSLinuxType()){
				try{
					fOut = new FileOutputStream(currentDir+"/src/main/resources/current_Images/"+imageFile);
					fOut.write(imageArr);
					fOut.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					fOut = new FileOutputStream(currentDir+"\\src\\main\\resources\\current_Images\\"+imageFile);
					fOut.write(imageArr);
					fOut.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case INCOMPATIBLE_IMAGE:
			if(osType.isOSLinuxType()){
				try{
					fOut = new FileOutputStream(currentDir+"/src/main/resources/incompatible_Images/"+imageFile);
					fOut.write(imageArr);
					fOut.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					fOut = new FileOutputStream(currentDir+"\\src\\main\\resources\\incompatible_Images\\"+imageFile);
					fOut.write(imageArr);
					fOut.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}
		
	}

	public void writeImages(ImagesType imagesType){
		switch(imagesType){
		case BASE_IMAGES:
			if(osType.isOSLinuxType()){
				try{
					int count =0;
					for(File imageFile : imageFiles){
						fOut = new FileOutputStream(currentDir+"/src/main/resources/base_Images/"+imageFile);
						fOut.write(imagesArr.get(count));
						fOut.close();
						count++;
					}				
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					int count =0;
					for(File imageFile : imageFiles){
					fOut = new FileOutputStream(currentDir+"\\src\\main\\resources\\base_Images\\"+imageFile);
					fOut.write(imagesArr.get(count));
					fOut.close();
					count++;
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case CURRENT_IMAGES:
			if(osType.isOSLinuxType()){
				try{
					int count =0;
					for(File imageFile : imageFiles){
						fOut = new FileOutputStream(currentDir+"/src/main/resources/current_Images/"+imageFile);
						fOut.write(imagesArr.get(count));
						fOut.close();
						count++;
					}				
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					int count =0;
					for(File imageFile : imageFiles){
					fOut = new FileOutputStream(currentDir+"\\src\\main\\resources\\current_Images\\"+imageFile);
					fOut.write(imagesArr.get(count));
					fOut.close();
					count++;
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case INCOMPATIBLE_IMAGES:
			if(osType.isOSLinuxType()){
				try{
					int count =0;
					for(File imageFile : imageFiles){
						fOut = new FileOutputStream(currentDir+"/src/main/resources/incompatible_Images/"+imageFile);
						fOut.write(imagesArr.get(count));
						fOut.close();
						count++;
					}				
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					int count =0;
					for(File imageFile : imageFiles){
					fOut = new FileOutputStream(currentDir+"\\src\\main\\resources\\incompatible_Images\\"+imageFile);
					fOut.write(imagesArr.get(count));
					fOut.close();
					count++;
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}
		
		
	}

	
	
}
