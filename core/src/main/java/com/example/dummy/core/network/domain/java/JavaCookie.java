package com.example.dummy.core.network.domain.java;

import com.example.dummy.core.network.cookie.DomainCookie;
import com.example.dummy.core.storage.StorageConstants;

public class JavaCookie extends DomainCookie {

    public JavaCookie() {
        super(StorageConstants.EmptyCookieHeader, StorageConstants.EmptyCookie);
    }
}
