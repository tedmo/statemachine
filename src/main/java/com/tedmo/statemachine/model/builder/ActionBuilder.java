package com.tedmo.statemachine.model.builder;

public class ActionBuilder <S extends Enum<S>, D> {

	private StateMachineModelBuilder<S, D> stateMachineModelBuilder;
	
	public ActionBuilder(StateMachineModelBuilder<S, D> stateMachineModelBuilder) {
		this.stateMachineModelBuilder = stateMachineModelBuilder;
	}
	
	public TransitionalActionBuilder<S, D> in(S state) {
		return new OnEventActionBuilder<S, D>(this, state);
	}
	
	public TransitionalActionBuilder<S, D> entering(S state) {
		return new OnEnterActionBuilder<S, D>(this, state);
	}
	
	public TransitionalActionBuilder<S, D> exiting(S state) {
		return new OnExitActionBuilder<S, D>(this, state);
	}
	
	protected StateMachineModelBuilder<S, D> addOnEventAction(TransitionalActionBuilder<S, D> actionBuilder) {
		return stateMachineModelBuilder.addOnEventAction(actionBuilder);
	}
	
	protected StateMachineModelBuilder<S, D> addOnEnterAction(TransitionalActionBuilder<S, D> actionBuilder) {
		return stateMachineModelBuilder.addOnEnterAction(actionBuilder);
	}
	
	protected StateMachineModelBuilder<S, D> addOnExitAction(TransitionalActionBuilder<S, D> actionBuilder) {
		return stateMachineModelBuilder.addOnExitAction(actionBuilder);
	}
	
}
