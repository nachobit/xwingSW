package com.xwingSW.demoCRUD.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xwingSW.demoCRUD.model.Spaceship;
import com.xwingSW.demoCRUD.repository.SpaceshipRepository;
import com.xwingSW.demoCRUD.service.SpaceshipService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {
	
	@Autowired
    private SpaceshipRepository spaceshipRepository;

	@Override
	//@Cacheable("spaceships")
	public Page<Spaceship> getAllSpaceships(Pageable pageable) {
		return spaceshipRepository.findAll(pageable);
	}

	@Override
	@Cacheable("spaceships")
	public Spaceship getSpaceshipById(Long id) {
		return spaceshipRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Spaceship not found"));
	}

	@Override
	public Page<Spaceship> searchSpaceshipsByName(String name, Pageable pageable) {
		return spaceshipRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Spaceship createSpaceship(Spaceship spaceship) {
		Spaceship savedSpaceship = spaceshipRepository.save(spaceship);
		//messageProducer.sendMessage(savedSpaceship);
		return savedSpaceship;
	}

	@Override
	@CacheEvict(value = "spaceships", allEntries = true)
	public Spaceship updateSpaceship(Long id, Spaceship newSpaceship) {
		Spaceship spaceship = getSpaceshipById(id);
		if (newSpaceship.getName() != null) {
	        spaceship.setName(newSpaceship.getName());
	    }
	    if (newSpaceship.getType() != null) {
	        spaceship.setType(newSpaceship.getType());
	    }
	    if (newSpaceship.getDescription() != null) {
	        spaceship.setDescription(newSpaceship.getDescription());
	    }
		return spaceshipRepository.save(spaceship);
	}

	@Override
	public void deleteSpaceship(Long id) {
		spaceshipRepository.deleteById(id);
	}
	
}