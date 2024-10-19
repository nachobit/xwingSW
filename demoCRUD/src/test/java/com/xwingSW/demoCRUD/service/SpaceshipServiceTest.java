package com.xwingSW.demoCRUD.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
	
    @Test
    public void testGetAllSpaceships() {
    	List<Spaceship> spaceshipList = new ArrayList<>();
    	spaceshipList.add(new Spaceship("Halcon M"));
    	spaceshipList.add(new Spaceship("Apollo 11"));
    	Page<Spaceship> page = new PageImpl<>(spaceshipList);
    	
    	Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(page);

    	Page<Spaceship> result = spaceshipService.getAllSpaceships(Pageable.unpaged());

    	assertEquals(spaceshipList.size(), result.getContent().size());
        assertEquals("Halcon M", result.getContent().get(0).getName());
    }
    
    @Test
    public void testSearchSpaceshipsByName() {
        List<Spaceship> spaceshipList = new ArrayList<>();
        spaceshipList.add(new Spaceship("Halcon M"));
    	spaceshipList.add(new Spaceship("X-Wing"));
        Page<Spaceship> page = new PageImpl<>(spaceshipList);
        
        Mockito.when(repository.findByNameContainingIgnoreCase("Wing", Pageable.unpaged())).thenReturn(page);

        Page<Spaceship> result = spaceshipService.searchSpaceshipsByName("Wing", Pageable.unpaged());

        assertEquals(2, result.getTotalElements());
        assertEquals("Halcon M", result.getContent().get(0).getName());
        assertEquals("X-Wing", result.getContent().get(1).getName());
    }
    
    @Test
    public void testCreateSpaceship() {
        Spaceship newSpaceship = new Spaceship();
        newSpaceship.setName("X-Wing");
        
        Mockito.when(repository.save(newSpaceship)).thenReturn(newSpaceship);

        Spaceship result = spaceshipService.createSpaceship(newSpaceship);

        verify(repository, times(1)).save(newSpaceship);
        assertEquals("X-Wing", result.getName());
    }
    
    @Test
    public void testUpdateSpaceship() {
        Spaceship existingSpaceship = new Spaceship("X-Wing");
        Spaceship updatedSpaceship = new Spaceship("X-Wing v2");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existingSpaceship));
        Mockito.when(repository.save(existingSpaceship)).thenReturn(updatedSpaceship);

        Spaceship result = spaceshipService.updateSpaceship(1L, updatedSpaceship);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existingSpaceship);
        assertEquals("X-Wing v2", result.getName());
    }
    
    @Test
    public void testDeleteSpaceship() {
        long spaceshipId = 1L;

        spaceshipService.deleteSpaceship(spaceshipId);

        verify(repository, times(1)).deleteById(spaceshipId);
    }
}
