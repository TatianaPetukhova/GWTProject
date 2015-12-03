package com.project.carsrent.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.carsrent.client.GreetingService;
import com.project.carsrent.shared.Car;
import com.project.carsrent.shared.Order;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	DBConnection dbc = new DBConnection();

	public Integer sentCredentials(String login, String password) throws IllegalArgumentException {
		return dbc.sendCredentials(login, password);
	}

	@Override
	public Integer sendRegisterCredentials(String login, String password, String name, String family_name)
			throws IllegalArgumentException {
		return dbc.sendRegisterCredentials(login, password, name, family_name);
	}

	@Override
	public List<Car> getCarList() throws IllegalArgumentException {
		return dbc.getCarList();
	}

	@Override
	public List<Order> getOrdersListByClient(int user_id) throws IllegalArgumentException {
		return dbc.getOrdersListByClient(user_id);
	}

	@Override
	public void addPaidOrder(int client_id, int selectedCarId, int price, String selectedHours) {
		dbc.addPaidOrder(client_id, selectedCarId, price, selectedHours);

	}

	@Override
	public List<Order> getOrdersList() throws IllegalArgumentException {
		return dbc.getOrdersList();
	}
}
