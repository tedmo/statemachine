package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateMachineModel<S, D> {
	
	private Map<S, StateModel<S, D>> states = new HashMap<>();
	
	public StateMachineModel(Map<S, StateModel<S, D>> states) {
		this.states = states;
	}
	
	public StateModel<S, D> getState(S state) {
		return states.get(state);
	}
	
	public <E> Action<S, D, E> getOnEventAction(S state, E event) {
		return states.get(state).getOnEventAction(event);
	}
	
	public <E> void putOnEventAction(S state, Class<E> eventClass, Action<S, D, E> action) {
		states.get(state).putOnEventAction(eventClass, action);
	}
	
	public <E> Action<S, D, E> getOnExitAction(S state, E event) {
		return states.get(state).getOnExitAction(event);
	}
	
	public <E> Action<S, D, E> getOnEnterAction(S state, E event) {
		return states.get(state).getOnEnterAction(event);
	}
	
	public <E> void putOnEnterAction(S state, Class<E> eventClass, Action<S, D, E> action) {
		states.get(state).putOnEnterAction(eventClass, action);
	}
	
	public <E> void putOnExitAction(S state, Class<E> eventClass, Action<S, D, E> action) {
		states.get(state).putOnExitAction(eventClass, action);
	}
	
	public void putTransition(S state, Class<?> eventClass, Transition<S, D> transition) {
		states.get(state).putTransition(eventClass, transition);
	}
	
	public <E> Optional<Transition<S, D>> getTransition(S state, E event, StateMachine<S, D> ctx) {
		return states.get(state).getTransition(event, ctx);
	}
	
}
