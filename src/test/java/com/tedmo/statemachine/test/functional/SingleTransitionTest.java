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
import com.tedmo.statemachine.StateMachine;
import com.tedmo.statemachine.StateMachineModel;
import com.tedmo.statemachine.StateModel;
import com.tedmo.statemachine.Transition;
import com.tedmo.statemachine.test.util.TestAppCtx;
import com.tedmo.statemachine.test.util.TestEvent;
import com.tedmo.statemachine.test.util.TestStateId;

import lombok.AllArgsConstructor;

public class SingleTransitionTest {
	
	private static final String ON_EVENT = "onEvent";
	private static final String ON_ENTER = "onEnter";
	private static final String ON_EXIT = "onExit";
	
	private StateMachine<TestStateId, TestAppCtx> stateMachine;
	
	private StateModel<TestStateId, TestAppCtx> initStateModel;
	private StateModel<TestStateId, TestAppCtx> suspendedStateModel;
	
	private Action<TestStateId, TestAppCtx, TestEvent> onTestEvent;
	private Action<TestStateId, TestAppCtx, TestEvent> onEnterTestEvent;
	private Action<TestStateId, TestAppCtx, TestEvent> onExitTestEvent;
	
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
		
		stateMachine = new StateMachine<>();
		stateMachine.setAppCtx(appCtx);
		stateMachine.setModel(buildStateMachineModel());
		stateMachine.setCurrentState(TestStateId.START_STATE);
		
		
	}
	
	@AllArgsConstructor
	private static class TestAction implements Action<TestStateId, TestAppCtx, TestEvent> {
		
		private String actionType;

		@Override
		public void doAction(StateMachine<TestStateId, TestAppCtx> ctx, TestEvent event) {
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
	
	private StateMachineModel<TestStateId, TestAppCtx> buildStateMachineModel() {
		initStateModel = new StateModel<>(TestStateId.START_STATE);
		
		Transition<TestStateId, TestAppCtx> initToSuspendedTransition = new Transition<>();
		initToSuspendedTransition.setToState(TestStateId.END_STATE);
		initStateModel.putTransition(TestEvent.class, initToSuspendedTransition);
		
		initStateModel.putOnEventAction(TestEvent.class, onTestEvent);
		initStateModel.putOnExitAction(TestEvent.class, onExitTestEvent);
		
		suspendedStateModel = new StateModel<>(TestStateId.END_STATE);
		suspendedStateModel.putOnEnterAction(TestEvent.class, onEnterTestEvent);
		
		Map<TestStateId, StateModel<TestStateId, TestAppCtx>> stateModels = new HashMap<>();
		stateModels.put(TestStateId.START_STATE, initStateModel);
		stateModels.put(TestStateId.END_STATE, suspendedStateModel);
		
		StateMachineModel<TestStateId, TestAppCtx> stateMachineModel = new StateMachineModel<>(stateModels);
		
		return stateMachineModel;
	}

	@Test
	public void test() {
		stateMachine.sendEvent(event);
		
		assertThat(stateMachine.getCurrentState()).isEqualTo(TestStateId.END_STATE);
		
		verify(appCtx).logAction(TestStateId.START_STATE, event, ON_EVENT);
		verify(appCtx).logAction(TestStateId.START_STATE, event, ON_EXIT);
		verify(appCtx).logAction(TestStateId.END_STATE, event, ON_ENTER);
		
		verify(onTestEvent).doAction(stateMachine, event);
		verify(onEnterTestEvent).doAction(stateMachine, event);
		verify(onExitTestEvent).doAction(stateMachine, event);
	}
	
}
