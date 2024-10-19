package com.xwingSW.demoCRUD.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.xwingSW.demoCRUD.model.Spaceship;
import com.xwingSW.demoCRUD.service.SpaceshipService;

@ExtendWith(MockitoExtension.class)
public class SpaceshipControllerTest {


	@Mock
	private SpaceshipService spaceshipService;
	
	@InjectMocks
	private SpaceshipController spaceshipController;

	@Test
	public void testGetAllSpaceships() throws Exception {

		Page<Spaceship> spaceships = new PageImpl<>(List.of(
				new Spaceship("Falcon 9"), 
				new Spaceship("Apollo 11")));

		when(spaceshipService.getAllSpaceships(any(Pageable.class))).thenReturn(spaceships);
		
		ResponseEntity<Page<Spaceship>> response = spaceshipController.getAllSpaceships(Pageable.unpaged());

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
}