package com.elzhart.shortener.linkservice.service;

import com.elzhart.shortener.linkservice.exception.ValidationException;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {
    private final static class URLMatcher {
        private static final Pattern pattern =
                Pattern.compile("((http|https)://)(www.)?"
                        + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                        + "{2,256}\\.[a-z]"
                        + "{2,6}\\b([-a-zA-Z0-9@:%"
                        + "._\\+~#?&//=]*)");


        public static Boolean isMatch(String url) {
            return pattern.matcher(url).matches();
        }
    }

    private final static String URL_EMPTY_ERROR = "Empty url";

    private final static String URL_FORMAT_ERROR = "Wrong url format";

    public void validateLongUrl(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            throw new ValidationException(URL_EMPTY_ERROR);
        }
        if (!URLMatcher.isMatch(longUrl)) {
            throw new ValidationException(URL_FORMAT_ERROR);
        }
    }
}
