package org.d2predict.dotaheroprediction.states;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class GameStateContextImpl implements GameStateContext {

    private static final Logger LOG = Logger.getLogger(GameStateContextImpl.class.getSimpleName());
    private GameState currentGameState;
    @Inject
    private StateSearchingForGame stateSearchingForGame;
    long lastTickTime = System.currentTimeMillis();
    long currentTime;

    @PostConstruct
    public void init() {
	if(stateSearchingForGame.getStateType() != GameState.StateType.startState) {
	    LOG.warning("Starting state is not declared as starting stage.");
	}
	setCurrentState(stateSearchingForGame);
    }

    @Override
    public void handle() {
	while (currentGameState.getStateType() != GameState.StateType.finalState) {
	    waitForNextTick();
	    currentGameState.handle(this);
	}
	LOG.log(Level.INFO, "Final State reached. Shutting down.");
    }

    @Override
    public void setCurrentState(GameState state) {
	LOG.log(Level.INFO, "Changing state to: {0}", state.getName());
	this.currentGameState = state;
    }

    private void waitForNextTick() {
	currentTime = System.currentTimeMillis();

	if (currentTime - lastTickTime < currentGameState.getTickSpeed()) {
	    try {
		Thread.sleep(100);
	    } catch (InterruptedException ex) {
		LOG.log(Level.SEVERE, null, ex);
	    }
	}
	lastTickTime = currentTime;
    }
}
