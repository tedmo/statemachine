package com.tedmo.statemachine.test.functional.order.statemachine.action;

import com.tedmo.statemachine.model.Action;
import com.tedmo.statemachine.test.functional.order.model.Order;
import com.tedmo.statemachine.test.functional.order.statemachine.OrderState;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderUpdateRequested;

public class RejectOrderUpdate implements Action<OrderState, Order, OrderUpdateRequested> {

	@Override
	public void doAction(OrderState currentState, Order appCtx, OrderUpdateRequested event) {
		System.out.println("Updates cannot be accepted when in " + currentState + " state");
		
	}

}
