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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Products class. <br />
 * <br />
 * Creates a product instance.
 */
public class Products {
;
    private Utilities api;

    /**
     * Constructor.
     *
     * @param key the public key to access the APIs
     */
    public Products(String key, Context context) {
        api = new Utilities(context, key);
    }

    /**
     * Retrieves the product with the given ID.
     *
     * @param id the ID of the product
     * @return the data of the product with the given ID
     */
    @SuppressWarnings("unused")
    public JSONObject getById(int id) throws InterruptedException, ExecutionException, JSONException {
        return api.getById("http://api.marketcloud.it/v0/products/", id);
    }

    /**
     * Retrieves the products that comply with the filters.
     *
     * @param map list of filters (organized in a map<filterName, filterData>
     * @return a list of products that comply with the filters
     */
    @SuppressWarnings("unused")
    public JSONObject list(HashMap<String, Object> map) throws ExecutionException, InterruptedException, JSONException {
        return api.list("http://api.marketcloud.it/v0/products?", map);
    }
}