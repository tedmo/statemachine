package com.tedmo.statemachine;

public class OnExitActionBuilder<S, D> extends TransitionalActionBuilder<S, D>{
	
	public OnExitActionBuilder(ActionMapBuilder<S, D> actionMapBuilder, S enteredState) {
		super(actionMapBuilder, enteredState);
	}
	
	@Override
	protected ActionMapBuilder<S, D> addToMap() {
		this.actionMapBuilder.addOnEnterAction(this);
		return null;
	}

}
