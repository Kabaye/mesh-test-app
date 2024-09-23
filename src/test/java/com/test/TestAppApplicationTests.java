package com.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestAppApplicationTests {
    @Autowired
    MockMvc mockMvc;

    private String token;

    @BeforeAll
    public void setUpToken() throws Exception {
        String authResponse = mockMvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                 "  \"email\": \"test@dot.com\",\n" +
                                 "  \"password\": \"zxcvb098532\"\n" +
                                 "}"))
                .andReturn()
                .getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        token = jsonParser.parseMap(authResponse).get("token").toString();
    }

    @Test
    void givenNothing_whenSearchUser_thenReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", "01.01.1995")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenToken_whenSearchByDate_thenReturnOkAndUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .param("date", "01.01.1995")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.content[0].id").value(2),
                        jsonPath("$.content[0].name").value("Roman"));
    }

    @Test
    void givenToken_whenSearchByEmail_thenReturnOkAndUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .param("email", "test@dot.com")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.content[0].id").value(1),
                        jsonPath("$.content[0].name").value("Alexei"));
    }

    @Test
    void givenToken_whenSearchByPhone_thenReturnOkAndUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .param("phone", "792156589565")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.content[0].id").value(1),
                        jsonPath("$.content[0].name").value("Alexei"));
    }

    @Test
    void givenToken_whenSearch_thenReturnOkAndUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .param("page", "0")
                        .param("size", "3"))
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.totalElements").value(2));
    }
}
