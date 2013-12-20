package org.d2predict.dotaheroprediction;

import org.d2predict.dotaheroprediction.opencv.OpenCvImageDisplay;
import org.d2predict.dotaheroprediction.opencv.Template;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;

import com.googlecode.javacv.cpp.opencv_highgui;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.d2predict.dotaheroprediction.states.GameStateContext;
import org.d2predict.dotaheroprediction.states.GameStateContextImpl;
import org.jboss.weld.environment.se.Weld;

import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@ApplicationScoped
public class Main {

    @Inject private GameStateContext gameStateContext;
    @Inject private OpenCvImageDisplay openCvImageDisplay;

    public static void main(String[] args) {
	Weld weld = new Weld();
	WeldContainer container = weld.initialize();
//	Main main = container.instance().select(Main.class).get();
//	if (main.gameStateContext == null) {
//	    System.out.println("Error!!!!!!");
//	} else {
//	    System.out.println("success");
//	}
    }

    public void main(@Observes ContainerInitialized event) {
    }
    
    @PostConstruct
    public void init() {
	gameStateContext.handle();
	if(true) {
	    return;
	}
	
//	loadAllTemplates();


    } 

//    private void analyseScreen() {
//        boolean clipboardBlockedOnlyOnce = false;
//        Screenshot screenshot = new Screenshot();
//
//        for (int i = 0;; i++) {
//            try {
//                IplImage iplImage = IplImage.createFrom(screenshot.takeScreenshot());
//
//                showImage(iplImage, "RawImage", 512, 512);
//
//                screenshot.saveScreenshot("screenshot" + i + ".jpg");
//                Thread.sleep(4000);
//            } catch (Exception ex) {
//                if (ex.getMessage().equals("cannot open system clipboard")) {
//                    if (clipboardBlockedOnlyOnce == true) {
//                        Logger.getLogger(Main.class.getName()).log(Level.WARNING,
//                                "Just trying again...", ex);
//                    }
//
//                    clipboardBlockedOnlyOnce = true;
//                }
//            }
//        }
//    }
    public GameStateContext getGameStateContext() {
	return gameStateContext;
    }
}
