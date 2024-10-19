package com.xwingSW.demoCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xwingSW.demoCRUD.model.Spaceship;
import com.xwingSW.demoCRUD.service.SpaceshipService;


@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {
	
	@Autowired
	private SpaceshipService spaceshipService;

    @GetMapping
    public ResponseEntity<Page<Spaceship>> getAllSpaceships(Pageable pageable) {
    	return ResponseEntity.ok(spaceshipService.getAllSpaceships(pageable));
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Long id) {
    	return ResponseEntity.ok(spaceshipService.getSpaceshipById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Spaceship>> searchSpaceshipsByName(@RequestParam String nameParam, Pageable pageable) {
    	 return ResponseEntity.ok(spaceshipService.searchSpaceshipsByName(nameParam, pageable));
    }

    @PostMapping
    public ResponseEntity<Spaceship> createSpaceship(@RequestBody Spaceship spaceship) {
    	return new ResponseEntity<>(spaceshipService.createSpaceship(spaceship), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> updateSpaceship(@PathVariable Long id, @RequestBody Spaceship spaceship) {
    	return ResponseEntity.ok(spaceshipService.updateSpaceship(id, spaceship));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpaceship(@PathVariable Long id) {
    	return ResponseEntity.noContent().build();
    }
}
