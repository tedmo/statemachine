package com.tedmo.statemachine;

import java.util.Map;

public class StateMachineCtx <S, E extends Enum<E>> {
	
	StateContext<S, E> currentState;
	Map<S, StateContext<S, E>> states;
	
	public void sendEvent(Event<E> event) {
		currentState.getOnEventAction(event).doAction(this, event);
		currentState.getTransition(event.getName(), this)
			.ifPresent(transition -> doTransition(transition, event));
	}
	
	private void doTransition(Transition<S, E> transition, Event<E> event) {
		currentState.getOnExitAction(event).doAction(this, event);
		currentState = states.get(transition.getToState());
		currentState.getOnEnterAction(event).doAction(this, event);
	}

	public StateContext<S, E> getCurrentStateContext() {
		return currentState;
	}

	public void setCurrentStateContext(StateContext<S, E> currentStateContext) {
		this.currentState = currentStateContext;
	}

	public Map<S, StateContext<S, E>> getStateContexts() {
		return states;
	}

	public void setStateContexts(Map<S, StateContext<S, E>> stateContexts) {
		this.states = stateContexts;
	}
	
}
