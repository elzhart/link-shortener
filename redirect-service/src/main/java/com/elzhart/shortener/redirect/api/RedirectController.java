package com.elzhart.shortener.redirect.api;

import com.elzhart.shortener.redirect.service.RedirectService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService redirectService;

    @GetMapping("{shortUrl}")
    public ModelAndView redirect(@PathVariable String shortUrl) {
        return new ModelAndView("redirect:" + redirectService.findLongLink(shortUrl));
    }
}
