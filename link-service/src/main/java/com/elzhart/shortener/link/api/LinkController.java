package com.elzhart.shortener.link.api;

import com.elzhart.shortener.common.validation.ValidationService;
import com.elzhart.shortener.link.api.dto.LinkShortenInput;
import com.elzhart.shortener.link.service.LinkService;

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

    private final LinkService linkService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody LinkShortenInput input, Principal principal) {
        ValidationService.validateUrl(input.getLongUrl());
        String username = principal.getName();
        return linkService.create(input.getLongUrl(), username);
    }
}
