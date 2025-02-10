package com.usth_connect.vpn_server_backend_usth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class UsthConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsthConnectApplication.class, args);
	}

}
