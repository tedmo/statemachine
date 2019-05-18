package com.tedmo.statemachine;

public interface Condition<D> {

	public boolean conditionMet(StateMachineCtx<?, D> ctx);
}
