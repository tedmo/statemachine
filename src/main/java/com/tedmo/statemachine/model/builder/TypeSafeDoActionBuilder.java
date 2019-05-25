package com.tedmo.statemachine.model.builder;

import com.tedmo.statemachine.model.Action;

public class TypeSafeDoActionBuilder <S, D, E>{
	
	private TransitionalActionBuilder<S, D> transitionalActionBuilder;
	
	public TypeSafeDoActionBuilder(TransitionalActionBuilder<S, D> transitionActionBuilder) {
		this.transitionalActionBuilder = transitionActionBuilder;
	}
	
	public StateMachineModelBuilder<S, D> doAction(Action<S, D, E> action) {
		return this.transitionalActionBuilder.doAction(action);
	}
	
}
