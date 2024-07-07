package com.petadoptionapp.petadoptionapp.controller;


import com.petadoptionapp.petadoptionapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@SpringBootTest
@Testcontainers
//@AutoConfigureMockMvc
@AutoConfigureMockMvc(addFilters = false)
//@WithMockUser(username = "user", password = "haslo123", roles = "USER")
public class UserControllerTest {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private UserRepository userRepository;


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


    // todo do skończenia testy

}













