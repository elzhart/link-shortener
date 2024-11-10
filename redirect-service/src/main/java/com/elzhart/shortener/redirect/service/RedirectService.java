package com.elzhart.shortener.redirect.service;


import com.elzhart.shortener.common.exception.NotFoundException;
import com.elzhart.shortener.common.model.Link;
import com.elzhart.shortener.common.model.dao.LinkRepository;
import com.elzhart.shortener.common.service.LinkShortener;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "longLinks")
public class RedirectService {

    private final LinkRepository linkRepository;

    @Cacheable(value = "longLink", key = "#shortUrl")
    public String findLongLink(String shortUrl) {
        Long id = LinkShortener.encode(shortUrl);

        return linkRepository.findById(id).orElseThrow(() -> new NotFoundException(Link.class, id)).getUrl();
    }
}
