package org.d2predict.dotaheroprediction.opencv;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;

public class OpenCvImageDisplay {

    private CanvasFrame canvasFrame;

    public void showImage(opencv_core.IplImage image, String caption, int width, int height) {
	if (canvasFrame == null) {
	    canvasFrame = new CanvasFrame(caption, 1);   // gamma = 1
	    canvasFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	canvasFrame.setCanvasSize(width, height);
	canvasFrame.showImage(image);
    }

    public void showImage(opencv_core.IplImage image, String caption) {
	if (canvasFrame == null) {
	    canvasFrame = new CanvasFrame(caption, 1);   // gamma = 1
	    canvasFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	canvasFrame.setCanvasSize(image.width(), image.height());
	canvasFrame.showImage(image);
    }
}
