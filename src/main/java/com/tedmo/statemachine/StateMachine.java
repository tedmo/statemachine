package com.tedmo.statemachine;

import com.tedmo.statemachine.model.StateMachineModel;
import com.tedmo.statemachine.model.TransitionModel;

public final class StateMachine <S extends Enum<S>, C> {
	
	final private C appCtx;
	final private StateMachineModel<S, C> model;
	private S currentState;
	
	public StateMachine(C appCtx, StateMachineModel<S, C> model) {
		this(appCtx, model, model.getInitialState());
	}
	
	public S getCurrentState() {
		return currentState;
	}
	
	public StateMachine(C appCtx, StateMachineModel<S, C> model, S currentState) {
		this.appCtx = appCtx;
		this.model = model;
		this.currentState = currentState;
	}
	
	public <E> void sendEvent(E event) {
		model.getOnEventAction(currentState, event)
			.ifPresent(action -> action.doAction(currentState, appCtx, event));
		model.getTransition(currentState, event, appCtx)
			.ifPresent(transition -> doTransition(transition, event));
	}
	
	private <E> void doTransition(TransitionModel<S, C> transition, E event) {
		model.getOnExitAction(currentState, event)
			.ifPresent(action -> action.doAction(currentState, appCtx, event));
		currentState = transition.getToState();
		model.getOnEnterAction(currentState, event)
			.ifPresent(action -> action.doAction(currentState, appCtx, event));
	}
	
}
