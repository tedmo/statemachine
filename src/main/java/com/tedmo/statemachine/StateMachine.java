package com.tedmo.statemachine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateMachine <S, D> {
	
	private StateMachineModel<S, D> model;
	private S currentState;
	private D appCtx;
	
	public <E> void sendEvent(E event) {
		model.getState(currentState).getOnEventAction(event).doAction(this, event);
		model.getState(currentState).getTransition(event, this)
			.ifPresent(transition -> doTransition(transition, event));
	}
	
	private <E> void doTransition(Transition<S, D> transition, E event) {
		model.getState(currentState).getOnExitAction(event).doAction(this, event);
		currentState = transition.getToState();
		model.getState(currentState).getOnEnterAction(event).doAction(this, event);
	}
	
}
