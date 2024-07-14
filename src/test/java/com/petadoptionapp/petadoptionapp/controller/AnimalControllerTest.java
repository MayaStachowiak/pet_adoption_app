package com.petadoptionapp.petadoptionapp.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class AnimalControllerTest {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        dynamicPropertyRegistry.add("spring.sql.init.mode", () -> "never");
    }


    @Test
    @Transactional
    void shouldSaveAnimal() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/animal_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        mockMvc.perform(post("/api/animals")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animal.name").value("Bella"))
                .andExpect(jsonPath("$.animal.age").value(3))
                .andExpect(jsonPath("$.animal.type").value("Dog"))
                .andExpect(jsonPath("$.animal.color").value("Brown"))
                .andExpect(jsonPath("$.animal.status").value("Available"))
                .andExpect(jsonPath("$.animal.shortDescription").value("Friendly dog"))
                .andExpect(jsonPath("$.animal.id").exists());
    }


    @Test
    @Transactional
    void shouldGetAnimalById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/animal_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        // Save the animal
        mockMvc.perform(post("/api/animals")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Get the animal by ID
        mockMvc.perform(get("/api/animals/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bella"))
                .andExpect(jsonPath("$.age").value(3))
                .andExpect(jsonPath("$.type").value("Dog"))
                .andExpect(jsonPath("$.color").value("Brown"))
                .andExpect(jsonPath("$.status").value("Available"))
                .andExpect(jsonPath("$.shortDescription").value("Friendly dog"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Transactional
    void shouldReturnNotFoundForNonExistentAnimal() throws Exception {
        mockMvc.perform(get("/api/animals/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void shouldDeleteAnimalById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/animal_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        // Save the animal
        mockMvc.perform(post("/api/animals")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Delete the animal
        mockMvc.perform(delete("/api/animals/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    @Transactional
    @Sql("classpath:sql/clear_data.sql")
    void shouldUpdateAnimalById() throws Exception {
        Resource createResource = resourceLoader.getResource("classpath:json/animal_create_request_ok.json");
        String createRequestBody = IOUtils.toString(createResource.getInputStream());

        // Save the animal
        mockMvc.perform(post("/api/animals")
                        .content(createRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Resource updateResource = resourceLoader.getResource("classpath:json/animal_update_request_ok.json");
        String updateRequestBody = IOUtils.toString(updateResource.getInputStream());

        // Update the animal
        mockMvc.perform(patch("/api/animals/1")
                        .content(updateRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BellaUpdated"))
                .andExpect(jsonPath("$.age").value(4))
                .andExpect(jsonPath("$.type").value("Dog"))
                .andExpect(jsonPath("$.color").value("Black"))
                .andExpect(jsonPath("$.status").value("Adopted"))
                .andExpect(jsonPath("$.shortDescription").value("Friendly and energetic dog"))
                .andExpect(jsonPath("$.id").exists());
    }


    @Test
    @Transactional
    @Sql("classpath:sql/animal_data.sql")
    void shouldGetAnimalPage() throws Exception {
        mockMvc.perform(get("/api/animals/all")
                        .queryParam("page", "0")
                        .queryParam("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value("3"))
                .andExpect(jsonPath("$.size").value("2"))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }


}
