package com.elzhart.shortener.link.integration.data;

import java.security.Principal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestPrincipal implements Principal {

    private String testUsername;

    @Override
    public boolean equals(Object another) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return testUsername;
    }
}
