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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Shippings class. <br />
 * <br />
 * Creates a new shipping instance.
 */
public class Shippings {

    private Utilities api;

    /**
     * Constructor.
     *
     * @param key the public key to access the APIs
     */
    public Shippings(String key, Context ct) {
        api = new Utilities(ct, key);
    }

    /**
     * Returns the data about the shipping with the given ID.
     *
     * @param id the id of the shipping that the user wants to retrieve
     * @return a JSONObject containing the data about the given shipping, or null if the ID does not belong to any shipping
     */
    @SuppressWarnings("unused")
    public JSONObject getById(final int id) throws InterruptedException, ExecutionException, JSONException {
        return api.getById("http://api.marketcloud.it/v0/shippings/", id);
    }

    /**
     * Returns a list of shippings that comply with the given query.
     *
     * @param map HashMap containing a list of filters
     * @return a JSONArray containing a list of shippings that comply with the given filter
     */
    @SuppressWarnings("unused")
    public JSONObject list(HashMap<String, Object> map) throws ExecutionException, InterruptedException, JSONException {
        return api.list("http://api.marketcloud.it/v0/shippings?", map);
    }
}