package com.tedmo.statemachine;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transition<S, D> {
	
	private S toState;
	private Condition<D> condition;
	
	public Optional<Condition<D>> getCondition() {
		return Optional.ofNullable(condition);
	}
	
}
