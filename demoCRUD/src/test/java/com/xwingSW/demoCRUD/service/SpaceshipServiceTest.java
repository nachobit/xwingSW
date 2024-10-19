package com.xwingSW.demoCRUD.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.xwingSW.demoCRUD.model.Spaceship;
import com.xwingSW.demoCRUD.repository.SpaceshipRepository;
import com.xwingSW.demoCRUD.service.impl.SpaceshipServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SpaceshipServiceTest {
	
    @InjectMocks
    private SpaceshipServiceImpl spaceshipService;

	@Mock
	private SpaceshipRepository repository;

	@Test
	public void testGetSpaceshipById() {
		Long id = 1L;
		Spaceship spaceship = new Spaceship();
		spaceship.setId(id);
		spaceship.setName("X-Wing");

		Mockito.when(repository.findById(id)).thenReturn(Optional.of(spaceship));

		Spaceship found = spaceshipService.getSpaceshipById(id);

		assertEquals("X-Wing", found.getName());
	}
}
