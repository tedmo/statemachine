package com.tedmo.statemachine;

public abstract class TransitionalActionBuilder<S, D> {
	
	protected ActionMapBuilder<S, D> actionMapBuilder;
	
	private S state;
	private Class<?> event;
	private Action<S, D, ?> action;

	public TransitionalActionBuilder(ActionMapBuilder<S,D> actionMapBuilder, S enteredState) {
		this.state = enteredState;
	}
	
	public <E> TypeSafeDoActionBuilder<S, D, E> on(Class<E> event) {
		this.event = event;
		return new TypeSafeDoActionBuilder<S, D, E>(this);
	}
	
	protected ActionMapBuilder<S, D> doAction(Action<S, D, ?> action) {
		this.action = action;
		return addToMap();
	}
	
	abstract protected ActionMapBuilder<S, D> addToMap();
	
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
