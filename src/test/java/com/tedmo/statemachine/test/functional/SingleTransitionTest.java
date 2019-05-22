package com.tedmo.statemachine.test.functional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.tedmo.statemachine.Action;
import com.tedmo.statemachine.StateMachine;
import com.tedmo.statemachine.StateMachineModel;
import com.tedmo.statemachine.builder.StateMachineModelBuilder;
import com.tedmo.statemachine.test.util.TestAppCtx;
import com.tedmo.statemachine.test.util.TestEvent;
import com.tedmo.statemachine.test.util.TestStateId;

import lombok.AllArgsConstructor;

public class SingleTransitionTest {
	
	private static final String ON_EVENT = "onEvent";
	private static final String ON_ENTER = "onEnter";
	private static final String ON_EXIT = "onExit";
	
	private StateMachine<TestStateId, TestAppCtx> stateMachine;
	
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
		return new StateMachineModelBuilder<TestStateId, TestAppCtx>()
			.states(new HashSet<>(Arrays.asList(TestStateId.START_STATE, TestStateId.END_STATE)))
			.initialState(TestStateId.START_STATE)
			.transition()
				.from(TestStateId.START_STATE)
				.to(TestStateId.END_STATE)
				.on(TestEvent.class)
				.withoutCondition()
			.action()
				.in(TestStateId.START_STATE)
				.on(TestEvent.class)
				.doAction(onTestEvent)
			.action()
				.exiting(TestStateId.START_STATE)
				.on(TestEvent.class)
				.doAction(onExitTestEvent)
			.action()
				.entering(TestStateId.END_STATE)
				.on(TestEvent.class)
				.doAction(onEnterTestEvent)
			.build();
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
