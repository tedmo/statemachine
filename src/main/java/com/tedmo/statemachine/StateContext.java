package com.tedmo.statemachine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StateContext<S, E extends Enum<E>> {

	S id;
	Map<E, List<Transition<S, E>>> transitions;
	Map<E, Action<S, E>> onEventActions;
	Map<E, Action<S, E>> onEnterActions;
	Map<E, Action<S, E>> onExitActions;
	
	public Action<S, E> getOnEventAction(Event<E> event) {
		return onEventActions.get(event.getName());
	}
	
	public Action<S, E> getOnExitAction(Event<E> event) {
		return onExitActions.get(event.getName());
	}
	
	public Action<S, E> getOnEnterAction(Event<E> event) {
		return onEnterActions.get(event.getName());
	}
	
	public Optional<Transition<S, E>> getTransition(E event, StateMachineCtx<S, E> ctx) {
		return transitions.get(event).stream()
				.filter(transition -> transition.getCondition().conditionMet(ctx))
				.findFirst();
	}
	
	public S getId() {
		return id;
	}
	public void setId(S id) {
		this.id = id;
	}
	public Map<E, List<Transition<S, E>>> getTransitions() {
		return transitions;
	}
	public void setTransitions(Map<E, List<Transition<S, E>>> transitions) {
		this.transitions = transitions;
	}
	public Map<E, Action<S, E>> getOnEventActions() {
		return onEventActions;
	}
	public void setOnEventActions(Map<E, Action<S, E>> onEventActions) {
		this.onEventActions = onEventActions;
	}
	public Map<E, Action<S, E>> getOnEnterActions() {
		return onEnterActions;
	}
	public void setOnEnterActions(Map<E, Action<S, E>> onEnterActions) {
		this.onEnterActions = onEnterActions;
	}
	public Map<E, Action<S, E>> getOnExitActions() {
		return onExitActions;
	}
	public void setOnExitActions(Map<E, Action<S, E>> onExitActions) {
		this.onExitActions = onExitActions;
	}
	
}
