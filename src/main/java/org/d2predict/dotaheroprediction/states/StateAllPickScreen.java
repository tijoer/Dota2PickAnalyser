package org.d2predict.dotaheroprediction.states;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.d2predict.dotaheroprediction.opencv.OpenCvImageDisplay;
import org.d2predict.dotaheroprediction.opencv.Template;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;

public class StateAllPickScreen implements GameState {

    private static final Logger LOG = Logger.getLogger(StateAllPickScreen.class.getSimpleName());
    @Inject
    StateFinalState stateFinalState;
    @Inject
    OpenCvImageDisplay openCvImageDisplay;
    private ArrayList<Template> allTemplatesColored = new ArrayList<>();
    private ArrayList<Template> allTemplatesBlackAndWhite = new ArrayList<>();

    @Override
    public void handle(GameStateContext gameStateContext) {
	LOG.info("Executing state logic: AllPick");

	//TODO durch richtigen Screenshot ersetzen
	opencv_core.IplImage source = opencv_highgui.cvLoadImage(
		"templates/gui/allPickScreen.png",
		CV_LOAD_IMAGE_COLOR);

	for (Template template : allTemplatesBlackAndWhite) {
	    opencv_core.IplImage templateImage = template.getImage();
	    opencv_core.CvPoint templatePosition = template.matchTemplate(source);
	    if (templatePosition != null) {
		template.drawTemplateLocation(source, templatePosition);
	    }
	}

	openCvImageDisplay.showImage(source, "Matching Test", source.width(), source.height());
	gameStateContext.setCurrentState(stateFinalState);
    }

    @PostConstruct
    public void init() {
	loadAllTemplates();
    }

    private void loadAllTemplates() {
	File f = new File(Template.HEROES_FOLDER);
	ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
	ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));

	for (String filename : names) {
	    Template template;
	    template = newTemplateToList(filename, allTemplatesColored);
	    
	    template = newTemplateToList(filename, allTemplatesBlackAndWhite);
	    template.convertToGrayscale();
	}
    }

    @Override
    public String getName() {
	return StateAllPickScreen.class.getSimpleName();
    }

    @Override
    public int getTickSpeed() {
	return 1000;
    }

    @Override
    public StateType getStateType() {
	return StateType.normalState;
    }

    private Template newTemplateToList(String filename, ArrayList<Template> arrayList) {
	Template template = new Template();
	template.load(Template.HEROES_FOLDER + filename, CV_LOAD_IMAGE_COLOR);
	template.resize(99, 54);
	arrayList.add(template);
	
	return template;
    }
}
