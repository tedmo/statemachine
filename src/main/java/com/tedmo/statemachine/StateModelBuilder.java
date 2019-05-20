package com.tedmo.statemachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateModelBuilder<S, D> {

	private Map<S, Map<Class<?>, List<Transition<S, D>>>> transitions = new HashMap<>();
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onEventActions = new HashMap<>();
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onEnterActions = new HashMap<>();
	private Map<S, Map<Class<?>, Action<S, D, ?>>> onExitActions = new HashMap<>();
	
	
}
