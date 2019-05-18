package com.tedmo.statemachine;

public interface Condition {

	public boolean conditionMet(StateMachineCtx<?> ctx);
}
