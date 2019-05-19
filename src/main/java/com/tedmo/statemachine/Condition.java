package com.tedmo.statemachine;

public interface Condition<D> {

	public boolean conditionMet(StateMachine<?, D> ctx);
}
