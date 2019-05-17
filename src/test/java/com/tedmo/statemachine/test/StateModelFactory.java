package com.tedmo.statemachine.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tedmo.statemachine.Action;
import com.tedmo.statemachine.Event;
import com.tedmo.statemachine.StateContext;
import com.tedmo.statemachine.StateMachineCtx;
import com.tedmo.statemachine.Transition;

public class StateModelFactory {
	
	public static void main(String[] args) {
		StateMachineCtx<StateId, EventId> stateMachineCtx = buildStateMachine();
		stateMachineCtx.sendEvent(new Event<EventId>(EventId.NEW));
	}
	
	public static StateMachineCtx<StateId, EventId> buildStateMachine() {
		StateMachineCtx<StateId, EventId> stateMachineCtx = new StateMachineCtx<>();
		
		StateContext<StateId, EventId> initStateContext = new StateContext<>();
		initStateContext.setId(StateId.INIT);
		
		Transition<StateId, EventId> initToSuspendedTransition = new Transition<>();
		initToSuspendedTransition.setToState(StateId.SUSPENDED);
		initToSuspendedTransition.setCondition((ctx) -> {
			System.out.println("evaluating condition...");
			return true;
		});
		
		initStateContext.setTransitions(new HashMap<>());
		initStateContext.getTransitions().put(EventId.NEW, Arrays.asList(initToSuspendedTransition));
		
		Action<StateId, EventId> createAction = (ctx, event) -> {
			System.out.println("create something...");
		};
		
		Action<StateId, EventId> onEnterAction = (ctx, event) -> 
			System.out.println(
				"entering " + ctx.getCurrentStateContext().getId().name() + " state on "
						+ event.getName() + " event...");
			
		Action<StateId, EventId> onExitAction = (ctx, event) ->
			System.out.println(
				"exiting " + ctx.getCurrentStateContext().getId().name() + " state on "
						+ event.getName() + " event...");
		
		initStateContext.setOnExitActions(new HashMap<>());
		initStateContext.getOnExitActions().put(EventId.NEW, onExitAction);
		
		initStateContext.setOnEventActions(new HashMap<>());
		initStateContext.getOnEventActions().put(EventId.NEW, createAction);
		
		StateContext<StateId, EventId> suspendedStateContext = new StateContext<>();
		suspendedStateContext.setId(StateId.SUSPENDED);
		suspendedStateContext.setOnEnterActions(new HashMap<>());
		suspendedStateContext.getOnEnterActions().put(EventId.NEW, onEnterAction);
		
		Map<StateId, StateContext<StateId, EventId>> stateContexts = new HashMap<>();
		stateContexts.put(StateId.INIT, initStateContext);
		stateContexts.put(StateId.SUSPENDED, suspendedStateContext);
		stateMachineCtx.setStateContexts(stateContexts);
		
		stateMachineCtx.setCurrentStateContext(initStateContext);
		
		return stateMachineCtx;
	}

}
