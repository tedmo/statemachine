package com.tedmo.statemachine;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transition<S, D> {
	
	private S toState;
	private Condition<D> condition;
	
	public Optional<Condition<D>> getCondition() {
		return Optional.ofNullable(condition);
	}
	
}
