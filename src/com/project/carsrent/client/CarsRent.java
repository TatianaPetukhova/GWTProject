package com.project.carsrent.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.project.carsrent.shared.Car;
import com.project.carsrent.shared.FieldVerifier;
import com.project.carsrent.shared.Order;

public class CarsRent implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private int client_id;
	private String user_name;
	private boolean isAdmin;

	public void onModuleLoad() {
		showLoginPage();
	}

	private void showLoginPage() {

		final FlexTable loginTable = new FlexTable();
		final TextBox nameField = new TextBox();
		final PasswordTextBox passwordField = new PasswordTextBox();
		final Button loginButton = new Button("Login");
		final Button registerButton = new Button("Register New Account");
		final Label errorLabel = new Label();

		RootPanel.get().clear();

		nameField.setFocus(true);
		nameField.selectAll();
		loginButton.addStyleName("loginButtonStyle");
		registerButton.addStyleName("registerButtonStyle");
		errorLabel.addStyleName("serverResponseLabelError");
		errorLabel.setText("");

		loginTable.setText(0, 0, "Login:");
		loginTable.setWidget(0, 1, nameField);
		loginTable.setText(1, 0, "Password");
		loginTable.setWidget(1, 1, passwordField);
		loginTable.setWidget(2, 1, loginButton);
		loginTable.setWidget(4, 1, registerButton);
		loginTable.setWidget(5, 1, errorLabel);

		RootPanel.get().add(loginTable);

		class MyLoginHandler implements ClickHandler, KeyUpHandler {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					loginButton.setEnabled(false);
					sendCredentialsToServer();
				}
			}

			@Override
			public void onClick(ClickEvent event) {
				loginButton.setEnabled(false);
				sendCredentialsToServer();
			}

			private void sendCredentialsToServer() {

				final String inputLogin = nameField.getText();
				final String inputPassword = passwordField.getText();
				if (!FieldVerifier.isValidName(inputLogin)) {
					loginButton.setEnabled(true);
					errorLabel.setText("Wrong credentials");
					return;
				}
				if (!FieldVerifier.isValidName(inputPassword)) {
					loginButton.setEnabled(true);
					errorLabel.setText("Wrong credentials");
					return;
				}

				greetingService.sentCredentials(inputLogin, inputPassword, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						loginButton.setEnabled(true);
						String serverErrorMessage = caught.getMessage().toString();
						errorLabel.setText(serverErrorMessage);
					}

					public void onSuccess(Integer result) {
						if (result == 0) {
							errorLabel.setText("Wrong credentials");
							loginButton.setEnabled(true);
						} else {
							client_id = result;
							user_name = inputLogin;
							if (client_id == 2) {
								isAdmin = true;
								showAdminPage();
							} else {
								showCarsPage();
							}
						}
					}
				});
			}
		}

		registerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showRegisterPage();
			}
		});

		nameField.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				nameField.setText("");
			}
		});

		passwordField.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				passwordField.setText("");
			}
		});

		MyLoginHandler myLoginHandler = new MyLoginHandler();
		loginButton.addClickHandler(myLoginHandler);

	}

	private void showRegisterPage() {

		final FlexTable registerTable = new FlexTable();
		final TextBox loginField = new TextBox();
		final TextBox nameField = new TextBox();
		final TextBox familyNameField = new TextBox();
		final PasswordTextBox passwordField = new PasswordTextBox();
		final PasswordTextBox passwordConfirmField = new PasswordTextBox();
		final Button loginButton = new Button("Login");
		final Button registerButton = new Button("Submit registration");
		final Label errorLabel = new Label();
		final Label welcomeRegisterMessage = new Label("Please fill in all fields:");

		RootPanel.get().clear();

		loginField.setFocus(true);
		loginField.selectAll();
		loginButton.addStyleName("loginButtonStyle");
		registerButton.addStyleName("registerButtonStyle");
		errorLabel.addStyleName("serverResponseLabelError");
		errorLabel.setText("");

		registerTable.setWidget(0, 1, welcomeRegisterMessage);
		registerTable.setText(1, 0, "Login:");
		registerTable.setWidget(1, 1, loginField);
		registerTable.setText(2, 0, "Name:");
		registerTable.setWidget(2, 1, nameField);
		registerTable.setText(3, 0, "Family Name:");
		registerTable.setWidget(3, 1, familyNameField);
		registerTable.setText(4, 0, "Password:");
		registerTable.setWidget(4, 1, passwordField);
		registerTable.setText(5, 0, "Confirm password:");
		registerTable.setWidget(5, 1, passwordConfirmField);
		registerTable.setWidget(6, 1, registerButton);
		registerTable.setWidget(7, 1, loginButton);
		registerTable.setWidget(8, 1, errorLabel);

		RootPanel.get().add(registerTable);

		class MyRegisterHandler implements ClickHandler, KeyUpHandler {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

					sendRegisterCredentialsToServer();
				}
			}

			@Override
			public void onClick(ClickEvent event) {
				sendRegisterCredentialsToServer();
			}

			private void sendRegisterCredentialsToServer() {
				registerButton.setEnabled(false);
				final String inputLogin = loginField.getText();
				final String inputName = nameField.getText();
				final String inputFamilyName = familyNameField.getText();
				final String inputPassword = passwordField.getText();
				final String inputConfirmPassword = passwordConfirmField.getText();

				if (!FieldVerifier.isValidName(inputLogin)) {
					errorLabel.setVisible(true);
					errorLabel.setText("Login should contain at least four characters");
					registerButton.setEnabled(true);
					return;
				}

				if (inputName.length() == 0 || inputName == null) {
					errorLabel.setVisible(true);
					errorLabel.setText("Please enter your name");
					registerButton.setEnabled(true);
					return;
				}

				if (inputFamilyName.length() == 0 || inputFamilyName == null) {
					errorLabel.setVisible(true);
					errorLabel.setText("Please enter your family name");
					registerButton.setEnabled(true);
					return;
				}

				if (!FieldVerifier.isValidName(inputPassword)) {
					errorLabel.setVisible(true);
					errorLabel.setText("Password should contain at least four characters");
					registerButton.setEnabled(true);
					return;
				}

				if (!inputPassword.equals(inputConfirmPassword)) {
					errorLabel.setVisible(true);
					errorLabel.setText("Confirm password is incorrect");
					registerButton.setEnabled(true);
					return;
				}

				greetingService.sendRegisterCredentials(inputLogin, inputPassword, inputName, inputFamilyName,
						new AsyncCallback<Integer>() {
							public void onFailure(Throwable caught) {
								registerButton.setEnabled(true);
								String serverErrorMessage = caught.getMessage().toString();
								errorLabel.setText(serverErrorMessage);
							}

							public void onSuccess(Integer result) {
								if (0 == result) {
									registerButton.setEnabled(true);
									errorLabel.setText("Login is already used");
								}
								if (-1 == result) {
									registerButton.setEnabled(true);
									errorLabel.setText("Please try again");
								} else {
									System.out.println("success");
									user_name = inputLogin;
									client_id = result;
									isAdmin = false;
									showCarsPage();
								}
							}
						});
			}
		}

		MyRegisterHandler myRegisterHandler = new MyRegisterHandler();
		registerButton.addClickHandler(myRegisterHandler);

		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showLoginPage();
			}
		});

		nameField.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				nameField.setText("");
			}
		});

		familyNameField.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				familyNameField.setText("");
			}
		});

		passwordField.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				passwordField.setText("");
			}
		});

		loginField.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loginField.setText("");
			}
		});

		passwordConfirmField.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				passwordConfirmField.setText("");
			}
		});

	}

	private void showCarsPage() {
		RootPanel.get().clear();

		final ListBox carBox = new ListBox();
		final ListBox hoursBox = new ListBox();
		final List<Car> carList = new ArrayList<>();
		final Button orderButton = new Button("Order selected car");
		final Label errorLabel = new Label();
		final Label greetLabel = new Label("Hello, " + user_name + "Please select a car:");
		final Button seeOrdersButton = new Button("Show ordered Cars");
		final Button logoutButton = new Button("Logout");

		FlexTable table = new FlexTable();
		table.setWidget(0, 0, greetLabel);
		table.setText(1, 0, "Model");
		table.setWidget(1, 1, carBox);
		table.setText(2, 0, "Hours");
		table.setWidget(2, 1, hoursBox);
		table.setWidget(3, 1, orderButton);
		table.setWidget(4, 1, seeOrdersButton);
		table.setWidget(5, 1, logoutButton);
		table.setWidget(6, 1, errorLabel);
		RootPanel.get().add(table);

		for (int i = 1; i < 10; i++) {
			hoursBox.addItem(String.valueOf(i));
		}

		greetingService.getCarList(new AsyncCallback<List<Car>>() {

			@Override
			public void onSuccess(List<Car> result) {
				carList.clear();
				for (Car car : result) {
					carList.add(car);
					carBox.addItem(car.toString());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				String serverErrorMessage = caught.getMessage().toString();
				errorLabel.setText(serverErrorMessage);
			}
		});

		orderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Car selectedCar = carList.get(carBox.getSelectedIndex());
				String selectedHours = hoursBox.getSelectedItemText();
				showOrderPage(selectedCar, selectedHours);
			}
		});

		seeOrdersButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showPaidOrdersPage();
			}
		});

		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				client_id = 0;
				showLoginPage();
			}
		});

		return;

	}

	private void showOrderPage(Car selectedCar, String selectedHours) {
		RootPanel.get().clear();
		final Car temporaryCar = selectedCar;
		final String temporarySelectedHours = selectedHours;

		final int hours = Integer.parseInt(selectedHours);
		final int price = selectedCar.getPrice();
		final int temporaryCarId = selectedCar.getId();

		final FlexTable orderTable = new FlexTable();
		final Label orderLabel = new Label(
				"Hello, " + user_name + "! Your order: " + selectedCar.toString() + temporaryCar.getId());
		final Label costLabel = new Label("The order costs: " + hours * price);
		final Button payButton = new Button("Confirm and pay");
		final Button cancelButton = new Button("Cancel");
		final Button logoutButton = new Button("Logout");
		final Label errorLabel = new Label("");
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("You have successfully paid for the order!");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		closeButton.getElement().setId("closeButton");
		dialogBox.setWidget(closeButton);
		final int temporaryUserId = client_id;

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				showPaidOrdersPage();
			}
		});

		orderTable.setWidget(0, 0, orderLabel);
		orderTable.setWidget(1, 0, costLabel);
		orderTable.setWidget(2, 0, payButton);
		orderTable.setWidget(2, 1, cancelButton);
		orderTable.setWidget(3, 0, logoutButton);
		orderTable.setWidget(4, 0, errorLabel);
		RootPanel.get().add(orderTable);

		payButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				payButton.setEnabled(false);

				greetingService.addPaidOrder(temporaryUserId, temporaryCarId, price, temporarySelectedHours,
						new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						String serverErrorMessage = caught.getMessage().toString();
						errorLabel.setText(serverErrorMessage);
						payButton.setEnabled(true);
					}

					public void onSuccess(Void result) {
						System.out.println("success");
						dialogBox.center();
						closeButton.setFocus(true);

					}
				});
			}
		});

		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showCarsPage();
			}
		});

		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				client_id = 0;
				showLoginPage();
			}
		});

	}

	private void showPaidOrdersPage() {
		RootPanel.get().clear();

		final FlexTable ordersFlexTable = new FlexTable();
		final CellTable<Order> ordersTable = new CellTable<>();
		final Button orderNewCarButton = new Button("Order New Car");
		final Label errorLabel = new Label();
		final Label greetLabel = new Label("Hello, " + user_name + "Your orders:");
		final Button logoutButton = new Button("Logout");

		TextColumn<Order> orderIdColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getOrder_id());
			}
		};

		TextColumn<Order> hourColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getHour());
			}
		};

		TextColumn<Order> priceColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getPrice());
			}
		};

		ordersTable.addColumn(orderIdColumn, "Order Number");
		ordersTable.addColumn(hourColumn, "Hours");
		ordersTable.addColumn(priceColumn, "Price");

		ListDataProvider<Order> dataProvider = new ListDataProvider<Order>();
		dataProvider.addDataDisplay(ordersTable);

		final List<Order> listFromProvider = dataProvider.getList();

		ordersFlexTable.setWidget(0, 0, greetLabel);
		ordersFlexTable.setWidget(1, 0, ordersTable);
		ordersFlexTable.setWidget(2, 0, orderNewCarButton);
		ordersFlexTable.setWidget(3, 0, logoutButton);
		ordersFlexTable.setWidget(4, 0, errorLabel);
		RootPanel.get().add(ordersFlexTable);

		greetingService.getOrdersListByClient(client_id, new AsyncCallback<List<Order>>() {

			@Override
			public void onSuccess(List<Order> result) {
				if (null == result) {
					greetLabel.setText("You have not ordered any cars yet");
					return;
				} else {
					listFromProvider.clear();
					for (Order order : result) {
						listFromProvider.add(order);
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				String serverErrorMessage = caught.getMessage().toString();
				errorLabel.setText(serverErrorMessage);
			}
		});

		orderNewCarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showCarsPage();
			}
		});

		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				client_id = 0;
				showLoginPage();
			}
		});

	}

	private void showAdminPage() {

		RootPanel.get().clear();

		final FlexTable ordersFlexTable = new FlexTable();
		final CellTable<Order> ordersTable = new CellTable<>();
		final Label errorLabel = new Label();
		final Label greetLabel = new Label("Hello, " + user_name + "! The list of paid orders:");
		final Button logoutButton = new Button("Logout");
		final Button confirmButton = new Button("Confirm that the car was returned");

		TextColumn<Order> orderIdColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getOrder_id());
			}
		};

		TextColumn<Order> clientIdColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getClient_id());
			}
		};

		TextColumn<Order> carIdColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getCar_id());
			}
		};

		TextColumn<Order> hourColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getHour());
			}
		};

		TextColumn<Order> priceColumn = new TextColumn<Order>() {
			@Override
			public String getValue(Order order) {
				return String.valueOf(order.getPrice());
			}
		};

		ordersTable.addColumn(orderIdColumn, "Order Number");
		ordersTable.addColumn(clientIdColumn, "Client Id");
		ordersTable.addColumn(carIdColumn, "Car Id");
		ordersTable.addColumn(hourColumn, "Hours");
		ordersTable.addColumn(priceColumn, "Price");

		final SingleSelectionModel<Order> selectionModel = new SingleSelectionModel<Order>();
		ordersTable.setSelectionModel(selectionModel);

		ListDataProvider<Order> dataProvider = new ListDataProvider<Order>();
		dataProvider.addDataDisplay(ordersTable);

		final List<Order> listFromProvider = dataProvider.getList();

		ordersFlexTable.setWidget(0, 0, greetLabel);
		ordersFlexTable.setWidget(1, 1, ordersTable);
		ordersFlexTable.setWidget(2, 1, confirmButton);
		ordersFlexTable.setWidget(3, 1, errorLabel);
		ordersFlexTable.setWidget(4, 1, logoutButton);

		RootPanel.get().add(ordersFlexTable);

		greetingService.getOrdersList(new AsyncCallback<List<Order>>() {

			@Override
			public void onSuccess(List<Order> result) {
				if (null == result) {
					return;
				} else {
					listFromProvider.clear();
					for (Order order : result) {
						listFromProvider.add(order);
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				String serverErrorMessage = caught.getMessage().toString();
				errorLabel.setText(serverErrorMessage);
			}
		});

		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				client_id = 0;
				showLoginPage();
			}
		});

	}
}
