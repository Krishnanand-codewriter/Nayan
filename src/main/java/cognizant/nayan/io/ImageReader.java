package cognizant.nayan.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import cognizant.nayan.commons.ImageType;
import cognizant.nayan.commons.ImagesType;
import cognizant.nayan.commons.OSType;

public class ImageReader {

	FileInputStream fin;
	File imageFile;
	byte[] imageArr;
	List<File> imageFiles;
	List<byte[]> imagesArr;
	String fileLocation;
	String currentDir = System.getProperty("user.dir");
	OSType osType = new OSType();

	public ImageReader(File imageFile, byte[] imageArr){
		this.imageArr = imageArr;
	}

	public ImageReader(List<File> imageFiles, List<byte[]> imagesArr){
		this.imageFiles = imageFiles;
		this.imagesArr = imagesArr;
	}

	public void readImage(ImageType imageType){
		
		switch(imageType){
		case BASE_IMAGE:
			if(osType.isOSLinuxType()){
				try{
					fin = new FileInputStream(currentDir+"/src/main/resources/base_Images/"+imageFile);
					fin.read(imageArr);
					fin.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					fin = new FileInputStream(currentDir+"\\src\\main\\resources\\base_Images\\"+imageFile);
					fin.read(imageArr);
					fin.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
			
		case CURRENT_IMAGE:
			if(osType.isOSLinuxType()){
				try{
					fin = new FileInputStream(currentDir+"/src/main/resources/current_Images/"+imageFile);
					fin.read(imageArr);
					fin.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					fin = new FileInputStream(currentDir+"\\src\\main\\resources\\current_Images\\"+imageFile);
					fin.read(imageArr);
					fin.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case INCOMPATIBLE_IMAGE:
			if(osType.isOSLinuxType()){
				try{
					fin = new FileInputStream(currentDir+"/src/main/resources/incompatible_Images/"+imageFile);
					fin.read(imageArr);
					fin.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					fin = new FileInputStream(currentDir+"\\src\\main\\resources\\incompatible_Images\\"+imageFile);
					fin.read(imageArr);
					fin.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public void readImages(ImagesType imagesType){
		switch(imagesType){
		case BASE_IMAGES:
			if(osType.isOSLinuxType()){
				try{
					int count =0;
					for(File imageFile : imageFiles){
						fin = new FileInputStream(currentDir+"/src/main/resources/base_Images/"+imageFile);
						fin.read(imagesArr.get(count));
						fin.close();
						count++;
					}				
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					for(File imageFile : imageFiles){
						fin = new FileInputStream(currentDir+"\\src\\main\\resources\\base_Images\\"+imageFile);
						fin.read(imageArr);
						fin.close();
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
						fin = new FileInputStream(currentDir+"/src/main/resources/current_Images/"+imageFile);
						fin.read(imagesArr.get(count));
						fin.close();
						count++;
					}				
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					for(File imageFile : imageFiles){
						fin = new FileInputStream(currentDir+"\\src\\main\\resources\\current_Images\\"+imageFile);
						fin.read(imageArr);
						fin.close();
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
						fin = new FileInputStream(currentDir+"/src/main/resources/incompatible_Images/"+imageFile);
						fin.read(imagesArr.get(count));
						fin.close();
						count++;
					}				
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else if(osType.isOSWinType()){
				try{
					int count=0;
					for(File imageFile : imageFiles){
						fin = new FileInputStream(currentDir+"\\src\\main\\resources\\incompatible_Images\\"+imageFile);
						fin.read(imagesArr.get(count));
						fin.close();
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
