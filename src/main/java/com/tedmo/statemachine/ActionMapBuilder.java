package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.Map;

public class ActionMapBuilder <S, D> {

	private StateMachineModelBuilder<S, D> stateMachineModelBuilder;
	
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onEventActions;
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onEnterActions;
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onExitActions;
	
	public ActionMapBuilder(StateMachineModelBuilder<S, D> stateMachineModelBuilder) {
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
	
	protected ActionMapBuilder<S, D> addOnEventAction(TransitionalActionBuilder<S, D> actionBuilder) {
		addAction(onEventActions, actionBuilder);
		return this;
	}
	
	protected ActionMapBuilder<S, D> addOnEnterAction(TransitionalActionBuilder<S, D> actionBuilder) {
		addAction(onEventActions, actionBuilder);
		return this;
	}
	
	protected ActionMapBuilder<S, D> addOnExitAction(TransitionalActionBuilder<S, D> actionBuilder) {
		addAction(onEventActions, actionBuilder);
		return this;
	}

	private void addAction(Map<S, Map<Class<?>, Action<S, D, ?>>> actions,
			TransitionalActionBuilder<S, D> actionBuilder) {
		S state = actionBuilder.getState();
		if(actions.get(state) == null) {
			actions.put(state, new HashMap<>());
		}
		
		actions.get(state).put(actionBuilder.getEvent(), actionBuilder.getAction());
	}
	
}
