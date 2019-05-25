package com.tedmo.statemachine.test.functional.order.statemachine.action;

import com.tedmo.statemachine.model.Action;
import com.tedmo.statemachine.test.functional.order.model.Order;
import com.tedmo.statemachine.test.functional.order.statemachine.OrderState;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderShipped;

public class SendShippingNotification implements Action<OrderState, Order, OrderShipped> {

	@Override
	public void doAction(OrderState currentState, Order appCtx, OrderShipped event) {
		System.out.println("Sending shipping notification for order ID: " + appCtx.getOrderId());
	}

}
