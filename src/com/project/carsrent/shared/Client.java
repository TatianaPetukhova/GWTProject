package com.project.carsrent.shared;

import java.io.Serializable;

public class Client implements Serializable {

	private int client_id;
	private String login;
	private String password;
	private String name;
	private String family_name;

	public Client() {
		super();
	}

	public Client(int id) {
		this.client_id = id;
	}

	public Client(String name, String family_name) {
		this.name = name;
		this.family_name = family_name;
	}

	public void setClient_id(int id) {
		this.client_id = id;
	}

	public int getClient_id() {
		return this.client_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getFamily_name() {
		return this.family_name;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return this.login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
}
