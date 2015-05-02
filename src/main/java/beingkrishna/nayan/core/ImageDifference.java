package cognizant.nayan.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import cognizant.nayan.commons.Bool;
import cognizant.nayan.commons.Commons;
import cognizant.nayan.commons.ImageComparisonType;
import cognizant.nayan.commons.ImageType;
import cognizant.nayan.commons.PixelList;
import cognizant.nayan.io.ImageReader;
import cognizant.nayan.io.ImageWriter;
import cognizant.nayan.statuscode.StatusCodes;

/**
 * @author Krishnanand
 *
 */
public class ImageDifference {
	
	private BufferedImage mBaseImage, mIncompatibleImage;
	private ImageComparisonType mImageCompType;
	private BufferedImage mCurrentImage;
	private BufferedImage mExtremeIncompatibleImage = new BufferedImage(255, 255, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage mBaseCurrentNotFound = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
	private byte[] mImageArr;
	private File mImageFile;
	private String mScenario;
	private ImageReader mReader;
	private DifferencePercentage mDiffPercentage;
	private static String sPixelCoordinates;
	private PixelList mPixelList = new PixelList();
	private static final ImageType BASE_IMAGE_TYPE = ImageType.BASE_IMAGE;
	private static final ImageType CURRENT_IMAGE_TYPE = ImageType.CURRENT_IMAGE;
	private static final ImageType INCOMPATIBLE_IMAGE_TYPE = ImageType.INCOMPATIBLE_IMAGE;
	private ImageWriter mWriter;
	private RegionHighlighter mRegionHighlighter;
	private Commons mCommons = new Commons();

	public ImageDifference(File mImageFile,String mScenario, ImageComparisonType mImageCompType) {
		this.mImageFile = mImageFile;
		this.mScenario = mScenario;
		this.mImageCompType = mImageCompType;
		getImages();
	}

	public void getImages(){
		try{
			mReader = new ImageReader(mImageFile);
			mBaseImage = mReader.readImage(BASE_IMAGE_TYPE, mScenario);
			System.out.println(mBaseImage.getHeight()+" , "+mBaseImage.getWidth());
			mCurrentImage = mReader.readImage(CURRENT_IMAGE_TYPE, mScenario);
			System.out.println(mCurrentImage.getHeight()+" , "+mCurrentImage.getWidth());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public double getImageDifferencePercentage(){
		mDiffPercentage = new DifferencePercentage(mBaseImage, mCurrentImage,mImageFile);
		return mDiffPercentage.getDifferencePercentage(); 
	}

	public void writeIncompatibleImage() throws InterruptedException, IOException{
		switch (mImageCompType) {
		case REGION_HIGLIGHTER:
			try{
				mIncompatibleImage = createRegionOfInterest();
				mImageArr = mCommons.convertBufferedImageToByteAr(mIncompatibleImage);
				mWriter = new ImageWriter(mImageFile,mImageArr);
				mWriter.writeImage(INCOMPATIBLE_IMAGE_TYPE, mScenario,null);
			}catch(Exception e){
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND);
				e.printStackTrace();
			}
			break;
		default:
			try{
				mIncompatibleImage = getImageDifferenceByPixelsOrRGB(mImageCompType);
				if(mIncompatibleImage.equals(mCurrentImage)){
				} else if(mIncompatibleImage.equals(mExtremeIncompatibleImage)) {
					Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.EXTREMELY_INCOMPATIBLE_IMAGES);
				} else if(mIncompatibleImage.equals(mBaseCurrentNotFound)) {
					Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND);
				} else {
					mImageArr = mCommons.convertBufferedImageToByteAr(mIncompatibleImage);
					mWriter = new ImageWriter(mImageFile,mImageArr);
					mWriter.writeImage(INCOMPATIBLE_IMAGE_TYPE, mScenario,null);
				}
			}catch(Exception e){
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND);
				e.printStackTrace();
			}

			break;
		}


	}

	public BufferedImage createRegionOfInterest() throws InterruptedException{
		System.out.println("Image difference for mScenario "+mScenario+" is "+getImageDifferencePercentage()+"%");
		if (getImageDifferencePercentage()>0.0) {
			try{
				PixelGrabber grab1 = new PixelGrabber(mBaseImage, 0, 0, -1, -1, false);
				PixelGrabber grab2 =new PixelGrabber(mCurrentImage, 0,0,-1,-1,false);
				@SuppressWarnings("unused")
				int[] data1 = null;
				if(grab1.grabPixels()) {
					int width = grab1.getWidth();
					int height = grab1.getHeight();
					data1 = new	int[width* height];
					data1 = (int[])grab1.getPixels();
				}

				@SuppressWarnings("unused")
				int[] data2 = null;
				if(grab2.grabPixels()) {
					int width = grab2.getWidth();
					int height = grab2.getHeight();
					data2 = new	int[width* height];
					data2 = (int[])grab2.getPixels();
				}

				for(int j=0; j<grab1.getHeight(); j++){
					for(int i=0; i<grab1.getWidth(); i++){
						int rgb1 = mBaseImage.getRGB(i,j);
						int rgb2 = mCurrentImage.getRGB(i,j);
						int r1 = (rgb1 >> 16) & 0xff;
						int g1 = (rgb1 >>  8) & 0xff;
						int b1 = (rgb1      ) & 0xff;
						int r2 = (rgb2 >> 16) & 0xff;
						int g2 = (rgb2 >>  8) & 0xff;
						int b2 = (rgb2      ) & 0xff;
						if((r1!=r2) || (g1!=g2) ||(b1!=b2)){
							sPixelCoordinates = i+","+j;
							mPixelList.storePixelCoords(sPixelCoordinates);

						}
					}
				}


				mRegionHighlighter = new RegionHighlighter(mImageFile, mPixelList.getPixelListAsArray(),mScenario);
			}catch(Exception e){

			}

			return mRegionHighlighter.drawRegionOfInterest();

		}

		return mCurrentImage;

	}

	public BufferedImage getImageDifferenceByPixelsOrRGB(ImageComparisonType imgCompType)
	{
		double imageDifferencePercentage = getImageDifferencePercentage();
		if(imageDifferencePercentage==0.0){
			Commons.addFilesCompatibility(mImageFile.getName(), Bool.YES);
			Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.MATCHED_IMAGES);

			return mCurrentImage;
		} else if(imageDifferencePercentage == 100.0 | Double.isNaN(imageDifferencePercentage)) {
			if(StatusCodes.sActiveStatusCode == StatusCodes.BASE_IMAGE_NOT_FOUND) {
				Commons.addFilesCompatibility(mImageFile.getName(), Bool.EXTREMELY_INCOMPATIBLE);
				return mCurrentImage;
			} else if(StatusCodes.sActiveStatusCode == StatusCodes.CURRENT_IMAGE_NOT_FOUND) {
				Commons.addFilesCompatibility(mImageFile.getName(), Bool.EXTREMELY_INCOMPATIBLE);
				return mBaseImage;
			} else if(StatusCodes.sActiveStatusCode == StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND) {
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND);
				return mBaseCurrentNotFound;
			} else {
				Commons.addFilesCompatibility(mImageFile.getName(), Bool.EXTREMELY_INCOMPATIBLE);
				return mExtremeIncompatibleImage;
			}
		} else {
			Commons.addFilesCompatibility(mImageFile.getName(), Bool.NO);
			Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.MISMATCHED_IMAGES);
			BufferedImage im1 = null;
			BufferedImage im2 = null;
			//loading the two pictures
			//read and load the image
			BufferedImage input = mBaseImage;

			//build an image with the same dimension of the file read
			im1 =
					new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
			//object create to draw into the bufferedImage
			Graphics2D g2d = im1.createGraphics();
			//draw input into im
			g2d.drawImage(input, 0,0, null);
			//making all again for the second image

			BufferedImage input2 = mCurrentImage;
			//build an image with the same dimension of the file read
			im2 =
					new BufferedImage(input2.getWidth(), input2.getHeight(), BufferedImage.TYPE_INT_RGB);
			//object create to draw into the bufferedImage
			Graphics2D g2d2 = im2.createGraphics();
			//draw input into im
			g2d2.drawImage(input2, 0,0, null);
			if(imgCompType.equals(ImageComparisonType.RGB_PIXEL_COMPARATOR)){
				return getDifferenceByRGB(im1,im2); 
			}else if(imgCompType.equals(ImageComparisonType.PIXEL_COMPARATOR)){
				return getDifferenceByPixels(im1, im2);
			}
			return input2; 
		}

	}

	public BufferedImage getDifferenceByRGB(BufferedImage im1, BufferedImage im2)
	{
		BufferedImage resultImage =
				new BufferedImage(im1.getWidth(), im2.getHeight(), BufferedImage.TYPE_INT_RGB);

		double THR = 50;
		for(int h=0; h < im1.getHeight(); h++)
		{
			for(int w=0; w < im1.getWidth(); w++)
			{
				@SuppressWarnings("unused")
				int alpha1 = 0xff &(im1.getRGB(w, h)>>24);
				int red1 = 0xff &(im1.getRGB(w, h)>>16);
				int green1 = 0xff & (im1.getRGB(w, h)>>8);
				int blue1 = 0xff & im1.getRGB(w, h); 

				@SuppressWarnings("unused")
				int alpha2 = 0xff &(im2.getRGB(w, h)>>24);
				int red2 = 0xff &(im2.getRGB(w, h)>>16);
				int green2 = 0xff & (im2.getRGB(w, h)>>8);
				int blue2 = 0xff & im2.getRGB(w, h); 

				//euclidian distance to estimate the simil.
				double dist =0;
				dist = Math.sqrt(Math.pow((double)(red1-red2), 2.0)
						+Math.pow((double)(green1-green2), 2.0)
						+Math.pow((double)(blue1-blue2), 2.0) );
				if(dist >THR)
				{
					resultImage.setRGB(w, h, im1.getRGB(w, h));
				}
				else
				{
					resultImage.setRGB(w, h, 1);
				}
			} 
		} 
		return resultImage;

	}

	public BufferedImage getDifferenceByPixels(BufferedImage img1, BufferedImage img2) {
		final int w = img1.getWidth(), h = img1.getHeight(), highlight = Color.PINK.getRGB();
		final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
		final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);
		for (int i = 0; i < p1.length; i++) {
			if (p1[i] != p2[i]){
				p1[i] = highlight;

			}


		}
		final BufferedImage resultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		resultImage.setRGB(0, 0, w, h, p1, 0, w);
		return resultImage;
	}

}
