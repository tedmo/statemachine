package com.tedmo.statemachine.test.functional;

import static com.tedmo.statemachine.test.util.TestStateId.END_STATE;
import static com.tedmo.statemachine.test.util.TestStateId.START_STATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.tedmo.statemachine.model.StateMachineModel;
import com.tedmo.statemachine.model.builder.StateMachineModelBuilder;
import com.tedmo.statemachine.test.util.TestAppCtx;
import com.tedmo.statemachine.test.util.TestEvent;
import com.tedmo.statemachine.test.util.TestStateId;

public class TwoTransitionTest extends TwoStateTestBase {
	
	@Override
	protected StateMachineModel<TestStateId, TestAppCtx> buildStateMachineModel() {
		return new StateMachineModelBuilder<TestStateId, TestAppCtx>()
			.states(TestStateId.class)
			.initialState(START_STATE)
			.transition()
				.from(START_STATE)
				.to(END_STATE)
				.on(TestEvent.class)
				.withoutCondition()
			.transition()
				.from(END_STATE)
				.to(START_STATE)
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
			.action()
				.in(END_STATE)
				.on(TestEvent.class)
				.doAction(onTestEvent)
			.action()
				.exiting(END_STATE)
				.on(TestEvent.class)
				.doAction(onExitTestEvent)
			.action()
				.entering(START_STATE)
				.on(TestEvent.class)
				.doAction(onEnterTestEvent)
			.build();
	}
	
	@Test
	public void testTransitionFromStartToEndAndEndToStart() {
		
		// Send first event to transition from START to END
		
		TestEvent firstEvent = new TestEvent("first event");
		stateMachine.sendEvent(firstEvent);
		
		assertThat(stateMachine.getCurrentState()).isEqualTo(END_STATE);
		
		verify(appCtx).logAction(START_STATE, firstEvent, ON_EVENT);
		verify(appCtx).logAction(START_STATE, firstEvent, ON_EXIT);
		verify(appCtx).logAction(END_STATE, firstEvent, ON_ENTER);
		
		verify(onTestEvent).doAction(START_STATE, appCtx, firstEvent);
		verify(onExitTestEvent).doAction(START_STATE, appCtx, firstEvent);
		verify(onEnterTestEvent).doAction(END_STATE, appCtx, firstEvent);
		
		
		// Send second event to transition from END to START
		
		TestEvent secondEvent = new TestEvent("second event");
		stateMachine.sendEvent(secondEvent);
		
		assertThat(stateMachine.getCurrentState()).isEqualTo(START_STATE);
		
		verify(appCtx).logAction(END_STATE, secondEvent, ON_EVENT);
		verify(appCtx).logAction(END_STATE, secondEvent, ON_EXIT);
		verify(appCtx).logAction(START_STATE, secondEvent, ON_ENTER);
		
		verify(onTestEvent).doAction(END_STATE, appCtx, secondEvent);
		verify(onExitTestEvent).doAction(END_STATE, appCtx, secondEvent);
		verify(onEnterTestEvent).doAction(START_STATE, appCtx, secondEvent);
	}
	
}
