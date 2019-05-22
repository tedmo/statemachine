package com.tedmo.statemachine.builder;

public class OnEnterActionBuilder<S, D> extends TransitionalActionBuilder<S, D>{
	
	public OnEnterActionBuilder(ActionBuilder<S, D> actionBuilder, S enteredState) {
		super(actionBuilder, enteredState);
	}
	
	@Override
	protected StateMachineModelBuilder<S, D> addToMap() {
		return this.actionBuilder.addOnEnterAction(this);
	}

}
