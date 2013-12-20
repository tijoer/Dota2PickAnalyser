package org.d2predict.dotaheroprediction.states;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_COLOR;
import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.d2predict.dotaheroprediction.ScreenshotGrabber;
import org.d2predict.dotaheroprediction.opencv.OpenCvImageDisplay;
import org.d2predict.dotaheroprediction.opencv.Template;

public class StateSearchingForGame implements GameState {

    private static final Logger LOG = Logger.getLogger(StateAllPickScreen.class.getSimpleName());
    
    Template template;
    
    @Inject
    OpenCvImageDisplay openCvImageDisplay;
    @Inject
    StateAllPickScreen stateAllPickScreen;
//    @Inject
//    Screenshot screenshot;

    @Override
    public void handle(GameStateContext gameStateContext) {
//	try {
//	    screenshot.takeScreenshot();
//	} catch (Exception ex) {
//	    LOG.log(Level.SEVERE, null, ex);
//	}
	
	opencv_core.IplImage screenshot = opencv_highgui.cvLoadImage(
		"templates/gui/allPickScreen.png",
		CV_LOAD_IMAGE_COLOR);

	opencv_core.CvPoint templatePosition = template.matchTemplate(screenshot);
	
	if (templatePosition != null) {
	    gameStateContext.setCurrentState(stateAllPickScreen);
	    template.freeNativeMemory();
	}
    }
    
    @PostConstruct
    public void init() {
	this.template = new Template();
	this.template.load(Template.GUI_FOLDER + "allPickTop.png", CV_LOAD_IMAGE_COLOR);
    }
    
    @Override
    public String getName() {
	return StateSearchingForGame.class.getSimpleName();
    }

    @Override
    public int getTickSpeed() {
	return 1000;
    }

    @Override
    public StateType getStateType() {
	return StateType.startState;
    }
}
