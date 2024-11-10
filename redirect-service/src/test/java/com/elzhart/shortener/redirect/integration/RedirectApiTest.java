package com.elzhart.shortener.redirect.integration;

import com.elzhart.shortener.common.AbstractIntegrationTest;
import com.elzhart.shortener.common.model.Link;
import com.elzhart.shortener.common.model.User;
import com.elzhart.shortener.common.model.dao.LinkRepository;
import com.elzhart.shortener.common.model.dao.UserRepository;
import com.elzhart.shortener.common.service.LinkShortener;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class RedirectApiTest extends AbstractIntegrationTest {

    private static final String TEST_USER_NAME = "johndoe@test.com";
    private static final String TEST_USER_FULL_NAME = "John Doe";
    public static final String EXPECTED_URL = "https://www.test.com/link-shortener";

    @Autowired
    private MockMvc mockMvc;

    private static Link savedLink;

    @BeforeAll
    public static void beforeAll(
            @Autowired UserRepository userRepository,
            @Autowired LinkRepository linkRepository
    ) {
        User testUser = new User()
                .withName(TEST_USER_NAME)
                .withFullName(TEST_USER_FULL_NAME)
                .withPassword("qwerty");
        userRepository.save(testUser);
        savedLink = linkRepository.save(new Link().withUrl(EXPECTED_URL).withCreatedBy(testUser));
    }

    @Test
    @WithMockUser(username = TEST_USER_NAME)
    public void testRedirectLink() throws Exception {

        MvcResult shortenRes = this.mockMvc
                .perform(get(format("/%s", LinkShortener.shorten(savedLink.getId()))))
                .andExpect(status().is3xxRedirection()).andReturn();

        String result = shortenRes.getResponse().getHeader("Location");

        assertEquals(EXPECTED_URL, result);
    }

}
