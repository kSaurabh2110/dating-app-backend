package com.soulstar.userFacing;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.soulstar")
public class UserFacingApplication {
	private static final Logger logger = LogManager.getLogger(UserFacingApplication.class);
	public static void main(String[] args) {
		try {
			SpringApplication.run(UserFacingApplication.class, args);
		} catch (Exception e) {
			logger.error("Shutting down the System ... "+ ExceptionUtils.getStackTrace(e));
			System.exit(1);
		}
	}

}
