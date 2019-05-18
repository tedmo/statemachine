package com.tedmo.statemachine.test.functional;

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
import com.tedmo.statemachine.State;
import com.tedmo.statemachine.StateMachineCtx;
import com.tedmo.statemachine.Transition;
import com.tedmo.statemachine.test.util.TestAppCtx;
import com.tedmo.statemachine.test.util.TestEvent;
import com.tedmo.statemachine.test.util.TestStateId;

import lombok.AllArgsConstructor;

public class SingleTransitionTest {
	
	private static final String ON_EVENT = "onEvent";
	private static final String ON_ENTER = "onEnter";
	private static final String ON_EXIT = "onExit";
	
	private StateMachineCtx<TestStateId, TestAppCtx> stateMachineCtx;
	
	private State<TestStateId, TestAppCtx> initStateContext;
	private State<TestStateId, TestAppCtx> suspendedStateContext;
	
	private Action<TestStateId, TestAppCtx, TestEvent> onTestEvent;
	private Action<TestStateId, TestAppCtx, TestEvent> onEnterTestEvent;
	private Action<TestStateId, TestAppCtx, TestEvent> onExitTestEvent;
	
	@Mock
	private Condition<TestAppCtx> condition;
	
	private TestAppCtx appCtx;
	
	private TestEvent event;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		appCtx = spy(new TestAppCtx());
		
		event = new TestEvent("test event message");
		
		onTestEvent = spy(new TestAction(ON_EVENT));
		onEnterTestEvent = spy(new TestAction(ON_ENTER));
		onExitTestEvent = spy(new TestAction(ON_EXIT));
		
		stateMachineCtx = buildStateMachine();
		
	}
	
	@AllArgsConstructor
	private static class TestAction implements Action<TestStateId, TestAppCtx, TestEvent> {
		
		private String actionType;

		@Override
		public void doAction(StateMachineCtx<TestStateId, TestAppCtx> ctx, TestEvent event) {
			ctx.getAppCtx().logAction(ctx.getCurrentState(), event, actionType);
			
			System.out.println(buildActionMessage(
					actionType,
					String.valueOf(ctx.getCurrentState()),
					event.getMessage()));
		}
		
		private String buildActionMessage(String actionType, String state, String message) {
			return String.format("%s %s: %s", actionType, state, message);
		}
		
	}
	
	private StateMachineCtx<TestStateId, TestAppCtx> buildStateMachine() {
		StateMachineCtx<TestStateId, TestAppCtx> stateMachineCtx = new StateMachineCtx<>();
		stateMachineCtx.setAppCtx(appCtx);
		
		initStateContext = new State<>(TestStateId.START_STATE);
		
		Transition<TestStateId, TestAppCtx> initToSuspendedTransition = new Transition<>();
		initToSuspendedTransition.setToState(TestStateId.END_STATE);
		initStateContext.putTransition(TestEvent.class, initToSuspendedTransition);
		
		initStateContext.putOnEventAction(TestEvent.class, onTestEvent);
		initStateContext.putOnExitAction(TestEvent.class, onExitTestEvent);
		
		suspendedStateContext = new State<>(TestStateId.END_STATE);
		suspendedStateContext.putOnEnterAction(TestEvent.class, onEnterTestEvent);
		
		Map<TestStateId, State<TestStateId, TestAppCtx>> stateContexts = new HashMap<>();
		stateContexts.put(TestStateId.START_STATE, initStateContext);
		stateContexts.put(TestStateId.END_STATE, suspendedStateContext);
		stateMachineCtx.setStates(stateContexts);
		
		stateMachineCtx.setCurrentState(initStateContext);
		
		return stateMachineCtx;
	}

	@Test
	public void test() {
		stateMachineCtx.sendEvent(event);
		
		assertThat(stateMachineCtx.getCurrentState()).isEqualTo(TestStateId.END_STATE);
		
		verify(appCtx).logAction(TestStateId.START_STATE, event, ON_EVENT);
		verify(appCtx).logAction(TestStateId.START_STATE, event, ON_EXIT);
		verify(appCtx).logAction(TestStateId.END_STATE, event, ON_ENTER);
		
		verify(onTestEvent).doAction(stateMachineCtx, event);
		verify(onEnterTestEvent).doAction(stateMachineCtx, event);
		verify(onExitTestEvent).doAction(stateMachineCtx, event);
	}
	
}
