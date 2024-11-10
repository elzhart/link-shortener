package com.elzhart.shortener.common.unit;

import com.elzhart.shortener.common.service.LinkShortener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkShortenerTest {

    private final long TEST_LINK_ID = 123456789L;
    private final String TEST_SHORT_LINK = "iwaUH";

    @Test
    public void shorten() {
        String link = LinkShortener.shorten(TEST_LINK_ID);
        Assertions.assertEquals(TEST_SHORT_LINK, link);
    }

    @Test
    public void encode() {
        long linkId = LinkShortener.encode(TEST_SHORT_LINK);
        Assertions.assertEquals(linkId, TEST_LINK_ID);
    }
}
