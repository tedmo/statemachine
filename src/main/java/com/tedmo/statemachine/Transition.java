package com.tedmo.statemachine;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(value=AccessLevel.PACKAGE)
@AllArgsConstructor
public final class Transition<S, C> {
	
	private final S toState;
	private final Condition<C> condition;
	
	public Optional<Condition<C>> getCondition() {
		return Optional.ofNullable(condition);
	}
	
}
