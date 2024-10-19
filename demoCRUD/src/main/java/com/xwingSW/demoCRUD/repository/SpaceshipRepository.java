package com.xwingSW.demoCRUD.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xwingSW.demoCRUD.model.Spaceship;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
	
	Page<Spaceship> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
