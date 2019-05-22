package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;

@Builder
public class StateModel<S, D> {
	
	private S id;
	
	private Map<Class<?>, List<Transition<S, D>>> transitions;
	private Map<Class<?>, Action<S, D, ?>> onEventActions;
	private Map<Class<?>, Action<S, D, ?>> onEnterActions;
	private Map<Class<?>, Action<S, D, ?>> onExitActions;
	
	public S getId() {
		return id;
	}
	
	public <E> Action<S, D, E> getOnEventAction(E event) {
		return getAction(event, onEventActions);
	}
	
	public <E> Action<S, D, E> getOnExitAction(E event) {
		return getAction(event, onExitActions);
	}
	
	public <E> Action<S, D, E> getOnEnterAction(E event) {
		return getAction(event, onEnterActions);
	}
	
	public <E> Optional<Transition<S, D>> getTransition(E event, StateMachine<S, D> ctx) {
		return transitions.get(event.getClass()).stream()
				.filter(transition -> 
						transition.getCondition()
						.map(condition -> condition.conditionMet(ctx))
						.orElse(true))
				.findFirst();
	}
	
	private <E> Action<S, D, E> getAction(E event, Map<Class<?>, Action<S, D, ?>> actions) {
		return (Action<S, D, E>) actions.get(event.getClass());
	}
	
}
