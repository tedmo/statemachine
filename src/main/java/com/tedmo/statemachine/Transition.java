package com.tedmo.statemachine;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transition<S> {
	
	private S toState;
	private Condition condition;
	
	public Optional<Condition> getCondition() {
		return Optional.ofNullable(condition);
	}
	
}
