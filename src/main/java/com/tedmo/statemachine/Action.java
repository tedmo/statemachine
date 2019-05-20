package com.tedmo.statemachine;

public interface Action<S, D, E> {

	// TODO Probably want to change this to no allow access to the state machine.
	//      Instead we might want a state machien ctx that can give you access
	//      to current state and state model ID (if state model ID is implemented)
	public void doAction(StateMachine<S, D> ctx, E event);
	
}
