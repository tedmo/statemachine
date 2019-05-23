package com.tedmo.statemachine.test.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class TestAppCtx {
	
	List<TestActionLog> actionLog = new ArrayList<>();
	
	public void logAction(TestStateId state, Object event, String actionType) {
		// do nothing
		actionLog.add(new TestActionLog(state, event, actionType));
	}
}
