package com.project.carsrent.server;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.project.carsrent.shared.Client;

public interface ClientMapper {

	@Select("select * from client where login = #{param1} and password = #{param2}")
	Client getClient(String param1, String param2);

	@Insert("insert into client (login, password, name, family_name) values (#{param1}, #{param2}, #{param3}, #{param4})")
	void addClient(String param1, String param2, String param3, String param4);

	@Select("select * from client")
	List<Client> getClientList();

}
