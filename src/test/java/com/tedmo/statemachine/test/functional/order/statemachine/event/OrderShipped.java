package com.tedmo.statemachine.test.functional.order.statemachine.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderShipped {
	
	private String orderId;

}
