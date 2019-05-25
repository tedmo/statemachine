package com.tedmo.statemachine.model.builder;

public class OnEnterActionBuilder<S extends Enum<S>, D> extends TransitionalActionBuilder<S, D>{
	
	public OnEnterActionBuilder(ActionBuilder<S, D> actionBuilder, S enteredState) {
		super(actionBuilder, enteredState);
	}
	
	@Override
	protected StateMachineModelBuilder<S, D> addToMap() {
		return this.actionBuilder.addOnEnterAction(this);
	}

}
