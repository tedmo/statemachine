package com.tedmo.statemachine;

public interface Action<S, D, E> {
	
	public void doAction(StateMachine<S, D> ctx, E event);
	
}
