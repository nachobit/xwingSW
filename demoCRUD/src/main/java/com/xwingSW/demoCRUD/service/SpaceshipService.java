package com.xwingSW.demoCRUD.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xwingSW.demoCRUD.model.Spaceship;

public interface SpaceshipService {
	
	Page<Spaceship> getAllSpaceships(Pageable pageable);
	
	Spaceship getSpaceshipById(Long id);
	
	Page<Spaceship> searchSpaceshipsByName(String name, Pageable pageable);
	
	Spaceship createSpaceship(Spaceship spaceship);
	
	Spaceship updateSpaceship(Long id, Spaceship newSpaceship);
	
	void deleteSpaceship(Long id);

}
