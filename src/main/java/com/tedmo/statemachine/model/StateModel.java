package com.tedmo.statemachine.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;

@Builder
public final class StateModel<S, C> {
	
	private Map<Class<?>, List<TransitionModel<S, C>>> transitions;
	private Map<Class<?>, Action<S, C, ?>> onEventActions;
	private Map<Class<?>, Action<S, C, ?>> onEnterActions;
	private Map<Class<?>, Action<S, C, ?>> onExitActions;
	
	public <E> Optional<Action<S, C, E>> getOnEventAction(E event) {
		return getAction(event, onEventActions);
	}
	
	public <E> Optional<Action<S, C, E>> getOnExitAction(E event) {
		return getAction(event, onExitActions);
	}
	
	public <E> Optional<Action<S, C, E>> getOnEnterAction(E event) {
		return getAction(event, onEnterActions);
	}
	
	public <E> Optional<TransitionModel<S, C>> getTransition(E event, C ctx) {
		
		List<TransitionModel<S, C>> eventTransitions = transitions.get(event.getClass());
		
		if(eventTransitions == null) {
			return Optional.empty();
		}
		
		return transitions.get(event.getClass()).stream()
				.filter(transition -> 
						transition.getCondition()
						.map(condition -> condition.conditionMet(ctx))
						.orElse(true))
				.findFirst();
	}
	
	@SuppressWarnings("unchecked")
	private <E> Optional<Action<S, C, E>> getAction(E event, Map<Class<?>, Action<S, C, ?>> actions) {
		if (actions == null || actions.get(event.getClass()) == null) {
			return Optional.empty();
		} else {
			return Optional.of((Action<S, C, E>) actions.get(event.getClass()));
		}
	}
	
}
