package com.tedmo.statemachine;

public interface Action<S, D, E> {
	
	public void doAction(StateMachineCtx<S, D> ctx, E event);
	
}
