package com.tedmo.statemachine;

public interface Condition<C> {
	boolean conditionMet(C appCtx);
}
