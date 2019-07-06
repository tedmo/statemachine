package com.tedmo.statemachine.model.builder;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.tedmo.statemachine.model.Action;
import com.tedmo.statemachine.model.StateMachineModel;
import com.tedmo.statemachine.model.StateModel;
import com.tedmo.statemachine.model.TransitionModel;

/**
 * Helper for building a state machine model
 *
 * @param <S> Enum holding all valid states
 * @param <D> Domain object that the state machine maintains state for 
 */
public class StateMachineModelBuilder<S extends Enum<S>, D> {
	
	private Set<S> states;
	private S initialState;
	private Map<S, Map<Class<?>, List<TransitionModel<S, D>>>> transitions = new HashMap<>();
	
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onEventActions = new HashMap<>();
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onEnterActions = new HashMap<>();
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onExitActions = new HashMap<>();
	
	public StateMachineModel<S, D> build() {
		
		if(Objects.isNull(states)) {
			
		}
		
		Map<S, StateModel<S, D>> stateModels = new HashMap<>();
		
		for(S state : states) {
			StateModel<S, D> stateModel = buildStateModel(state);
			stateModels.put(state, stateModel);
		}
		
		return new StateMachineModel<>(stateModels, initialState);
	}

	private StateModel<S, D> buildStateModel(S state) {
		
		StateModel<S, D> stateModel = StateModel.<S, D>builder()
			.transitions(transitions.get(state))
			.onEventActions(onEventActions.get(state))
			.onEnterActions(onEnterActions.get(state))
			.onExitActions(onExitActions.get(state))
			.build();
		return stateModel;
	}
	
	public StateMachineModelBuilder<S, D> states(Class<S> stateEnum) {
		if(Objects.isNull(stateEnum)) {
			throw new IllegalArgumentException("An enum type must be provided to declare valid states in the state machine");
		}
		
		EnumSet<S> enumSet = EnumSet.allOf(stateEnum);
		if(enumSet.isEmpty()) {
			throw new IllegalArgumentException("At least one element must be declared in the provided enum type");
		}
		
		this.states = enumSet;
		return this;
	}
	
	public StateMachineModelBuilder<S, D> initialState(S initialState) {
		if(Objects.isNull(initialState)) {
			throw new IllegalArgumentException("The initial state must be provided");
		}
		this.initialState = initialState;
		return this;
	}
	
	public TransitionBuilder<S, D> transition() {
		return new TransitionBuilder<>(this);
	}
	
	protected StateMachineModelBuilder<S, D> addTransition(TransitionBuilder<S, D> transitionBuilder) {
		
		// TODO validate transition builder
		
		S from = transitionBuilder.getFrom();
		if(Objects.isNull(transitions.get(from))) {
			transitions.put(from, new HashMap<>());
		}
		
		Map<Class<?>, List<TransitionModel<S, D>>> stateTransitions = transitions.get(from);
		
		Class<?> on = transitionBuilder.getOn();
		if(Objects.isNull(stateTransitions.get(on))) {
			stateTransitions.put(on, new ArrayList<>());
		}
		
		stateTransitions.get(on).add(
				new TransitionModel<>(transitionBuilder.getTo(), transitionBuilder.getWhen()));
		
		return this;
	}
	
	public ActionBuilder<S, D> action() {
		return new ActionBuilder<>(this);
	}
	
	protected StateMachineModelBuilder<S, D> addOnEventAction(TransitionalActionBuilder<S, D> actionBuilder) {
		addAction(onEventActions, actionBuilder);
		return this;
	}
	
	protected StateMachineModelBuilder<S, D> addOnEnterAction(TransitionalActionBuilder<S, D> actionBuilder) {
		addAction(onEnterActions, actionBuilder);
		return this;
	}
	
	protected StateMachineModelBuilder<S, D> addOnExitAction(TransitionalActionBuilder<S, D> actionBuilder) {
		addAction(onExitActions, actionBuilder);
		return this;
	}

	private void addAction(Map<S, Map<Class<?>, Action<S, D, ?>>> actions,
			TransitionalActionBuilder<S, D> actionBuilder) {
		S state = actionBuilder.getState();
		if(Objects.isNull(actions.get(state))) {
			actions.put(state, new HashMap<>());
		}
		
		actions.get(state).put(actionBuilder.getEvent(), actionBuilder.getAction());
	}
}
