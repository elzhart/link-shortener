package com.elzhart.shortener.linkservice.unit;

import com.elzhart.shortener.linkservice.service.LinkShortener;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkShortenerTest {

    private final long TEST_LINK_ID = 123456789L;
    private final String TEST_SHORT_LINK = "iwaUH";

    @Test
    public void shorten() {
        String link = LinkShortener.shorten(TEST_LINK_ID);
        assertEquals(TEST_SHORT_LINK, link);
    }

    @Test
    public void encode() {
        long linkId = LinkShortener.encode(TEST_SHORT_LINK);
        assertEquals(linkId, TEST_LINK_ID);
    }
}
