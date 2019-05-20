package com.tedmo.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransitionMapBuilder<S, D> {
	
	private StateMachineModelBuilder<S, D> stateMachineModelBuilder;

	private Map<S, Map<Class<?>, List<Transition<S, D>>>> transitions;
	
	public TransitionMapBuilder(StateMachineModelBuilder<S, D> stateMachineModelBuilder) {
		this.stateMachineModelBuilder = stateMachineModelBuilder;
		this.transitions = new HashMap<>();
	}
	
	public TransitionBuilder<S, D> transition() {
		return new TransitionBuilder<>(this);
	}
	
	public TransitionMapBuilder<S, D> addTransition(TransitionBuilder<S, D> transitionBuilder) {
		
		// TODO validate transition builder
		
		S from = transitionBuilder.getFrom();
		if(transitions.get(from) == null) {
			transitions.put(from, new HashMap<>());
		}
		
		Map<Class<?>, List<Transition<S, D>>> stateTransitions = transitions.get(from);
		
		Class<?> on = transitionBuilder.getOn();
		if(stateTransitions.get(on) == null) {
			stateTransitions.put(on, new ArrayList<>());
		}
		
		stateTransitions.get(on).add(
				new Transition<>(transitionBuilder.getTo(), transitionBuilder.getWhen()));
		
		return this;
	}
}
