package com.tedmo.statemachine.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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

import lombok.AllArgsConstructor;

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
	private Condition condition;
	
	private TestEvent event;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		event = new TestEvent("test event message");
		
		onTestEvent = spy(new TestAction(ON_EVENT));
		onEnterTestEvent = spy(new TestAction(ON_ENTER));
		onExitTestEvent = spy(new TestAction(ON_EXIT));
		
		stateMachineCtx = buildStateMachine();
		
	}
	
	@AllArgsConstructor
	private static class TestAction implements Action<StateId, TestEvent> {
		
		private String actionType;

		@Override
		public void doAction(StateMachineCtx<StateId> ctx, TestEvent event) {
			System.out.println(buildActionMessage(
					actionType,
					String.valueOf(ctx.getCurrentState().getId()),
					event.getMessage()));
		}
		
		private String buildActionMessage(String actionType, String state, String message) {
			return String.format("%s %s: %s", actionType, state, message);
		}
		
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
		
		stateMachineCtx.sendEvent(event);
		StateContext<StateId> currentState = stateMachineCtx.getCurrentState();
		assertThat(currentState).isNotNull();
		assertThat(currentState.getId()).isEqualTo(StateId.SUSPENDED);
		
		verify(onTestEvent).doAction(stateMachineCtx, event);
		verify(onEnterTestEvent).doAction(stateMachineCtx, event);
		verify(onExitTestEvent).doAction(stateMachineCtx, event);
	}
	
}
