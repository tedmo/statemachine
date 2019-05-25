package com.tedmo.statemachine.test.functional;

import static com.tedmo.statemachine.test.util.TestStateId.END_STATE;
import static com.tedmo.statemachine.test.util.TestStateId.START_STATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.tedmo.statemachine.model.StateMachineModel;
import com.tedmo.statemachine.model.builder.StateMachineModelBuilder;
import com.tedmo.statemachine.test.util.TestAppCtx;
import com.tedmo.statemachine.test.util.TestEvent;
import com.tedmo.statemachine.test.util.TestStateId;

public class ConditionalTransitionTest extends TwoStateTestBase {
	
	@Override
	protected StateMachineModel<TestStateId, TestAppCtx> buildStateMachineModel() {
		return new StateMachineModelBuilder<TestStateId, TestAppCtx>()
			.states(TestStateId.class)
			.initialState(START_STATE)
			.transition()
				.from(START_STATE)
				.to(END_STATE)
				.on(TestEvent.class)
				.when((ctx) -> false)
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
	
	@Test
	public void test() {
		stateMachine.sendEvent(event);
		
		assertThat(stateMachine.getCurrentState()).isEqualTo(START_STATE);
		
		verify(appCtx).logAction(START_STATE, event, ON_EVENT);
		verify(appCtx, never()).logAction(START_STATE, event, ON_EXIT);
		verify(appCtx, never()).logAction(END_STATE, event, ON_ENTER);
		
		verify(onTestEvent).doAction(START_STATE, appCtx, event);
		verify(onEnterTestEvent, never()).doAction(END_STATE, appCtx, event);
		verify(onExitTestEvent, never()).doAction(END_STATE, appCtx, event);
	}
	
	
}
