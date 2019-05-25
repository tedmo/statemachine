package com.tedmo.statemachine.test.util;

import com.tedmo.statemachine.model.Action;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestAction implements Action<TestStateId, TestAppCtx, TestEvent>{
	private String actionType;

	@Override
	public void doAction(TestStateId currentState, TestAppCtx ctx, TestEvent event) {
		ctx.logAction(currentState, event, actionType);
		
		System.out.println(buildActionMessage(
				actionType,
				String.valueOf(currentState),
				event.getMessage()));
	}
	
	private String buildActionMessage(String actionType, String state, String message) {
		return String.format("%s %s: %s", actionType, state, message);
	}
}
