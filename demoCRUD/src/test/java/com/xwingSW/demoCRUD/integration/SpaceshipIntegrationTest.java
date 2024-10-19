package com.xwingSW.demoCRUD.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.xwingSW.demoCRUD.controller.SpaceshipController;
import com.xwingSW.demoCRUD.model.Spaceship;
import com.xwingSW.demoCRUD.service.RabbitMQSender;
import com.xwingSW.demoCRUD.service.SpaceshipService;

@WebMvcTest(SpaceshipController.class)
@AutoConfigureMockMvc
public class SpaceshipIntegrationTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceshipService spaceshipService;
    
    @MockBean
    private RabbitMQSender rabbitMQSender;
	
	@Test
	@WithMockUser(username = "user", roles = { "USER" })
    public void testGetAllSpaceships() throws Exception {
        Page<Spaceship> spaceships = new PageImpl<>(Collections.emptyList());
        when(spaceshipService.getAllSpaceships(any(Pageable.class))).thenReturn(spaceships);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
	@WithMockUser(username = "user", roles = { "USER" })
    public void testGetSpaceshipById() throws Exception {
        Spaceship spaceship = new Spaceship("X-Wing");
        when(spaceshipService.getSpaceshipById(1L)).thenReturn(spaceship);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships/1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
	@WithMockUser(username = "user", roles = { "USER" })
    public void testGetSpaceshipSearch() throws Exception {
	    String nameParam = "X-Wing";
	    Pageable pageable = PageRequest.of(0, 10);
	    List<Spaceship> spaceshipsList = Arrays.asList(new Spaceship("X-Wing"), new Spaceship("Y-Wing"));
	    Page<Spaceship> spaceshipsPage = new PageImpl<>(spaceshipsList, pageable, spaceshipsList.size());

		when(spaceshipService.searchSpaceshipsByName(nameParam, pageable)).thenReturn(spaceshipsPage);
        
		mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships/search")
			 .param("nameParam", nameParam)
	         .param("page", "0")
	         .param("size", "10"))
			.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
	
}