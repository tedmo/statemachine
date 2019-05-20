package com.tedmo.statemachine;

public class TypeSafeDoActionBuilder <S, D, E>{
	
	private TransitionalActionBuilder<S, D> transitionalActionBuilder;
	
	public TypeSafeDoActionBuilder(TransitionalActionBuilder<S, D> transitionActionBuilder) {
		this.transitionalActionBuilder = transitionActionBuilder;
	}
	
	public ActionMapBuilder<S, D> doAction(Action<S, D, E> action) {
		return this.transitionalActionBuilder.doAction(action);
	}
	
}
