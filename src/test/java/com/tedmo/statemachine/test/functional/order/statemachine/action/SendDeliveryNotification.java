package com.tedmo.statemachine.test.functional.order.statemachine.action;

import com.tedmo.statemachine.model.Action;
import com.tedmo.statemachine.test.functional.order.model.Order;
import com.tedmo.statemachine.test.functional.order.statemachine.OrderState;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderDelivered;

public class SendDeliveryNotification implements Action<OrderState, Order, OrderDelivered> {

	@Override
	public void doAction(OrderState currentState, Order appCtx, OrderDelivered event) {
		System.out.println("Sending delivery notification for order ID: " + appCtx.getOrderId());
	}

}
