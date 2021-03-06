package com.tedmo.statemachine.test.functional.order.statemachine.event;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderUpdateRequested {

	private String orderId;
	private List<String> addedItems;
	private List<String> removedItems;
	
}
