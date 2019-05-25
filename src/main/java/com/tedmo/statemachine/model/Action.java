package com.tedmo.statemachine.model;

public interface Action<S, C, E> {

	// TODO Probably want to change this to no allow access to the state machine.
	//      Instead we might want a state machien ctx that can give you access
	//      to current state and state model ID (if state model ID is implemented)
	void doAction(S currentState, C appCtx, E event);
	
}
