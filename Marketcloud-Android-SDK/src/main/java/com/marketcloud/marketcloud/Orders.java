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

import java.util.concurrent.ExecutionException;

/**
 * Orders class. <br />
 * <br />
 * Creates a new order instance.
 */
public class Orders {

    private String publicKey;
    private Context context;
    private Utilities api;
    private String token;

    /**
     * Constructor.
     *
     * @param key application public key
     * @param tokenManager token manager
     * @param ct application context
     */
    public Orders(String key, TokenManager tokenManager, Context ct) {
        publicKey = key;
        api = new Utilities(context, key);
        token = tokenManager.getSessionToken();
        context = ct;
    }

    /**
     * Creates a new order.
     *
     * @param userid user id
     * @param shipping_address_id shipping address id
     * @param billing_address_id billing address id
     * @param items list of items in the order
     * @return the new order
     */
    @SuppressWarnings("unused")
    public JSONObject create(int userid, int shipping_address_id, int billing_address_id, Object[][] items) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObjectSend(
                    shipping_address_id,
                    billing_address_id,
                    toJsonArray(items),
                    userid);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "post",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Creates a new order.
     *
     * @param userid user id
     * @param state state of the order ("Processing", "Shipped", "Cancelled", etc.)
     * @param shipping_address_id shipping address id
     * @param billing_address_id billing address id
     * @param items list of items in the order
     * @return the new order
     */
    @SuppressWarnings("unused")
    public JSONObject create(int userid, String state, int shipping_address_id, int billing_address_id, Object[][] items) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObjectSend(
                    state,
                    shipping_address_id,
                    billing_address_id,
                    toJsonArray(items),
                    userid);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "post",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Creates a new order.
     *
     * @param userid user id
     * @param shipping_address_id shipping address id
     * @param billing_address_id billing address id
     * @param items list of items in the order
     * @return the new order
     */
    @SuppressWarnings("unused")
    public JSONObject create(int userid, int shipping_address_id, int billing_address_id, JSONArray items) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObjectSend(
                    shipping_address_id,
                    billing_address_id,
                    items,
                    userid);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "post",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Creates a new order. <br />
     * <br />
     * Required parameters: <br />
     * - shipping_address_id, <br />
     * - billing_address_id, <br />
     * - items. <br />
     * <br />
     * Optional parameters: <br />
     * - user_id, <br />
     * - state, <br />
     * - currency_id, <br />
     * - store_id. <br />
     * <br />
     * Example: <br />
     * {<br />
     *     "shipping_address_id" : "0001", <br />
     *     "billing_address_id" : "0001", <br />
     *     "items" : [{ <br />
     *                  "product_id" : "0002", <br />
     *                  "quantity": 2
     *              }] <br />
     *     "currency_id" : "0003" <br />
     * }<br />
     *
     * @param jo the order parameters
     * @return the new order
     */
    @SuppressWarnings("unused")
    public JSONObject create(JSONObject jo) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null)
            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "post",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);

        return null;
    }

    /**
     * Get a list of all the user's orders.
     *
     * @return list of the user's orders
     */
    @SuppressWarnings("unused")
    public JSONArray get() throws ExecutionException, InterruptedException {
        if (token != null)
            return api.getInstanceList("http://api.marketcloud.it/v0/orders", token);
        else return null;
    }

    /**
     * Get an order by its id.
     *
     * @param id order id
     * @return the order data
     */
    @SuppressWarnings("unused")
    public JSONObject getById(int id) throws InterruptedException, ExecutionException, JSONException {
        if (token != null)
            return api.getById("http://api.marketcloud.it/v0/orders/", id, token);
        else return null;
    }

    /**
     *
     * Updates an order.
     *
     * @param userid new user id
     * @param shipping_address_id new shipping address id
     * @param billing_address_id new billing address id
     * @param items new list of items
     * @return the updated order
     */
    @SuppressWarnings("unused")
    public JSONObject update(int userid, int shipping_address_id, int billing_address_id, Object[][] items) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObjectSend(
                    shipping_address_id,
                    billing_address_id,
                    toJsonArray(items),
                    userid);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Updates an order.
     *
     * @param userid new user id
     * @param state new state of the order ("Processing", "Shipped", "Cancelled", etc.)
     * @param shipping_address_id new shipping address id
     * @param billing_address_id new billing address id
     * @param items new list of items in the order
     * @return the updated order
     */
    @SuppressWarnings("unused")
    public JSONObject update(int userid, String state, int shipping_address_id, int billing_address_id, Object[][] items) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObjectSend(
                    state,
                    shipping_address_id,
                    billing_address_id,
                    toJsonArray(items),
                    userid);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Updates an order.
     *
     * @param userid new user id
     * @param shipping_address_id new shipping address id
     * @param billing_address_id new billing address id
     * @param items new list of items in the order
     * @return the updated order
     */
    @SuppressWarnings("unused")
    public JSONObject update(int userid, int shipping_address_id, int billing_address_id, JSONArray items) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObjectSend(
                    shipping_address_id,
                    billing_address_id,
                    items,
                    userid);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Updates an order. <br />
     * <br />
     * Parameters list: <br />
     * - shipping_address_id, <br />
     * - billing_address_id, <br />
     * - items, <br />
     * - user_id, <br />
     * - state, <br />
     * - currency_id, <br />
     * - store_id. <br />
     * <br />
     * Example: <br />
     * {<br />
     *     "currency_id" : "0005" <br />
     *     "store_id" : "0004" <br />
     * }<br />
     *
     * @param jo order parameters to update
     * @return the updated order
     */
    @SuppressWarnings("unused")
    public JSONObject update(JSONObject jo) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null)
            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/orders",
                                        publicKey + ":" + token,
                                        jo.toString(),})
                        .get()
                        .get(0);

        return null;
    }

    /**
     * Convert bidimensional array of items to JSONArray of items.
     *
     * @param array bidimensional array to convert
     * @return converted array
     */
    private JSONArray toJsonArray(Object[][] array) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Object[] i : array) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("product_id", (int) i[0]);
            jsonObject.put("quantity", (int) i[1]);
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    /**
     * Prepare a proper JSONObject from some data.
     *
     * @param shipping_address_id shipping address id
     * @param billing_address_id billing address id
     * @param jsonArray items array
     * @param userid user id
     * @return JSONObject
     */
    private JSONObject toJsonObjectSend(int shipping_address_id, int billing_address_id, JSONArray jsonArray, int userid) throws JSONException {
        return new JSONObject().put("shipping_address_id", shipping_address_id).put("billing_address_id", billing_address_id).put("items", jsonArray).put("user_id", userid);
    }

    /**
     * Prepare a proper JSONObject from some data.
     *
     * @param state order state
     * @param shipping_address_id shipping address id
     * @param billing_address_id billing address id
     * @param jsonArray items array
     * @param userid user id
     * @return JSONObject
     */
    private JSONObject toJsonObjectSend(String state, int shipping_address_id, int billing_address_id, JSONArray jsonArray, int userid) throws JSONException {
        return new JSONObject().put("state", state).put("shipping_address_id", shipping_address_id).put("billing_address_id", billing_address_id).put("items", jsonArray).put("user_id", userid);
    }
}