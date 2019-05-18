package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateMachineCtx <S, D> {
	
	private D appCtx;
	private State<S, D> currentState;
	private Map<S, State<S, D>> states = new HashMap<>();
	
	public <E> void sendEvent(E event) {
		currentState.getOnEventAction(event).doAction(this, event);
		currentState.getTransition(event, this)
			.ifPresent(transition -> doTransition(transition, event));
	}
	
	private <E> void doTransition(Transition<S, D> transition, E event) {
		currentState.getOnExitAction(event).doAction(this, event);
		currentState = states.get(transition.getToState());
		currentState.getOnEnterAction(event).doAction(this, event);
	}
	
	public S getCurrentState() {
		return currentState.getId();
	}
	
}
