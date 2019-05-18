package com.tedmo.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StateContext<S> {
	
	private S id;
	
	private Map<Class<?>, List<Transition<S>>> transitions = new HashMap<>();
	private Map<Class<?>, Action<S, ?>> onEventActions = new HashMap<>();
	private Map<Class<?>, Action<S, ?>> onEnterActions = new HashMap<>();
	private Map<Class<?>, Action<S, ?>> onExitActions = new HashMap<>();
	
	public StateContext(S id) {
		this.id = id;
	}
	
	public S getId() {
		return id;
	}
	
	public <E> Action<S, E> getOnEventAction(E event) {
		return getAction(event, onEventActions);
	}
	
	public <E> Action<S, E> getOnExitAction(E event) {
		return getAction(event, onExitActions);
	}
	
	public <E> Action<S, E> getOnEnterAction(E event) {
		return getAction(event, onEnterActions);
	}
	
	public <E> void putOnEventAction(Class<E> eventClass, Action<S, E> action) {
		onEventActions.put(eventClass, action);
	}
	
	public <E> void putOnEnterAction(Class<E> eventClass, Action<S, E> action) {
		onEnterActions.put(eventClass, action);
	}
	
	public <E> void putOnExitAction(Class<E> eventClass, Action<S, E> action) {
		onExitActions.put(eventClass, action);
	}
	
	public void putTransition(Class<?> eventClass, Transition<S> transition) {
		if(transitions.get(eventClass) == null) {
			transitions.put(eventClass, new ArrayList<>());
		}
		transitions.get(eventClass).add(transition);
	}
	
	private <E> Action<S, E> getAction(E event, Map<Class<?>, Action<S, ?>> actions) {
		return (Action<S, E>) actions.get(event.getClass());
	}
	
	public <E> Optional<Transition<S>> getTransition(E event, StateMachineCtx<S> ctx) {
		return transitions.get(event.getClass()).stream()
				.filter(transition -> 
						transition.getCondition()
						.map(condition -> condition.conditionMet(ctx))
						.orElse(true))
				.findFirst();
	}
	
}
