package com.tedmo.statemachine.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class StateMachineModel<S extends Enum<S>, C> {
	
	private Map<S, StateModel<S, C>> states = new HashMap<>();
	private S initialState;
	
	public StateMachineModel(Map<S, StateModel<S, C>> states, S initialState) {
		this.states = states;
		this.initialState = initialState;
	}
	
	public S getInitialState() {
		return initialState;
	}
	
	public <E> Optional<Action<S, C, E>> getOnEventAction(S state, E event) {
		return states.get(state).getOnEventAction(event);
	}
	
	public <E> Optional<Action<S, C, E>> getOnExitAction(S state, E event) {
		return states.get(state).getOnExitAction(event);
	}
	
	public <E> Optional<Action<S, C, E>> getOnEnterAction(S state, E event) {
		return states.get(state).getOnEnterAction(event);
	}
	
	public <E> Optional<TransitionModel<S, C>> getTransition(S state, E event, C appCtx) {
		return states.get(state).getTransition(event, appCtx);
	}
	
}
