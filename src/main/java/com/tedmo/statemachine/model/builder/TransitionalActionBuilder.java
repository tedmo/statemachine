package com.tedmo.statemachine.model.builder;

import com.tedmo.statemachine.model.Action;

public abstract class TransitionalActionBuilder<S, D> {
	protected ActionBuilder<S, D> actionBuilder;
	
	private S state;
	private Class<?> event;
	private Action<S, D, ?> action;

	public TransitionalActionBuilder(ActionBuilder<S, D> actionBuilder, S enteredState) {
		this.actionBuilder = actionBuilder;
		this.state = enteredState;
	}
	
	public <E> TypeSafeDoActionBuilder<S, D, E> on(Class<E> event) {
		this.event = event;
		return new TypeSafeDoActionBuilder<S, D, E>(this);
	}
	
	protected StateMachineModelBuilder<S, D> doAction(Action<S, D, ?> action) {
		this.action = action;
		return addToMap();
	}
	
	abstract protected StateMachineModelBuilder<S, D> addToMap();
	
	public S getState() {
		return state;
	}
	
	public Class<?> getEvent() {
		return event;
	}
	
	public Action<S, D, ?> getAction() {
		return action;
	}
}
