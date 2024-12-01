package com.elzhart.shortener.link.service;

import com.elzhart.shortener.common.model.Link;
import com.elzhart.shortener.common.model.User;
import com.elzhart.shortener.common.model.dao.LinkRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.elzhart.shortener.common.service.LinkShortener.shorten;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "shortLinks")
public class LinkService {

    private final LinkRepository linkRepository;
    private final UserService userService;
    @Value("${redirect.server.host}")
    private String host;
    @Value("${redirect.server.port}")
    private String port;

    @Cacheable(value = "shortLink", key = "#longUrl")
    public String create(String longUrl, String username) {
        User user = userService.getByUsername(username);
        Link link = linkRepository.findByUrl(longUrl)
                .orElse(linkRepository.save(new Link().withUrl(longUrl).withCreatedBy(user)));

        return format("http://%s:%s/%s", host, port, shorten(link.getId()));
    }

}
