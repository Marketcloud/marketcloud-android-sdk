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
 * Carts class. <br />
 * <br />
 * Creates a cart instance. Each user has by definition just one cart; when a new cart is created, if the server will
 * notice that a previous cart owned by the user still exists, it will execute a patch operation and provide the merged
 * cart.<br />
 * Notice: this feature is still in beta development, so at the moment you should be able to create more than one cart
 * per user.
 */
public class Carts {

    private String publicKey;
    private Context context;
    private Utilities api;
    private TokenManager tm;

    /**
     * Constructor.
     *
     * @param key application public key
     * @param tokenManager token manager
     * @param ct application context
     */
    public Carts(String key, TokenManager tokenManager, Context ct) {
        publicKey = key;
        api = new Utilities(ct, key);
        tm = tokenManager;
        context = ct;
    }

    /**
     * Creates a new cart.
     *
     * @param userid   the id of the user that is creating the cart
     * @param products the list of products that will be added to the cart
     * @return the cart
     */
    @SuppressWarnings("unused")
    public JSONObject create(int userid, Object[][] products) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                userid,
                toJsonArray(products));

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/carts",
                                    publicKey + ":" + tm.getSessionToken(),
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Creates a new cart.
     *
     * @param products the list of products that will be added to the cart
     * @return the cart
     */
    @SuppressWarnings("unused")
    public JSONObject create(Object[][] products) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                toJsonArray(products));

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/carts",
                                    publicKey + ":" + tm.getSessionToken(),
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Creates a new cart.
     *
     * @param userid the id of the user that is creating the cart
     * @param products the list of products that will be added to the cart
     * @return the cart
     */
    @SuppressWarnings("unused")
    public JSONObject create(int userid, JSONArray products) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                userid,
                products);

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/carts",
                                    publicKey + ":" + tm.getSessionToken(),
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Creates a new cart.
     *
     * @param products the list of products that will be added to the cart
     * @return the cart
     */
    @SuppressWarnings("unused")
    public JSONObject create(JSONArray products) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                products);

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/carts",
                                    publicKey + ":" + tm.getSessionToken(),
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Returns the data of a specific cart.
     *
     * @param id the id of the desired cart
     * @return the data of the desired cart
     */
    @SuppressWarnings("unused")
    public JSONObject getById(int id) throws InterruptedException, ExecutionException, JSONException {
        if (tm.getSessionToken() != null)
            return api.getById("http://api.marketcloud.it/v0/carts/", id, tm.getSessionToken());
        else return null;
    }

    /**
     * Increment the quantity of some products in the cart by a specified quantity, or adds a new product to the cart.
     * Note: returns null if the quantity is not available.
     *
     * @param id cart id
     * @param products list of products to update, in the form {product_id, quantity_to_add}
     * @return the updated cart
     */
    @SuppressWarnings("unused")
    public JSONObject add(int id, Object[][] products) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null) {
            JSONObject jo = toJsonObjectPatch("add", toJsonArray(products));

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "patch",
                                        "http://api.marketcloud.it/v0/carts/" + id,
                                        publicKey + ":" + tm.getSessionToken(),
                                        jo.toString()})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Remove some products from the cart.
     *
     * @param id       cart id
     * @param products list of products that will be removed
     * @return the updated cart
     */
    @SuppressWarnings("unused")
    public JSONObject remove(int id, Object[] products) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null) {
                JSONObject jo = toJsonObjectPatch("remove", toJsonArray(products));

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "patch",
                                        "http://api.marketcloud.it/v0/carts/" + id,
                                        publicKey + ":" + tm.getSessionToken(),
                                        jo.toString()})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Updates the quantity of some products in the cart to a specified quantity.
     * Note: returns null if the quantity is not available.
     *
     * @param id       cart id
     * @param products list of products that will be updated, in the form {product_id, new_quantity}
     * @return the updated cart
     */
    @SuppressWarnings("unused")
    public JSONObject update(int id, Object[][] products) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null) {
            JSONObject jo = toJsonObjectPatch("update", toJsonArray(products));

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "patch",
                                        "http://api.marketcloud.it/v0/carts/" + id,
                                        publicKey + ":" + tm.getSessionToken(),
                                        jo.toString()})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Deletes a cart.
     *
     * @param id cart id
     * @return true if successful, false if not
     */
    @SuppressWarnings("unused")
    public boolean delete(int id) throws InterruptedException, ExecutionException, JSONException {
        return tm.getSessionToken() != null && (boolean) api.delete("http://api.marketcloud.it/v0/carts/", id, tm.getSessionToken()).get("status");
    }


    /**
     * Converts a bidimensional array into a json array.
     *
     * @param array the array that will be converted
     * @return the json array
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
     * Converts an array into a json array.
     *
     * @param array the array that will be converted
     * @return the json array
     */
    private JSONArray toJsonArray(Object[] array) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Object i : array) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("product_id", (int) i);
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param userid user id
     * @param jsonArray the array of the items
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(int userid, JSONArray jsonArray) throws JSONException {
        return new JSONObject().put("user_id", userid).put("items", jsonArray);
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param op operation to be perfomed. must be "add", "update" or "remove"
     * @param jsonArray the array of the items
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObjectPatch(String op, JSONArray jsonArray) throws JSONException {
        return new JSONObject().put("op", op).put("items", jsonArray);
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param jsonArray the array of the items
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(JSONArray jsonArray) throws JSONException {
        return new JSONObject().put("items", jsonArray);
    }
}