package com.project.carsrent.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.carsrent.shared.Car;
import com.project.carsrent.shared.Order;

@RemoteServiceRelativePath("cars")
public interface GreetingService extends RemoteService {
	Integer sentCredentials(String login, String password) throws IllegalArgumentException;

	Integer sendRegisterCredentials(String login, String password, String name, String family_name)
			throws IllegalArgumentException;

	List<Car> getCarList() throws IllegalArgumentException;

	List<Order> getOrdersListByClient(int user_id) throws IllegalArgumentException;

	void addPaidOrder(int client_id, int selectedCarId, int price, String selectedHours)
			throws IllegalArgumentException;

	List<Order> getOrdersList() throws IllegalArgumentException;

}
