package com.tedmo.statemachine;

public interface Condition<C> {
	boolean conditionMet(StateMachine<?, C> ctx);
}
