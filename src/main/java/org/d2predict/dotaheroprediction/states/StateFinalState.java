package org.d2predict.dotaheroprediction.states;

public class StateFinalState implements GameState {

    @Override
    public void handle(GameStateContext gameStateContext) {
	//nothing to do
    }

    @Override
    public String getName() {
	return StateFinalState.class.getSimpleName();	
    }

    @Override
    public int getTickSpeed() {
	return 0;
    }

    @Override
    public StateType getStateType() {
	return StateType.finalState;
    }
    
}
