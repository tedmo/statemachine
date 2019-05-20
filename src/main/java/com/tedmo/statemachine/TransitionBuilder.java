package com.tedmo.statemachine;

import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class TransitionBuilder<S, D> {
	
	private TransitionMapBuilder<S, D> transitionMapBuilder;
	
	private S from;
	private S to;
	private Class<?> on;
	private Condition<D> when;
	
	private Map<S, List<Transition<S, D>>> transitions;
	
	public TransitionBuilder(TransitionMapBuilder<S, D> transitionMapBuilder) {
		this.transitionMapBuilder = transitionMapBuilder;
	}
	
	public TransitionBuilder<S, D> from(S state) {
		this.from = state;
		return this;
	}
	
	public TransitionBuilder<S, D> to(S state) {
		this.to = state;
		return this;
	}
	
	public TransitionBuilder<S, D> on(Class<?> event) {
		this.on = event;
		return this;
	}
	
	public TransitionBuilder<S, D> when(Condition<D> condition) {
		this.when = condition;
		return this;
	}
	
	public TransitionMapBuilder<S, D> add() {
		return this.transitionMapBuilder.addTransition(this);
	}
	
}
