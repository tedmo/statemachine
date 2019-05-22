package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateMachineModel<S, D> {
	
	private Map<S, StateModel<S, D>> states = new HashMap<>();
	private S initialState;
	
	public StateMachineModel(Map<S, StateModel<S, D>> states, S initialState) {
		this.states = states;
		this.initialState = initialState;
	}
	
	public StateModel<S, D> getState(S state) {
		return states.get(state);
	}
	
	public <E> Action<S, D, E> getOnEventAction(S state, E event) {
		return states.get(state).getOnEventAction(event);
	}
	
	public <E> Action<S, D, E> getOnExitAction(S state, E event) {
		return states.get(state).getOnExitAction(event);
	}
	
	public <E> Action<S, D, E> getOnEnterAction(S state, E event) {
		return states.get(state).getOnEnterAction(event);
	}
	
	public <E> Optional<Transition<S, D>> getTransition(S state, E event, StateMachine<S, D> ctx) {
		return states.get(state).getTransition(event, ctx);
	}
	
}
