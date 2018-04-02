package com.example.dummy.core.network.cookie;

import java.io.IOException;

public abstract class DomainCookie {

    private String Cookie;
    private String CookieHeader;

    public DomainCookie(String cookie, String cookieHeader) {
        Cookie = cookie;
        CookieHeader = cookieHeader;
    }


    String getCookie() {
        return Cookie;
    }

    String getCookieHeader() {
        return CookieHeader;
    }
}
