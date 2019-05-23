package com.tedmo.statemachine.test.functional;

import static com.tedmo.statemachine.test.util.TestStateId.END_STATE;
import static com.tedmo.statemachine.test.util.TestStateId.START_STATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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
		
		stateMachine = new StateMachine<>(appCtx, buildStateMachineModel());
		
	}

	@Test
	public void test() {
		stateMachine.sendEvent(event);
		
		assertThat(stateMachine.getCurrentState()).isEqualTo(END_STATE);
		
		verify(appCtx).logAction(START_STATE, event, ON_EVENT);
		verify(appCtx).logAction(START_STATE, event, ON_EXIT);
		verify(appCtx).logAction(END_STATE, event, ON_ENTER);
		
		verify(onTestEvent).doAction(stateMachine, event);
		verify(onEnterTestEvent).doAction(stateMachine, event);
		verify(onExitTestEvent).doAction(stateMachine, event);
	}
	
	private StateMachineModel<TestStateId, TestAppCtx> buildStateMachineModel() {
		return new StateMachineModelBuilder<TestStateId, TestAppCtx>()
			.states(START_STATE, END_STATE)
			.initialState(START_STATE)
			.transition()
				.from(START_STATE)
				.to(END_STATE)
				.on(TestEvent.class)
				.withoutCondition()
			.action()
				.in(START_STATE)
				.on(TestEvent.class)
				.doAction(onTestEvent)
			.action()
				.exiting(START_STATE)
				.on(TestEvent.class)
				.doAction(onExitTestEvent)
			.action()
				.entering(END_STATE)
				.on(TestEvent.class)
				.doAction(onEnterTestEvent)
			.build();
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
	
}
