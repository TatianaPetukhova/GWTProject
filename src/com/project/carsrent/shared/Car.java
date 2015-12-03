package com.project.carsrent.shared;

import java.io.Serializable;

public class Car implements Serializable {

	private int car_id;
	private String model;
	private int year;
	private int price;

	public Car() {
		super();
	}

	public Car(String model) {
		this.model = model;
	}

	public Car(int id, String model, int year, int price) {
		this.car_id = id;
		this.model = model;
		this.year = year;
		this.price = price;
	}

	public String toString() {
		return "Model: " + this.model + ", Release year: " + this.year + ", Price per hour: " + this.price + " y.e.";
	}

	public void setId(int id) {
		this.car_id = id;
	}

	public int getId() {
		return this.car_id;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return this.model;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return this.year;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return this.price;
	}

}
