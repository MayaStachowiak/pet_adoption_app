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
public class PreferenceControllerTest {

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
    void shouldSavePreference() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/preference_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        mockMvc.perform(post("/api/preferences")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preference.type").value("Dog"))
                .andExpect(jsonPath("$.preference.color").value("Black"))
                .andExpect(jsonPath("$.preference.minAge").value(1))
                .andExpect(jsonPath("$.preference.maxAge").value(5))
                .andExpect(jsonPath("$.preference.id").exists());
    }


    @Test
    @Transactional
    void shouldGetPreferenceById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/preference_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        mockMvc.perform(post("/api/preferences")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/preferences/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Dog"))
                .andExpect(jsonPath("$.color").value("Black"))
                .andExpect(jsonPath("$.minAge").value(1))
                .andExpect(jsonPath("$.maxAge").value(5))
                .andExpect(jsonPath("$.id").exists());
    }


    @Test
    @Transactional
    void shouldReturnNotFoundForNonExistentPreference() throws Exception {
        mockMvc.perform(get("/api/preferences/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void shouldDeletePreferenceById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/preference_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        mockMvc.perform(post("/api/preferences")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/preferences/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    @Transactional
    @Sql("classpath:sql/clear_data.sql")
    void shouldUpdatePreferenceById() throws Exception {
        Resource createResource = resourceLoader.getResource("classpath:json/preference_create_request_ok.json");
        String createRequestBody = IOUtils.toString(createResource.getInputStream());

        mockMvc.perform(post("/api/preferences")
                        .content(createRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Resource updateResource = resourceLoader.getResource("classpath:json/preference_update_request_ok.json");
        String updateRequestBody = IOUtils.toString(updateResource.getInputStream());

        mockMvc.perform(patch("/api/preferences/1")
                        .content(updateRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Cat"))
                .andExpect(jsonPath("$.color").value("White"))
                .andExpect(jsonPath("$.minAge").value(2))
                .andExpect(jsonPath("$.maxAge").value(6))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Transactional
    @Sql("classpath:sql/preference_data.sql")
    void shouldGetPreferencePage() throws Exception {
        mockMvc.perform(get("/api/preferences/all")
                        .queryParam("page", "0")
                        .queryParam("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value("3"))
                .andExpect(jsonPath("$.size").value("2"))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }
}
