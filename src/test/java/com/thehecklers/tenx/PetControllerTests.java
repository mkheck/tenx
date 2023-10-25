package com.thehecklers.tenx;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(PetController.class)
public class PetControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetRepository repo;

    @BeforeEach
    void setUp() {
        when(repo.findAll())
            .thenReturn(List.of(new Pet("dog"),
                                new Pet("cat"),
                                new Pet("tribble")));

        when(repo.findById(1L))
            .thenReturn(Optional.of(new Pet("hamster")));
    }

    @Test
    void testGetPetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pets/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("hamster"));
    }

    @Test
    void testGetPets() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("dog"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("cat"));
    }
}
