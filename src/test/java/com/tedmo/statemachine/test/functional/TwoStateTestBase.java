package com.tedmo.statemachine.test.functional;

import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import com.tedmo.statemachine.StateMachine;
import com.tedmo.statemachine.model.Action;
import com.tedmo.statemachine.model.StateMachineModel;
import com.tedmo.statemachine.test.util.TestAction;
import com.tedmo.statemachine.test.util.TestAppCtx;
import com.tedmo.statemachine.test.util.TestEvent;
import com.tedmo.statemachine.test.util.TestStateId;

public abstract class TwoStateTestBase {

	protected static final String ON_EVENT = "onEvent";
	protected static final String ON_ENTER = "onEnter";
	protected static final String ON_EXIT = "onExit";
	
	protected StateMachine<TestStateId, TestAppCtx> stateMachine;
	
	protected Action<TestStateId, TestAppCtx, TestEvent> onTestEvent;
	protected Action<TestStateId, TestAppCtx, TestEvent> onEnterTestEvent;
	protected Action<TestStateId, TestAppCtx, TestEvent> onExitTestEvent;
	
	protected TestAppCtx appCtx;
	
	protected TestEvent event;
	
	@BeforeEach
	private void setupBase() {
		MockitoAnnotations.initMocks(this);
		
		appCtx = spy(new TestAppCtx());
		
		event = new TestEvent("test event message");
		
		onTestEvent = spy(new TestAction(ON_EVENT));
		onEnterTestEvent = spy(new TestAction(ON_ENTER));
		onExitTestEvent = spy(new TestAction(ON_EXIT));
		
		stateMachine = new StateMachine<>(appCtx, buildStateMachineModel());
		
		setup();
		
	}
	
	protected void setup() {}
	
	protected abstract StateMachineModel<TestStateId, TestAppCtx> buildStateMachineModel();
}
