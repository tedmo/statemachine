package com.tedmo.statemachine.test.functional.order;

import static com.tedmo.statemachine.test.functional.order.statemachine.OrderState.DELIVERED;
import static com.tedmo.statemachine.test.functional.order.statemachine.OrderState.INIT;
import static com.tedmo.statemachine.test.functional.order.statemachine.OrderState.PROCESSING;
import static com.tedmo.statemachine.test.functional.order.statemachine.OrderState.SHIPPED;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.tedmo.statemachine.StateMachine;
import com.tedmo.statemachine.model.StateMachineModel;
import com.tedmo.statemachine.model.builder.StateMachineModelBuilder;
import com.tedmo.statemachine.test.functional.order.model.Order;
import com.tedmo.statemachine.test.functional.order.statemachine.OrderState;
import com.tedmo.statemachine.test.functional.order.statemachine.action.CreateOrder;
import com.tedmo.statemachine.test.functional.order.statemachine.action.RejectOrderUpdate;
import com.tedmo.statemachine.test.functional.order.statemachine.action.SendDeliveryNotification;
import com.tedmo.statemachine.test.functional.order.statemachine.action.SendShippingNotification;
import com.tedmo.statemachine.test.functional.order.statemachine.action.UpdateOrder;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderDelivered;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderRequested;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderShipped;
import com.tedmo.statemachine.test.functional.order.statemachine.event.OrderUpdateRequested;

public class OrderExample {
	
	private CreateOrder createOrder = new CreateOrder();
	private UpdateOrder updateOrder = new UpdateOrder();
	private RejectOrderUpdate rejectOrderUpdate = new RejectOrderUpdate();
	private SendShippingNotification sendShippingNotification = new SendShippingNotification();
	private SendDeliveryNotification sendDeliveryNotification = new SendDeliveryNotification();
	
	private StateMachine<OrderState, Order> stateMachine;
	private Order order;
	
	private String orderId = UUID.randomUUID().toString();
	
	@Test
	public void test() {
		
		order = new Order();
		stateMachine = new StateMachine<>(order, buildStateMachineModel());
		
		OrderRequested orderRequest = OrderRequested.builder()
				.orderId(orderId)
				.items(Arrays.asList("Item1", "Item2"))
				.build();
		
		stateMachine.sendEvent(orderRequest);
		System.out.println("current state = " + stateMachine.getCurrentState());
		
		
		OrderUpdateRequested orderUpdateRequested = OrderUpdateRequested.builder()
				.orderId(orderId)
				.addedItems(Arrays.asList("Item3"))
				.removedItems(Arrays.asList("Item1"))
				.build();
		
		stateMachine.sendEvent(orderUpdateRequested);
		System.out.println("current state = " + stateMachine.getCurrentState());
		
		OrderShipped orderShipped = OrderShipped.builder()
				.orderId(orderId)
				.build();
		
		stateMachine.sendEvent(orderShipped);
		System.out.println("current state = " + stateMachine.getCurrentState());
		
		stateMachine.sendEvent(orderUpdateRequested);
		System.out.println("current state = " + stateMachine.getCurrentState());
		
		OrderDelivered orderDelivered = OrderDelivered.builder()
			.orderId(orderId)
			.build();
		
		stateMachine.sendEvent(orderDelivered);
		System.out.println("current state = " + stateMachine.getCurrentState());
		
				
	}
	
	private StateMachineModel<OrderState, Order> buildStateMachineModel() {
		return new StateMachineModelBuilder<OrderState, Order>()
			.states(INIT, PROCESSING, SHIPPED, DELIVERED)
			.initialState(INIT)
			.transition()
				.from(INIT)
				.to(PROCESSING)
				.on(OrderRequested.class)
				.withoutCondition()
			.transition()
				.from(PROCESSING)
				.to(SHIPPED)
				.on(OrderShipped.class)
				.withoutCondition()
			.transition()
				.from(SHIPPED)
				.to(DELIVERED)
				.on(OrderDelivered.class)
				.withoutCondition()
			.action()
				.in(INIT)
				.on(OrderRequested.class)
				.doAction(createOrder)
			.action()
				.in(PROCESSING)
				.on(OrderUpdateRequested.class)
				.doAction(updateOrder)
			.action()
				.in(SHIPPED)
				.on(OrderUpdateRequested.class)
				.doAction(rejectOrderUpdate)
			.action()
				.entering(SHIPPED)
				.on(OrderShipped.class)
				.doAction(sendShippingNotification)
			.action()
				.entering(DELIVERED)
				.on(OrderDelivered.class)
				.doAction(sendDeliveryNotification)
			.build();
	}

}
