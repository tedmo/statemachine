package com.tedmo.statemachine;

public interface Action<S, E> {
	
	public void doAction(StateMachineCtx<S> ctx, E event);
	
}
