package com.elzhart.shortener.common.validation;

import com.elzhart.shortener.common.exception.ValidationException;

import java.util.regex.Pattern;

public final class ValidationService {
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

    public static void validateUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new ValidationException(URL_EMPTY_ERROR);
        }
        if (!URLMatcher.isMatch(url)) {
            throw new ValidationException(URL_FORMAT_ERROR);
        }
    }
}
