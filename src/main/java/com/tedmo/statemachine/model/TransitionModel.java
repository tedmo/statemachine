package com.tedmo.statemachine.model;

import java.util.Optional;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class TransitionModel<S, C> {
	
	private final S toState;
	private final Predicate<C> condition;
	
	public Optional<Predicate<C>> getCondition() {
		return Optional.ofNullable(condition);
	}
	
}
