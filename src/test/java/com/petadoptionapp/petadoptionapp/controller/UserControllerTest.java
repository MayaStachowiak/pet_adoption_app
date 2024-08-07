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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

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
    void testBasicAuthenticate() throws Exception {
        mockMvc.perform(get("/").with(httpBasic("user", "haslo123")));
    }


    @Test
    @Transactional
    void shouldSaveUser() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/user_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        mockMvc.perform(post("/api/users")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("Adam007"))
                .andExpect(jsonPath("$.user.firstName").value("Adam"))
                .andExpect(jsonPath("$.user.lastName").value("Mickiewicz"))
                .andExpect(jsonPath("$.user.email").value("adasiek@gmail.com"))
                .andExpect(jsonPath("$.user.phoneNumber").value("587856999"))
                .andExpect(jsonPath("$.user.password").doesNotExist())
                .andExpect(jsonPath("$.user.role").doesNotExist())
                .andExpect(jsonPath("$.user.id").exists());
    }


    @Test
    @Transactional
    void shouldGetUserById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/user_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        //zapisanie usera
        mockMvc.perform(post("/api/users")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //sprawdzenie
        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Adam007"))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Mickiewicz"))
                .andExpect(jsonPath("$.email").value("adasiek@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("587856999"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.role").doesNotExist())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Transactional
    void shouldReturnNotFoundForNonExistentUser() throws Exception {
        mockMvc.perform(get("/api/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void shouldDeleteUserById() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/user_create_request_ok.json");
        String requestBody = IOUtils.toString(resource.getInputStream());

        // zapisanie usra
        mockMvc.perform(post("/api/users")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // skasowanie
        mockMvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    @Transactional
    @Sql("classpath:sql/clear_data.sql")
    void shouldUpdateUserById() throws Exception {
        Resource createResource = resourceLoader.getResource("classpath:json/user_create_request_ok.json");
        String createRequestBody = IOUtils.toString(createResource.getInputStream());

        // zapisanie usera
        mockMvc.perform(post("/api/users")
                        .content(createRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Resource updateResource = resourceLoader.getResource("classpath:json/user_update_request_ok.json");
        String updateRequestBody = IOUtils.toString(updateResource.getInputStream());

        // update, sprawdzenie
        mockMvc.perform(patch("/api/users/5")
                        .content(updateRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("AdamUpdated"))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Mickiewicz"))
                .andExpect(jsonPath("$.email").value("adasiek_updated@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("587856999"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.role").doesNotExist())
                .andExpect(jsonPath("$.id").exists());
    }


    @Test
    @Transactional
    @Sql("classpath:sql/user_data.sql")
    void shouldGetUserPage() throws Exception {

        mockMvc.perform(get("/api/users/all")
                        .queryParam("page", "0")
                        .queryParam("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value("3"))
                .andExpect(jsonPath("$.size").value("2"))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }
}













