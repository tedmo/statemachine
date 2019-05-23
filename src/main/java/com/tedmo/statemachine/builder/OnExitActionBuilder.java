package com.tedmo.statemachine.builder;

public class OnExitActionBuilder<S, D> extends TransitionalActionBuilder<S, D>{
	
	public OnExitActionBuilder(ActionBuilder<S, D> actionBuilder, S enteredState) {
		super(actionBuilder, enteredState);
	}
	
	@Override
	protected StateMachineModelBuilder<S, D> addToMap() {
		return this.actionBuilder.addOnExitAction(this);
	}

}
