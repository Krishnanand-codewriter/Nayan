package cognizant.nayan.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import cognizant.nayan.commons.ImageType;
import cognizant.nayan.io.ImageReader;

public class RegionHighlighter {
	private String[] mRegionCoords;
	private BufferedImage mBaseImage;
	private File mImageFile;
	private ImageReader mReader;
	private String mScenario;
	public RegionHighlighter(File mImageFile , String[] mRegionCoords, String mScenario) {
		this.mRegionCoords = mRegionCoords;
		this.mImageFile = mImageFile;
		this.mScenario = mScenario;
	}
	
	public BufferedImage drawRegionOfInterest(){
		mReader = new ImageReader(mImageFile);
		mBaseImage = mReader.readImage(ImageType.BASE_IMAGE,mScenario);
		Path2D.Float regionOfInterest = new Path2D.Float();
		boolean isFirst = true;
		double firstX=0, firstY=0;
		for(String coord :mRegionCoords){
			String[] xy = coord.split(",");
			double x = Double.parseDouble(xy[0]);
			double y = Double.parseDouble(xy[1]);
			if(isFirst){
			regionOfInterest.moveTo(x, y);
			firstX =x;
			firstY =y;
			isFirst = false;
			}else{regionOfInterest.lineTo(x, y);}
			regionOfInterest.moveTo(firstX, firstY);
			
			Path2D.Float pathForWholeImage = new Path2D.Float();
			pathForWholeImage.moveTo(0, 0);
			pathForWholeImage.lineTo(mBaseImage.getWidth(), 0);
			pathForWholeImage.lineTo(mBaseImage.getWidth(), mBaseImage.getHeight());
			pathForWholeImage.lineTo(0, mBaseImage.getHeight());
			pathForWholeImage.lineTo(0, 0);
			
			Area wholeImage = new Area(pathForWholeImage);
			wholeImage.subtract(new Area(regionOfInterest));
			Graphics2D g2D = (Graphics2D)mBaseImage.getGraphics();
			g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
			/*g2D.setColor(new Color(255,255,255,100));
			g2D.fill(wholeImage);*/
			g2D.setStroke(new BasicStroke(0.1f));
			g2D.setColor(new Color(1f,0f,0f,1f));
			g2D.draw(regionOfInterest);		
		}
		return mBaseImage;
		
	}
}
