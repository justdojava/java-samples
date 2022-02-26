package com.ziyou.algo.generator.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ziyou.algo.generator.demo.mapper")
public class DemoGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoGeneratorApplication.class, args);
	}

}
