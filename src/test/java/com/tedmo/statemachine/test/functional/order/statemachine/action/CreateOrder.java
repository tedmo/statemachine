package com.tedmo.statemachine.test.functional.order.statemachine.action;

import java.util.ArrayList;

import com.tedmo.statemachine.model.Action;
import com.tedmo.statemachine.test.functional.order.model.Order;
import com.tedmo.statemachine.test.functional.order.statemachine.OrderState;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderRequested;

public class CreateOrder implements Action<OrderState, Order, OrderRequested> {
	
	@Override
	public void doAction(OrderState currentState, Order appCtx, OrderRequested event) {
		String orderId = event.getOrderId();
		System.out.println("creating order with order ID: " + orderId);
		appCtx.setOrderId(orderId);
		System.out.println("setting items: " + event.getItems());
		appCtx.setItems(new ArrayList<>());
		event.getItems().forEach(item -> appCtx.getItems().add(item));
	}

}
