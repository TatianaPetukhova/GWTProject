package com.project.carsrent.server;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.project.carsrent.shared.Car;

public interface CarMapper {

	@Select("select * from car")
	List<Car> getCarList();

}
