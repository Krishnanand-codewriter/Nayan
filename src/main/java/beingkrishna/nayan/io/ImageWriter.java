package cognizant.nayan.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import cognizant.nayan.commons.Commons;
import cognizant.nayan.commons.ImageType;
import cognizant.nayan.commons.NayanProperties;
import cognizant.nayan.commons.OSType;
import cognizant.nayan.io.IO.Paths;
import cognizant.nayan.stepDefinitions.NayanStepDefinitions;

/**
 * @author 416474
 *
 */
public class ImageWriter{

	private String mBaseImageVersion, mCurrentImageVersion, mIncompatibleImageVersion;
	private File mImageFile;
	private byte[] mImageArr;
	private OSType mOsType = new OSType();
	private NayanProperties mNayanProp = new NayanProperties();
	private String mImageFormat = mNayanProp.getImageFormat();
	

	public ImageWriter(File mImageFile, byte[] mImageArr){
		this.mImageFile = mImageFile;
		this.mImageArr = mImageArr;
	}

	public ImageWriter(File mImageFile){
		this.mImageFile = mImageFile;
	}

	public void writeImage(ImageType imageType, String scenario,File file){
		mBaseImageVersion = mNayanProp.getBaseVersion();
		mCurrentImageVersion = mNayanProp.getCurrentVersion();
		mIncompatibleImageVersion = mNayanProp.getIncompatibleVersion();
		switch(imageType){
		case BASE_IMAGE:
			if(mOsType.isOSLinuxType()){
				if(NayanStepDefinitions.sBrowserName.toLowerCase().contains("fire")){
					File op = new File(Paths.BASE_IMAGE_ABS_DIR_PATH+mBaseImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
					try {
						FileUtils.copyFile(file, op);
					} catch (IOException e) {
						
					}
				}else{
					try{
						ByteArrayInputStream in = new ByteArrayInputStream(mImageArr);
						BufferedImage im = ImageIO.read(in);
						File op = new File(Paths.BASE_IMAGE_ABS_DIR_PATH+mBaseImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
						ImageIO.write(im, mImageFormat, op);
					}catch (IOException e) {
					
					}
				}
			}else if(mOsType.isOSWinType()){
				if(NayanStepDefinitions.sBrowserName.toLowerCase().contains("fire")){
					File op = new File(Paths.BASE_IMAGE_ABS_DIR_PATH+mBaseImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
					try {
						FileUtils.copyFile(file, op);
					} catch (IOException e) {
					
					}
				}else{
					try{
						ByteArrayInputStream in = new ByteArrayInputStream(mImageArr);
						BufferedImage im = ImageIO.read(in);
						File op = new File(Paths.BASE_IMAGE_ABS_DIR_PATH+mBaseImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
						ImageIO.write(im, mImageFormat, op);
					}catch (IOException e) {
						
					}
				}
			}
			break;
		case CURRENT_IMAGE:
			if(mOsType.isOSLinuxType()){
				if(NayanStepDefinitions.sBrowserName.toLowerCase().contains("fire")){
					File op = new File(Paths.CURRENT_IMAGE_ABS_DIR_PATH+mCurrentImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
					try {
						FileUtils.copyFile(file, op);
					} catch (IOException e) {
					
					}
				}else{
					try{
						ByteArrayInputStream in = new ByteArrayInputStream(mImageArr);
						BufferedImage im = ImageIO.read(in);
						File op = new File(Paths.CURRENT_IMAGE_ABS_DIR_PATH+mCurrentImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
						ImageIO.write(im, mImageFormat, op);
					}catch (IOException e) {
						
					}
				}
			}else if(mOsType.isOSWinType()){
				if(NayanStepDefinitions.sBrowserName.toLowerCase().contains("fire")){
					File op = new File(Paths.CURRENT_IMAGE_ABS_DIR_PATH+mCurrentImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
					try {
						FileUtils.copyFile(file, op);
					} catch (IOException e) {
						
					}
				}else{
					try{
						ByteArrayInputStream in = new ByteArrayInputStream(mImageArr);
						BufferedImage im = ImageIO.read(in);
						File op = new File(Paths.CURRENT_IMAGE_ABS_DIR_PATH+mCurrentImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
						ImageIO.write(im, mImageFormat, op);
					}catch (IOException e) {
						
					}
				}
			}
			break;
		case INCOMPATIBLE_IMAGE:
			if(mOsType.isOSLinuxType()){
				try{
					ByteArrayInputStream in = new ByteArrayInputStream(mImageArr);
					BufferedImage im = ImageIO.read(in);
					File op = new File(Paths.INCOMPATIBLE_IMAGE_ABS_DIR_PATH+mIncompatibleImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
					ImageIO.write(im, mImageFormat, op);
					Commons.addToIncompatibleUrls(mImageFile.getName(), op.getAbsolutePath());
				}catch (IOException e) {
					
				}
			}else if(mOsType.isOSWinType()){
				try{
					ByteArrayInputStream in = new ByteArrayInputStream(mImageArr);
					BufferedImage im = ImageIO.read(in);
					File op = new File(Paths.INCOMPATIBLE_IMAGE_ABS_DIR_PATH+mIncompatibleImageVersion+File.separator+scenario+File.separator+mImageFile+"."+mImageFormat);
					ImageIO.write(im, mImageFormat, op);
					Commons.addToIncompatibleUrls(mImageFile.getName(), op.getAbsolutePath());
				}catch (IOException e) {
					
				}
			}
			break;
		}

	}
}



