package com.tedmo.statemachine.test.functional.order.statemachine.action;

import com.tedmo.statemachine.model.Action;
import com.tedmo.statemachine.test.functional.order.model.Order;
import com.tedmo.statemachine.test.functional.order.statemachine.OrderState;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderUpdateRequested;

public class UpdateOrder implements Action<OrderState, Order, OrderUpdateRequested>{

	@Override
	public void doAction(OrderState currentState, Order appCtx, OrderUpdateRequested event) {
		
		System.out.println("Updating order ID: " + event.getOrderId());
		
		System.out.println("Removing items: " + event.getRemovedItems());
		
		event.getRemovedItems().forEach(removedItem -> appCtx.getItems().remove(removedItem));
		
		System.out.println("Adding items: " + event.getAddedItems());
		event.getAddedItems().forEach(addedItems -> appCtx.getItems().add(addedItems));
		
	}

}
