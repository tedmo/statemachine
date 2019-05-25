package com.tedmo.statemachine.model;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class TransitionModel<S, C> {
	
	private final S toState;
	private final Condition<C> condition;
	
	public Optional<Condition<C>> getCondition() {
		return Optional.ofNullable(condition);
	}
	
}
