package com.elzhart.shortener.linkservice.api;

import com.elzhart.shortener.linkservice.api.dto.LinkShortenInput;
import com.elzhart.shortener.linkservice.service.LinkService;
import com.elzhart.shortener.linkservice.service.ValidationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/link")
@RequiredArgsConstructor
public class LinkController {

    private final ValidationService validationService;
    private final LinkService linkService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody LinkShortenInput input, Principal principal) {
        validationService.validateLongUrl(input.getLongUrl());
        String username = principal.getName();
        return linkService.create(input.getLongUrl(), username);
    }
}
