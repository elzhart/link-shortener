package com.elzhart.shortener.linkservice.integration;

import com.elzhart.shortener.linkservice.api.dto.LinkShortenInput;
import com.elzhart.shortener.linkservice.model.User;
import com.elzhart.shortener.linkservice.model.dao.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.elzhart.shortener.linkservice.util.JsonHelper.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class LinkApiTest extends AbstractIntegrationTest {

    private static final String TEST_USER = "John Doe";


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll(@Autowired UserRepository userRepository) {
        User testUser = new User()
                .withName(TEST_USER)
                .withPassword("qwerty")
                .withEmail("johndoe@test.com");
        userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void testCreateLink() throws Exception {
        LinkShortenInput linkShortenInput = new LinkShortenInput().setLongUrl("https://www.test.com/link-shortener");

        MvcResult shortenRes = this.mockMvc
                .perform(post("/api/link/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, linkShortenInput)))
                .andExpect(status().isOk()).andReturn();

        String result = shortenRes.getResponse().getContentAsString();

        assertEquals("http://elzhart.com:8080/b", result);

    }

}
