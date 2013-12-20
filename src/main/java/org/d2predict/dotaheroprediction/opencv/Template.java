package org.d2predict.dotaheroprediction.opencv;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;

public class Template {

    private static final Logger LOG = Logger.getLogger(Template.class.getSimpleName());
    public static String HEROES_FOLDER = "templates/heroes/";
    public static String GUI_FOLDER = "templates/gui/";
    private opencv_core.IplImage image;
    private String name;

    public void load(String filename, int loadType) {
	image = opencv_highgui.cvLoadImage(filename, loadType); // CV_LOAD_IMAGE_COLOR);

	this.name = filename;
	this.name = this.name.replaceAll(".png", "");
	this.name = this.name.replaceAll(".PNG", "");
	this.name = this.name.replaceAll(".jpg", "");
	this.name = this.name.replaceAll(".JPG", "");
	this.name = this.name.replace(HEROES_FOLDER, "");
    }

    public opencv_core.CvPoint matchTemplate(IplImage source) {
	IplImage img32f;
	IplImage result = cvCreateImage(
		opencv_core.cvSize(source.width() - this.image.width() + 1,
		source.height() - this.image.height() + 1),
		opencv_core.IPL_DEPTH_32F, 1);
	opencv_core.cvZero(result);
	
	cvMatchTemplate(source, this.image, result, CV_TM_CCORR_NORMED);

	opencv_core.CvPoint maxLoc = new opencv_core.CvPoint();
	opencv_core.CvPoint minLoc = new opencv_core.CvPoint();
	double[] minVal = new double[2];
	double[] maxVal = new double[2];

	cvMinMaxLoc(result, minVal, maxVal, minLoc, maxLoc, null);
	maxLoc = maxVal[0] > 0.9f ? maxLoc : null;

	cvReleaseImage(result);
	return maxLoc;
    }

    IplImage convertToFloat32(IplImage img) {
	IplImage img32f = cvCreateImage(cvGetSize(img), IPL_DEPTH_32F, img.nChannels());

	for (int i = 0; i < img.height(); i++) {
	    for (int j = 0; j < img.width(); j++) {
		cvSet2D(img32f, i, j, cvGet2D(img, i, j));
	    }
	}
	return img32f;
    }

    public void drawTemplateLocation(opencv_core.IplImage source,
	    opencv_core.CvPoint templatePosition) {
	opencv_core.CvFont font = new opencv_core.CvFont(CV_FONT_HERSHEY_PLAIN, 1, 1);

	opencv_core.CvPoint point = new opencv_core.CvPoint();
	point.x(templatePosition.x() + this.image.width() + 4);
	point.y(templatePosition.y() + this.image.height() + 4);
	templatePosition.x(templatePosition.x() - 4);
	templatePosition.y(templatePosition.y() - 4);
	cvRectangle(source, templatePosition, point, opencv_core.CvScalar.GREEN, 2, 8, 0);

	point.x(templatePosition.x() + 3);
	point.y(point.y() - 3);

	cvPutText(source, this.name, point, font, opencv_core.CvScalar.GREEN);
    }

    public opencv_core.IplImage getImage() {
	return image;
    }

    public void setImage(IplImage image) {
	this.image = image;
    }

    public void resize(int width, int height) {
	IplImage destination = opencv_core.cvCreateImage(cvSize(width, height),
		image.depth(), image.nChannels());

	cvResize(this.image, destination);

	cvReleaseImage(this.image);
	this.image = destination;
    }

    public void convertToGrayscale() {
	IplImage image = this.getImage();
	IplImage gray = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);

	cvCvtColor(image, gray, CV_BGR2GRAY);
	cvCvtColor(gray, image, CV_GRAY2BGR); //call by reference

	cvReleaseImage(gray);
    }

    public void freeNativeMemory() {
	cvReleaseImage(this.image);
	this.image = null;
    }

    @Override
    protected void finalize() throws Throwable {
	if (this.image != null) {
	    LOG.severe("Memory from OpenCV was not cleaned up before finalize. Possible memory leak.");
	}

	super.finalize();
    }
}
