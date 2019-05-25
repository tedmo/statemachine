package com.tedmo.statemachine.test.functional.order.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {

	private String orderId;
	private List<String> items;
	
	
}
