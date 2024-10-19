package com.xwingSW.demoCRUD.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution(* com.example.controller.SpaceshipController.getSpaceshipById(..)) && args(id)")
	public void logIfNegativeId(Long id) {
		if (id < 0) {
			logger.warn("Attempt to access a spaceship with a negative ID: " + id);
		}
	}
}
