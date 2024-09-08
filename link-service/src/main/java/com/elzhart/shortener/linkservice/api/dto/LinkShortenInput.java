package com.elzhart.shortener.linkservice.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LinkShortenInput {
    private String longUrl;
    private String alias;
}
