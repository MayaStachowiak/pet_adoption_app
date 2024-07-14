package com.petadoptionapp.petadoptionapp.controller;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class AdoptionControllerTest {

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

    @BeforeEach
    public void createUserAndANimal() throws Exception {
        // Create User
        Resource userResource = resourceLoader.getResource("classpath:json/user_create_request_ok.json");
        String userRequestBody = IOUtils.toString(userResource.getInputStream(), StandardCharsets.UTF_8);

        MvcResult userResult = mockMvc.perform(post("/api/users")
                        .content(userRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String userResponseBody = userResult.getResponse().getContentAsString();
        System.out.println("User Response Body: " + userResponseBody);


        // Create Animal
        Resource animalResource = resourceLoader.getResource("classpath:json/animal_create_request_ok.json");
        String animalRequestBody = IOUtils.toString(animalResource.getInputStream(), StandardCharsets.UTF_8);

        MvcResult animalResult = mockMvc.perform(post("/api/animals")
                        .content(animalRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String animalResponseBody = animalResult.getResponse().getContentAsString();
        System.out.println("Animal Response Body: " + animalResponseBody);
    }


    @Test
    @Transactional
    void shouldSaveAdoption() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/adoption_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(requestBody);

        MvcResult mvcResult = mockMvc.perform(post("/api/adoptions")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adoption.adoptionDate").value("2024-07-10"))
                .andExpect(jsonPath("$.adoption.user.id").value(1))
                .andExpect(jsonPath("$.adoption.animal.id").value(1))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }

    @Test
    @Transactional
    void shouldGetAdoptionById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/adoption_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        mockMvc.perform(post("/api/adoptions")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adoptionDate").value("2024-07-10"))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.animal.id").value(1));
    }


    @Test
    @Transactional
    void shouldReturnNotFoundForNonExistentAdoption() throws Exception {
        mockMvc.perform(get("/api/adoptions/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void shouldDeleteAdoptionById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/adoption_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        mockMvc.perform(post("/api/adoptions")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @Sql("classpath:sql/clear_data.sql")
    void shouldUpdateAdoptionById() throws Exception {
        Resource createResource = resourceLoader.getResource("classpath:json/adoption_create_request_ok.json");
        String createRequestBody = IOUtils.toString(createResource.getInputStream());

        mockMvc.perform(post("/api/adoptions")
                        .content(createRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Resource updateResource = resourceLoader.getResource("classpath:json/adoption_update_request_ok.json");
        String updateRequestBody = IOUtils.toString(updateResource.getInputStream());

        mockMvc.perform(patch("/api/adoptions/1")
                        .content(updateRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adoptionDate").value("2024-07-11"))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.animal.id").value(1));
    }

    @Test
    @Transactional
    @Sql("classpath:sql/adoption_data.sql")
    void shouldGetAdoptionPage() throws Exception {
        mockMvc.perform(get("/api/adoptions/all")
                        .queryParam("page", "0")
                        .queryParam("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value("3"))
                .andExpect(jsonPath("$.size").value("2"))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }
}
