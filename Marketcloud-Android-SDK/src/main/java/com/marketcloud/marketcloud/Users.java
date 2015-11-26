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
 * Users class. <br />
 * <br />
 * Creates a new user instance.
 */
public class Users {

    private String publicKey;
    private Context context;
    private Utilities api;
    private String token;
    private TokenManager tm;

    /**
     * Constructor.
     *
     * @param key the public key to access the APIs
     */
    public Users(String key, TokenManager tokenManager, Context ct) {
        publicKey = key;
        api = new Utilities(context, key);
        token = tokenManager.getSessionToken();
        context = ct;
        tm = tokenManager;
    }

    /**
     * Authentication: allows the user to login.
     *
     * @param email    user's email
     * @param password user's password
     * @return true if the login is successful, false if not
     */
    @SuppressWarnings("unused")
    public boolean authenticate(String email, String password) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = toJsonObject(
                email,
                password);

        if (jsonObject != null) {
            jsonObject = (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/users/authenticate",
                                    publicKey,
                                    jsonObject.toString()})
                    .get()
                    .get(0);

            if (!(boolean) jsonObject.get("status"))
                return false;
            else {
                token = jsonObject.getJSONObject("data").getString("token");

                tm.setToken("auth", token);

                return true;
            }
        }

        return false;
    }

    /**
     * Register a new user.
     *
     * @param name     username
     * @param email    user's email
     * @param password user's password
     * @return the data of the newborn user
     */
    @SuppressWarnings("unused")
    public JSONObject create(String name, String email, String password) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                name,
                email,
                password);

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/users",
                                    publicKey,
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Register a new user.
     *
     * @param name     username
     * @param email    user's email
     * @param password user's password
     * @param imageURL url to the user's profile pic
     * @return the data of the newborn user
     */
    @SuppressWarnings("unused")
    public JSONObject create(String name, String email, String password, String imageURL) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                name,
                email,
                password,
                imageURL);

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/users",
                                    publicKey,
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Returns the data of a specific user.
     *
     * @param id the id of the desired user
     * @return the data of the desired user
     */
    @SuppressWarnings("unused")
    public JSONObject getById(String id) throws InterruptedException, ExecutionException, JSONException {
        if (token != null)
            return api.getById("http://api.marketcloud.it/v0/users/", id, token);
        else return null;
    }

    /**
     * Returns the data of a specific user.
     *
     * @param id the id of the desired user
     * @return the data of the desired user
     */
    @SuppressWarnings("unused")
    public JSONObject getById(int id) throws InterruptedException, ExecutionException, JSONException {
        if (token != null)
            return api.getById("http://api.marketcloud.it/v0/users/", id, token);
        else return null;
    }

    /**
     * Returns a list with the data of all the users.
     *
     * @return list of users (and their data)
     */
    @SuppressWarnings("unused")
    public JSONArray get() throws ExecutionException, InterruptedException {
        if (token != null)
            return api.getInstanceList("http://api.marketcloud.it/v0/users", token);
        else return null;
    }

    /**
     * Updates the data of a user.
     *
     * @param id       the id of the user
     * @param name     the new username
     * @param email    the new email
     * @param password the new password
     * @return the data of the user after the update
     */
    @SuppressWarnings("unused")
    public JSONObject update(String id, String name, String email, String password) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObject(
                    name,
                    email,
                    password);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/users/" + id,
                                        publicKey + ":" + token,
                                        jo.toString()})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Updates the data of a user.
     *
     * @param id       the id of the user
     * @param name     the new username
     * @param email    the new email
     * @param password the new password
     * @param imageURL the new user pic
     * @return the data of the user after the update
     */
    @SuppressWarnings("unused")
    public JSONObject update(String id, String name, String email, String password, String imageURL) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (token != null) {
            JSONObject jo = toJsonObject(
                    name,
                    email,
                    password,
                    imageURL);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/users/" + id,
                                        publicKey + ":" + token,
                                        jo.toString()})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Deletes a user from the database.
     *
     * @param id the id of the user
     * @return true if succeeded, false if not
     */
    @SuppressWarnings("unused")
    public boolean delete(String id) throws InterruptedException, ExecutionException, JSONException {
        return (boolean) api.delete("http://api.marketcloud.it/v0/users/", id, token).get("status");
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param name      username
     * @param email     user's email
     * @param password  user's password
     * @param image_url user's profile pic
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(String name, String email, String password, String image_url) throws JSONException {
        return new JSONObject().put("name", name).put("email", email).put("password", password).put("image_url", image_url);
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param name     username
     * @param email    user's email
     * @param password user's password
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(String name, String email, String password) throws JSONException {
        return new JSONObject().put("name", name).put("email", email).put("password", password);
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param email    user's email
     * @param password user's password
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(String email, String password) throws JSONException {
        return new JSONObject().put("email", email).put("password", password);
    }
}