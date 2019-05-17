package com.tedmo.statemachine;

public interface Condition<S, E extends Enum<E>> {

	public boolean conditionMet(StateMachineCtx<S, E> ctx);
}
