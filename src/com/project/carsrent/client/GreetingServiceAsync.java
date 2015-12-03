package com.project.carsrent.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.carsrent.shared.Car;
import com.project.carsrent.shared.Order;

public interface GreetingServiceAsync {

	void sentCredentials(String login, String password, AsyncCallback<Integer> callback)
			throws IllegalArgumentException;

	void sendRegisterCredentials(String login, String password, String name, String family_name,
			AsyncCallback<Integer> callback) throws IllegalArgumentException;

	void getCarList(AsyncCallback<List<Car>> asyncCallback) throws IllegalArgumentException;

	void getOrdersListByClient(int user_id, AsyncCallback<List<Order>> asyncCallback) throws IllegalArgumentException;

	void addPaidOrder(int client_id, int selectedCarId, int price, String selectedHours,
			AsyncCallback<Void> asyncCallback) throws IllegalArgumentException;

	void getOrdersList(AsyncCallback<List<Order>> asyncCallback) throws IllegalArgumentException;
}
