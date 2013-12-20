package org.d2predict.dotaheroprediction.states;

public interface GameStateContext {
    
    public void handle( );
    public void setCurrentState(GameState state);
}
