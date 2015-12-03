package com.project.carsrent.shared;

import java.io.Serializable;

public class Order implements Serializable {

	private int order_id;
	private int client_id;
	private int car_id;
	private int hour;
	private int price;

	public Order() {
		super();
	}

	public Order(int order_id) {
		this.order_id = order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getOrder_id() {
		return this.order_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public int getClient_id() {
		return this.client_id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}

	public int getCar_id() {
		return this.car_id;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getHour() {
		return this.hour;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return this.price;
	}

}
