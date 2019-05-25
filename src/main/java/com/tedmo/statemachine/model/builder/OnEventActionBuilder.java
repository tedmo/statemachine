package com.tedmo.statemachine.model.builder;

public class OnEventActionBuilder<S, D> extends TransitionalActionBuilder<S, D>{
	
	public OnEventActionBuilder(ActionBuilder<S, D> actionBuilder, S enteredState) {
		super(actionBuilder, enteredState);
	}
	
	@Override
	protected StateMachineModelBuilder<S, D> addToMap() {
		return this.actionBuilder.addOnEventAction(this);
	}

}
