package cognizant.nayan.core;

import java.awt.image.BufferedImage;
import java.io.File;

import cognizant.nayan.commons.Commons;
import cognizant.nayan.statuscode.StatusCodes;

public class DifferencePercentage{

	private BufferedImage mBaseImage;
	private BufferedImage mCurrentImage;
	private File mImageFile;

	public DifferencePercentage(BufferedImage paramBaseImage, BufferedImage paramCurrentImage, File paramImageFile) {
		this.mBaseImage = paramBaseImage;
		this.mCurrentImage = paramCurrentImage;
		this.mImageFile = paramImageFile;
	}

	public double getDifferencePercentage(){
		try{
			int width1 = 0,width2=0,height1=0,height2=0;
			if(mBaseImage == null) {
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.BASE_IMAGE_NOT_FOUND);
				StatusCodes.sActiveStatusCode = StatusCodes.BASE_IMAGE_NOT_FOUND;
			} else {
				width1 = mBaseImage.getWidth(null);
				height1 = mBaseImage.getHeight(null);
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.BASE_IMAGE_FOUND);
			}
			if(mCurrentImage == null) {
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.CURRENT_IMAGE_NOT_FOUND);
				StatusCodes.sActiveStatusCode = StatusCodes.CURRENT_IMAGE_NOT_FOUND;
			} else {
				width2 = mCurrentImage.getWidth(null);
				height2 = mCurrentImage.getHeight(null);
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.CURRENT_IMAGE_FOUND);
			}
			if(mBaseImage == null && mCurrentImage == null) {
				Commons.addToStatusCodes(mImageFile.getName(), StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND);
				StatusCodes.sActiveStatusCode = StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND;
			}

			if ((width1 != width2) || (height1 != height2)) {
				System.err.println("Error: Images dimensions mismatch"); 
			}else {
				if(width1>0 && width2 >0) {
					long diff = 0;
					for (int y = 0; y < height1; y++) {
						for (int x = 0; x < width1; x++) {
							int rgb1 = mBaseImage.getRGB(x, y);
							int rgb2 = mCurrentImage.getRGB(x, y);
							int r1 = (rgb1 >> 16) & 0xff;
							int g1 = (rgb1 >>  8) & 0xff;
							int b1 = (rgb1      ) & 0xff;
							int r2 = (rgb2 >> 16) & 0xff;
							int g2 = (rgb2 >>  8) & 0xff;
							int b2 = (rgb2      ) & 0xff;
							diff += Math.abs(r1 - r2);
							diff += Math.abs(g1 - g2);
							diff += Math.abs(b1 - b2);
						}
					}
					double n = width1 * height1 * 3;
					double p = diff / n / 255.0;

					return p*100.0;
				}
			}
		}catch(Exception e){

			return 100;
		}
		return 100;

	}
}
