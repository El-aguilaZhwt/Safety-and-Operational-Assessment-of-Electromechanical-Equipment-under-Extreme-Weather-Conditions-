package com.xinJiangHeTian.electromechanicalEquipment;

import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xinJiangHeTian.electromechanicalEquipment.mapper")
public class ElectromechanicalEquipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectromechanicalEquipmentApplication.class, args);
	}

}
