package com.tedmo.statemachine;

public class StateMachine <S, C> {
	
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
		model.getOnEventAction(currentState, event).doAction(currentState, appCtx, event);
		model.getTransition(currentState, event, appCtx)
			.ifPresent(transition -> doTransition(transition, event));
	}
	
	private <E> void doTransition(Transition<S, C> transition, E event) {
		model.getOnExitAction(currentState, event).doAction(currentState, appCtx, event);
		currentState = transition.getToState();
		model.getOnEnterAction(currentState, event).doAction(currentState, appCtx, event);
	}
	
}
