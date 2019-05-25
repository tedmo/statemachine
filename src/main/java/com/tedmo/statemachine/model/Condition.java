package com.tedmo.statemachine.model;

public interface Condition<C> {
	boolean conditionMet(C appCtx);
}
