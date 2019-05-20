package com.tedmo.statemachine;

public class OnEnterActionBuilder<S, D> extends TransitionalActionBuilder<S, D>{
	
	public OnEnterActionBuilder(ActionMapBuilder<S, D> actionMapBuilder, S enteredState) {
		super(actionMapBuilder, enteredState);
	}
	
	@Override
	protected ActionMapBuilder<S, D> addToMap() {
		this.actionMapBuilder.addOnEventAction(this);
		return null;
	}

}
