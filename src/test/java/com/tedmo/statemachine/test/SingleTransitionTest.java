package com.tedmo.statemachine.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tedmo.statemachine.Action;
import com.tedmo.statemachine.Condition;
import com.tedmo.statemachine.StateContext;
import com.tedmo.statemachine.StateMachineCtx;
import com.tedmo.statemachine.Transition;

public class SingleTransitionTest {
	
	private static final String ON_EVENT = "onEvent";
	private static final String ON_ENTER = "onEnter";
	private static final String ON_EXIT = "onExit";
	
	private StateMachineCtx<StateId> stateMachineCtx;
	
	private StateContext<StateId> initStateContext;
	private StateContext<StateId> suspendedStateContext;
	
	private Action<StateId, TestEvent> onTestEvent;
	private Action<StateId, TestEvent> onEnterTestEvent;
	private Action<StateId, TestEvent> onExitTestEvent;
	
	@Mock
	Condition condition;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		onTestEvent = buildCreateEventAction(ON_EVENT);
		onEnterTestEvent = buildCreateEventAction(ON_ENTER);
		onExitTestEvent = buildCreateEventAction(ON_EXIT);
		
		stateMachineCtx = buildStateMachine();
	}
	
	private Action<StateId, TestEvent> buildCreateEventAction(String actionType) {
		return (ctx, event) -> 
			System.out.println(buildActionMessage(
					actionType,
					String.valueOf(ctx.getCurrentState().getId()),
					event.getCreateMessage()));
	}
	
	private String buildActionMessage(String actionType, String state, String message) {
		return String.format("%s %s: %s", actionType, state, message);
	}
	
	private StateMachineCtx<StateId> buildStateMachine() {
		StateMachineCtx<StateId> stateMachineCtx = new StateMachineCtx<>();
		
		initStateContext = new StateContext<>(StateId.INIT);
		
		Transition<StateId> initToSuspendedTransition = new Transition<>();
		initToSuspendedTransition.setToState(StateId.SUSPENDED);
		initStateContext.putTransition(TestEvent.class, initToSuspendedTransition);
		
		initStateContext.putOnEventAction(TestEvent.class, onTestEvent);
		initStateContext.putOnExitAction(TestEvent.class, onExitTestEvent);
		
		suspendedStateContext = new StateContext<>(StateId.SUSPENDED);
		suspendedStateContext.putOnEnterAction(TestEvent.class, onEnterTestEvent);
		
		Map<StateId, StateContext<StateId>> stateContexts = new HashMap<>();
		stateContexts.put(StateId.INIT, initStateContext);
		stateContexts.put(StateId.SUSPENDED, suspendedStateContext);
		stateMachineCtx.setStates(stateContexts);
		
		stateMachineCtx.setCurrentState(initStateContext);
		
		return stateMachineCtx;
	}

	@Test
	public void test() {
		
		TestEvent event = new TestEvent("create event message");
		stateMachineCtx.sendEvent(event);
	}
	
}
