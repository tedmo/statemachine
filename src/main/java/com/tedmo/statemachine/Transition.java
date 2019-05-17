package com.tedmo.statemachine;

public class Transition<S, E extends Enum<E>> {
	
	S toState;
	Condition<S, E> condition;
	
	public S getToState() {
		return toState;
	}
	public void setToState(S toState) {
		this.toState = toState;
	}
	public Condition<S, E> getCondition() {
		return condition;
	}
	public void setCondition(Condition<S, E> condition) {
		this.condition = condition;
	}
	
}
