package com.tedmo.statemachine.model.builder;

public class OnExitActionBuilder<S extends Enum<S>, D> extends TransitionalActionBuilder<S, D>{
	
	public OnExitActionBuilder(ActionBuilder<S, D> actionBuilder, S enteredState) {
		super(actionBuilder, enteredState);
	}
	
	@Override
	protected StateMachineModelBuilder<S, D> addToMap() {
		return this.actionBuilder.addOnExitAction(this);
	}

}
