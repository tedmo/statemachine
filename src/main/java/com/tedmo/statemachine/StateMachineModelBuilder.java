package com.tedmo.statemachine;

import java.util.Set;

public class StateMachineModelBuilder<S, D> {
	
	private Set<S> states;
	private S initialState;
	private TransitionMapBuilder<S, D> transitionMapBuilder;
	
	private StateMachineModelBuilder<S, D> states(Set<S> states) {
		this.states = states;
		return this;
	}
	
	private StateMachineModelBuilder<S, D> initialState(S initialState) {
		this.initialState = initialState;
		return this;
	}
	
	private StateMachineModelBuilder() {
		this.transitionMapBuilder = new TransitionMapBuilder<>(this);
	}
	
	public TransitionMapBuilder<S, D> transitions() {
		return transitionMapBuilder;
	}
	
}
