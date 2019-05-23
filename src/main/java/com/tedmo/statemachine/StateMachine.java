package com.tedmo.statemachine;

import lombok.Getter;

@Getter
public class StateMachine <S, C> {
	
	final private C appCtx;
	final private StateMachineModel<S, C> model;
	private S currentState;
	
	public StateMachine(C appCtx, StateMachineModel<S, C> model) {
		this(appCtx, model, model.getInitialState());
	}
	
	public StateMachine(C appCtx, StateMachineModel<S, C> model, S currentState) {
		this.appCtx = appCtx;
		this.model = model;
		this.currentState = currentState;
	}
	
	public <E> void sendEvent(E event) {
		model.getState(currentState).getOnEventAction(event).doAction(this, event);
		model.getState(currentState).getTransition(event, this)
			.ifPresent(transition -> doTransition(transition, event));
	}
	
	private <E> void doTransition(Transition<S, C> transition, E event) {
		model.getState(currentState).getOnExitAction(event).doAction(this, event);
		currentState = transition.getToState();
		model.getState(currentState).getOnEnterAction(event).doAction(this, event);
	}
	
}
