package com.project.carsrent.server;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.project.carsrent.shared.Car;
import com.project.carsrent.shared.Client;
import com.project.carsrent.shared.FieldVerifier;
import com.project.carsrent.shared.Order;

public class DBConnection {

	private static SqlSessionFactory sqlSessionFactory;

	public DBConnection() {
		try {
			sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(Resources.getResourceAsReader("com/project/carsrent/server/config.xml"));
			sqlSessionFactory.getConfiguration().addMapper(ClientMapper.class);
			sqlSessionFactory.getConfiguration().addMapper(CarMapper.class);
			sqlSessionFactory.getConfiguration().addMapper(OrderMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer sendCredentials(String login, String password) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ClientMapper mapper = session.getMapper(ClientMapper.class);
			Client client = mapper.getClient(login, password);
			if (null == client) {
				throw new IllegalArgumentException("Wrong credentials");
			}
			if (!FieldVerifier.isValidName(login)) {
				throw new IllegalArgumentException("Wrong credentials");
			}
			if (!FieldVerifier.isValidName(password)) {
				throw new IllegalArgumentException("Wrong credentials");
			}
			return client.getClient_id();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return 0;

	}

	public Integer sendRegisterCredentials(String login, String password, String name, String family_name) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ClientMapper mapper = session.getMapper(ClientMapper.class);
			List<Client> clientList = mapper.getClientList();
			if (!FieldVerifier.isValidName(login)) {
				throw new IllegalArgumentException("Login should contain at least four characters");
			}
			if (name.length() == 0) {
				throw new IllegalArgumentException("Please enter your name");
			}

			if (family_name.length() == 0) {
				throw new IllegalArgumentException("Please enter your family name");
			}

			if (!FieldVerifier.isValidName(password)) {
				throw new IllegalArgumentException("Password should contain at least four characters");
			}
			for (Client client : clientList) {
				System.out.println(client.getLogin());
				if (client.getLogin().equals(login)) {
					return 0;

				}
			}
			System.out.println("ready");
			mapper.addClient(login, password, name, family_name);
			session.commit();
			return (mapper.getClient(login, password)).getClient_id();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("last step");
		return -1;
	}

	public List<Car> getCarList() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			CarMapper mapper = session.getMapper(CarMapper.class);
			System.out.println("Car list is got");
			return mapper.getCarList();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public List<Order> getOrdersListByClient(int user_id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			System.out.println("Orders list is starting");
			return mapper.getOrdersListByClient(user_id);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public void addPaidOrder(int client_id, int selectedCarId, int price, String selectedHours) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			int hours = Integer.valueOf(selectedHours);
			int orderPrice = Integer.valueOf(selectedHours) * price;
			System.out.println(selectedCarId);
			mapper.addOrder(client_id, selectedCarId, hours, orderPrice);
			session.commit();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return;
	}

	public List<Order> getOrdersList() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			return mapper.getOrdersList();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

}
