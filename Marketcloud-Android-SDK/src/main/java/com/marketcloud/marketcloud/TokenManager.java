/*
    Copyright (c) 2015 Marketcloud
    http://www.marketcloud.it

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        https://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.marketcloud.marketcloud;

import android.content.Context;

import com.loopj.android.http.PersistentCookieStore;

import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * TokenManager class. <br />
 * <br />
 * Creates a token manager instance, that will handle the session tokens.
 */
public class TokenManager {

    PersistentCookieStore myCookieStore;

    /**
     * Constructor.
     *
     * @param ct application context
     */
    public TokenManager(Context ct) {
        myCookieStore = new PersistentCookieStore(ct);
    }

    /**
     * Get the session token from the cookies.
     *
     * @return the session token, if exists
     */
    public String getSessionToken() {

        for (Cookie c : myCookieStore.getCookies()) {
            if (c.getName().equals("auth")) return c.getValue();
        }

        return null;
    }

    /**
     * Get the cookie with given name.
     *
     * @param name name of the cookie
     * @return the cookie, if exists
     */
    @SuppressWarnings("unused")
    public String getCookie(String name) {

        for (Cookie c : myCookieStore.getCookies()) {
            if (c.getName().equals(name)) return c.getValue();
        }

        return null;
    }

    /**
     * Deletes a token.
     *
     * @param name the name of the cookie to be deleted
     */
    public void deleteToken(String name) {

        Cookie cookie = null;
        boolean found = false;

        for (Cookie c : myCookieStore.getCookies()) {
            if (c.getName().equals(name)) {
                cookie = c;
                found = true;
                break;
            }
        }

        if (found) myCookieStore.deleteCookie(cookie);
    }

    /**
     * Sets a cookie.
     *
     * @param name cookie name
     * @param arg cookie value
     */
    public void setToken(String name, String arg) {
        BasicClientCookie newCookie = new BasicClientCookie(name, arg);
        newCookie.setVersion(1);
        newCookie.setDomain("api.marketcloud.it");
        newCookie.setPath("/");
        newCookie.setSecure(true);
        //newCookie.setExpiryDate();
        myCookieStore.addCookie(newCookie);
    }
}