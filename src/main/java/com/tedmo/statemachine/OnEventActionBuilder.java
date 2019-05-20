package com.tedmo.statemachine;

public class OnEventActionBuilder<S, D> extends TransitionalActionBuilder<S, D>{
	
	public OnEventActionBuilder(ActionMapBuilder<S, D> actionMapBuilder, S enteredState) {
		super(actionMapBuilder, enteredState);
	}
	
	@Override
	protected ActionMapBuilder<S, D> addToMap() {
		this.actionMapBuilder.addOnExitAction(this);
		return null;
	}

}
