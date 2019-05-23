package com.tedmo.statemachine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;

@Builder
public final class StateModel<S, C> {
	
	private Map<Class<?>, List<Transition<S, C>>> transitions;
	private Map<Class<?>, Action<S, C, ?>> onEventActions;
	private Map<Class<?>, Action<S, C, ?>> onEnterActions;
	private Map<Class<?>, Action<S, C, ?>> onExitActions;
	
	public <E> Action<S, C, E> getOnEventAction(E event) {
		return getAction(event, onEventActions);
	}
	
	public <E> Action<S, C, E> getOnExitAction(E event) {
		return getAction(event, onExitActions);
	}
	
	public <E> Action<S, C, E> getOnEnterAction(E event) {
		return getAction(event, onEnterActions);
	}
	
	public <E> Optional<Transition<S, C>> getTransition(E event, C ctx) {
		return transitions.get(event.getClass()).stream()
				.filter(transition -> 
						transition.getCondition()
						.map(condition -> condition.conditionMet(ctx))
						.orElse(true))
				.findFirst();
	}
	
	@SuppressWarnings("unchecked")
	private <E> Action<S, C, E> getAction(E event, Map<Class<?>, Action<S, C, ?>> actions) {
		return (Action<S, C, E>) actions.get(event.getClass());
	}
	
}
