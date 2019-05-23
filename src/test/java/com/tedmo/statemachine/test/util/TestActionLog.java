package com.tedmo.statemachine.test.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestActionLog {
	private TestStateId state;
	private Object event;
	private String actionType;
}
