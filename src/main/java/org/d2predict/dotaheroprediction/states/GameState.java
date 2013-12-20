package org.d2predict.dotaheroprediction.states;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface GameState {
    public void handle(GameStateContext gameStateContext);

    public String getName();
    
    public int getTickSpeed();
    
    enum StateType {
	startState,
	normalState,
	finalState
    }
    
    public StateType getStateType();
}
