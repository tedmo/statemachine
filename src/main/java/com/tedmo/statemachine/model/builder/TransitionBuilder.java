package com.tedmo.statemachine.model.builder;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.tedmo.statemachine.model.TransitionModel;

import lombok.Getter;

@Getter
public class TransitionBuilder<S extends Enum<S>, D> {
	
	private StateMachineModelBuilder<S, D> stateMachineModelBuilder;
	
	private S from;
	private S to;
	private Class<?> on;
	private Predicate<D> when;
	
	private Map<S, List<TransitionModel<S, D>>> transitions;
	
	public TransitionBuilder(StateMachineModelBuilder<S, D> stateMachineModelBuilder) {
		this.stateMachineModelBuilder = stateMachineModelBuilder;
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
	
	public StateMachineModelBuilder<S, D> when(Predicate<D> condition) {
		this.when = condition;
		return this.stateMachineModelBuilder.addTransition(this);
	}
	
	public StateMachineModelBuilder<S, D> withoutCondition() {
		this.when = null;
		return this.stateMachineModelBuilder.addTransition(this);
	}
	
}
