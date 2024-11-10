package com.elzhart.shortener.link.integration;

import com.elzhart.shortener.common.AbstractIntegrationTest;
import com.elzhart.shortener.link.api.dto.LinkShortenInput;
import com.elzhart.shortener.common.model.User;
import com.elzhart.shortener.common.model.dao.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.elzhart.shortener.link.util.JsonHelper.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class LinkApiTest extends AbstractIntegrationTest {

    private static final String TEST_USER_NAME = "johndoe@test.com";
    private static final String TEST_USER_FULL_NAME = "John Doe";


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll(@Autowired UserRepository userRepository) {
        User testUser = new User()
                .withName(TEST_USER_NAME)
                .withFullName(TEST_USER_FULL_NAME)
                .withPassword("qwerty");
        userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = TEST_USER_NAME)
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
