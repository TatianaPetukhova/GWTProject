package com.project.carsrent.server;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.project.carsrent.shared.Car;
import com.project.carsrent.shared.Order;

public interface OrderMapper {

	@Select("select * from order_table where client_id = #{param1}")
	List<Order> getOrdersListByClient(int param1);

	@Select("select model from car inner join order_table on (order_table.car_id = car.car_id) and (car.car_id = #{param1});")
	Car getCarModelFromOrder(int param1);

	@Insert("insert into order_table values (0, #{param1} , #{param2}, #{param3}, #{param4});")
	void addOrder(int param1, int param2, int param3, int param4);

	@Select("select * from order_table")
	List<Order> getOrdersList();

}
