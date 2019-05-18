package com.tedmo.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class State<S, D> {
	
	private S id;
	
	private Map<Class<?>, List<Transition<S, D>>> transitions = new HashMap<>();
	private Map<Class<?>, Action<S, D, ?>> onEventActions = new HashMap<>();
	private Map<Class<?>, Action<S, D, ?>> onEnterActions = new HashMap<>();
	private Map<Class<?>, Action<S, D, ?>> onExitActions = new HashMap<>();
	
	public State(S id) {
		this.id = id;
	}
	
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
	
	public <E> void putOnEventAction(Class<E> eventClass, Action<S, D, E> action) {
		onEventActions.put(eventClass, action);
	}
	
	public <E> void putOnEnterAction(Class<E> eventClass, Action<S, D, E> action) {
		onEnterActions.put(eventClass, action);
	}
	
	public <E> void putOnExitAction(Class<E> eventClass, Action<S, D, E> action) {
		onExitActions.put(eventClass, action);
	}
	
	public void putTransition(Class<?> eventClass, Transition<S, D> transition) {
		if(transitions.get(eventClass) == null) {
			transitions.put(eventClass, new ArrayList<>());
		}
		transitions.get(eventClass).add(transition);
	}
	
	private <E> Action<S, D, E> getAction(E event, Map<Class<?>, Action<S, D, ?>> actions) {
		return (Action<S, D, E>) actions.get(event.getClass());
	}
	
	public <E> Optional<Transition<S, D>> getTransition(E event, StateMachineCtx<S, D> ctx) {
		return transitions.get(event.getClass()).stream()
				.filter(transition -> 
						transition.getCondition()
						.map(condition -> condition.conditionMet(ctx))
						.orElse(true))
				.findFirst();
	}
	
}
