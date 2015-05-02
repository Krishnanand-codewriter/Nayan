package sampleTests;


import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_32F;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvMinMaxLoc;
import static org.bytedeco.javacpp.opencv_core.cvRectangle;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_core.cvSize;
import static org.bytedeco.javacpp.opencv_core.cvZero;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_highgui.cvShowImage;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_imgproc.CV_TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvMatchTemplate;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;

/**
 * Example of template javacv (opencv) template matching using the last java build
 *
 * We need 4 default parameters like this
 * "C:\Users\Waldema\Desktop\bg.jpg" "C:\Users\Waldema\Desktop\logosiemens.jpg" "C:\Users\Waldema\Desktop\imageToFind.jpg" 100 200
 *
 * @author Waldemar Neto
 */
public class TemplateMatching {

    public static void main(String[] args) throws Exception {
    	BufferedImage image = ImageIO.read(new File("/home/krishnanandb/Pictures/MLP1/MLP1.png"));
    	
        int width = image.getWidth();
        int height = image.getHeight();;

        IplImage src = cvLoadImage(
        		"/home/krishnanandb/Pictures/MLP1/MLP1.png", 0);
        IplImage tmp = cvLoadImage(
        		"/home/krishnanandb/Pictures/MLP1/MLP2.png", 0);

        IplImage result = cvCreateImage(
                cvSize(src.width() - tmp.width() + 1,
                        src.height() - tmp.height() + 1), IPL_DEPTH_32F, src.nChannels());

        cvZero(result);

        // Match Template Function from OpenCV
        cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);

        // double[] min_val = new double[2];
        // double[] max_val = new double[2];
        DoublePointer min_val = new DoublePointer();
        DoublePointer max_val = new DoublePointer();

        CvPoint minLoc = new CvPoint();
        CvPoint maxLoc = new CvPoint();

        cvMinMaxLoc(result, min_val, max_val, minLoc, maxLoc, null);

        // Get the Max or Min Correlation Value
        // System.out.println(Arrays.toString(min_val));
        // System.out.println(Arrays.toString(max_val));

        CvPoint point = new CvPoint();
        point.x(maxLoc.x() + tmp.width());
        point.y(maxLoc.y() + tmp.height());
        // cvMinMaxLoc(src, min_val, max_val,0,0,result);

        cvRectangle(src, maxLoc, point, CvScalar.RED, 2, 8, 0); // Draw a
                                                                // Rectangle for
                                                                // Matched
                                                                // Region
        CvRect rect = new CvRect();
        rect.x(maxLoc.x());
        rect.y(maxLoc.y());
        rect.width(tmp.width() + width);
        rect.height(tmp.width() + height);
        cvSetImageROI(src, rect);
        IplImage imageNew = cvCreateImage(cvGetSize(src), src.depth(),
                src.nChannels());
        cvCopy(src, imageNew);
        cvSaveImage(args[2], imageNew);

        cvShowImage("Lena Image", src);
        cvWaitKey(0);
        cvReleaseImage(src);
        cvReleaseImage(tmp);
        cvReleaseImage(result);
    }
}