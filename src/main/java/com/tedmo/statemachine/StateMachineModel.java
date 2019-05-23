package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class StateMachineModel<S, C> {
	
	private Map<S, StateModel<S, C>> states = new HashMap<>();
	private S initialState;
	
	public StateMachineModel(Map<S, StateModel<S, C>> states, S initialState) {
		this.states = states;
		this.initialState = initialState;
	}
	
	public S getInitialState() {
		return initialState;
	}
	
	public StateModel<S, C> getState(S state) {
		return states.get(state);
	}
	
	public <E> Action<S, C, E> getOnEventAction(S state, E event) {
		return states.get(state).getOnEventAction(event);
	}
	
	public <E> Action<S, C, E> getOnExitAction(S state, E event) {
		return states.get(state).getOnExitAction(event);
	}
	
	public <E> Action<S, C, E> getOnEnterAction(S state, E event) {
		return states.get(state).getOnEnterAction(event);
	}
	
	public <E> Optional<Transition<S, C>> getTransition(S state, E event, StateMachine<S, C> ctx) {
		return states.get(state).getTransition(event, ctx);
	}
	
}
