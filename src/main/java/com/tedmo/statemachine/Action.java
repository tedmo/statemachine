package com.tedmo.statemachine;

public interface Action<S, E extends Enum<E>> {

	public void doAction(StateMachineCtx<S, E> ctx, Event<E> event);
	
}
