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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Json class. <br />
 * <br />
 * An helper class for handling Json data.
 */
public class Json {

    /**
     * Retrieves (if it exists and is in a known position) the id of an instance, saved in a JSONObject.
     *
     * @param jsonObject the json that will be analyzed
     * @return the id (if exists)
     */
    @SuppressWarnings("unused")
    public int getId(JSONObject jsonObject) throws JSONException {

        if (jsonObject != null)
            if (jsonObject.has("id"))
                return jsonObject.getInt("id");
            else if (jsonObject.has("data"))
                if (jsonObject.getJSONObject("data").has("id"))
                    return jsonObject.getJSONObject("data").getInt("id");

        return -1;
    }

    /**
     * Retrieve data from a JSONObject. <br />
     * NOTE: the field "data" contains a JSONArray if you made a "list" request, or a JSONObject in all
     * other cases. The output will be an array of JSONObjects; this means that if the field contained a
     * single JSONObject it will be put in position 0 with length 1, but if the field contained a JSONArray
     * every item of the array will be parsed as a single JSONObject and will be put in the array accordingly
     * to its initial position.
     *
     * @param jsonObject JSONObject that will be parsed
     * @return JSONObject array containing the data
     */
    @SuppressWarnings("unused")
    public JSONObject[] getData(JSONObject jsonObject) throws JSONException {

        JSONObject jo[];

        try {
            JSONArray ja = jsonObject.getJSONArray("data");

            jo = new JSONObject[ja.length()];

            for (int i = 0; i < ja.length(); i++) {
                jo[i] = ja.getJSONObject(i);
            }
        } catch (JSONException | NullPointerException e) {
            jo = new JSONObject[1];

            jo[0] = jsonObject.getJSONObject("data");
        }

        return jo;
    }

    /**
     * Retrieves the "status" field in a JSONObject.
     *
     * @param jsonObject the JSONObject that will be parsed
     * @return a boolean containing the status of the JSONObject
     * @throws JSONException usually thrown if the "status" field does not exist
     */
    @SuppressWarnings("unused")
    public boolean getStatus(JSONObject jsonObject) throws JSONException {
        return jsonObject.getBoolean("status");
    }

    /**
     * Parses a JSONObject, looking for the given structure. <br />
     * <br />
     * Suppose you have a json like <br />
     * { <br />
     *     "id" : 1234, <br />
     *     "name" : "abcd", <br />
     *     "image_url" : "http://example" <br />
     * } <br />
     * and you want to parse its data. If you don't want to do it manually, and you already know its structure,
     * you can use this method. Just pass a string array containing the keys {"id", "name", "image_url"} and
     * the method will parse the data for you. <br />
     * <br />
     * Note: if the structure is not correct, the method will throw an exception and fail. If the JSONObject
     * contains a JSONArray, only the first argument of the array (the first object) will be parsed: if you
     * need to use this method for a list, please call it once for every list item.
     *
     * @param keys json fields names
     * @return an Hashmap with the parsed data
     */
    @SuppressWarnings("unused")
    public HashMap<String, Object> parseData(String[] keys, JSONObject jsonObject) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();

        if (jsonObject.has("data")) jsonObject = getData(jsonObject)[0];

        for (String key : keys) {
            map.put(key, jsonObject.get(key));
        }

        return map;
    }

    /**
     * Parses a JSONObject, looking for the given structure. <br />
     * <br />
     * It differs from the other parseData because the user should provide a pre-prepared Hashmap that will be
     * filled by the method. The keys of the map will be used as the string array of the other parseData.
     * <br />
     * In order to avoid override of data that you need to keep, and to use the method without getting an
     * exception, you could pass an "ignore" list containing the keys that you want the method to ignore,
     * simply adding all the keys you want to ignore to the method call. <br />
     * Example: parseData(map, jsonObject, "price", "source"); <br />
     * If the map contains the keys "price" and "source", then the method will ignore them. <br />
     * The normal method call remains: parseData(map, jsonObject) .
     * <br />
     * Note: if the structure is not correct, the method will throw an exception and fail. If the JSONObject
     * contains a JSONArray, only the first argument of the array (the first object) will be parsed.
     *
     * @param map pre-prepared Hashmap
     * @return an Hashmap with the parsed data
     */
    @SuppressWarnings("unused")
    public HashMap<String, Object> parseData(HashMap<String, Object> map, JSONObject jsonObject, String... ignore) throws JSONException {
        if (jsonObject.has("data")) jsonObject = getData(jsonObject)[0];

        ArrayList<String> ignorelist = new ArrayList<>();

        Collections.addAll(ignorelist, ignore);

        for (String key : map.keySet()) {
            if (!ignorelist.contains(key)) map.put(key, jsonObject.get(key));
        }

        return map;
    }

    /**
     * Parses a JSONObject, looking for the given structure. <br />
     * <br />
     * It differs from the other parseData because the user should provide a pre-prepared Hashmap that will be
     * filled by the method. The keys of the map will be used as the string array of the other parseData.
     * <br />
     * In order to avoid override of data that you need to keep, and to use the method without getting an
     * exception, you could pass an "ignore" list containing the keys that you want the method to ignore.
     * <br />
     * Note: if the structure is not correct, the method will throw an exception and fail. If the JSONObject
     * contains a JSONArray, only the first argument of the array (the first object) will be parsed.
     *
     * @param map pre-prepared Hashmap
     * @return an Hashmap with the parsed data
     */
    @SuppressWarnings("unused")
    public HashMap<String, Object> parseData(HashMap<String, Object> map, JSONObject jsonObject, List<String> ignore) throws JSONException {
        if (jsonObject.has("data")) jsonObject = getData(jsonObject)[0];

        for (String key : map.keySet()) {
            if (!ignore.contains(key)) map.put(key, jsonObject.get(key));
        }

        return map;
    }

    /**
     * Check validity of a JSONObject. <br />
     * A JSONObject is considered valid by the application if it is not null and has a "status" field. <br />
     * A "false" status field does not affect validity of the JSONObject, but simply states that the request
     * failed.
     *
     * @param jsonObject JSONObject that will be checked
     * @return true if valid, false if not valid
     */
    @SuppressWarnings("unused")
    public boolean checkValidity(JSONObject jsonObject) {

        if (jsonObject != null)
            try {
                getStatus(jsonObject);
                return true;
            } catch (JSONException ignored) {}

        return false;
    }

    /**
     * Retrieves "errors" field from a failed request JSONObject.
     *
     * @param jsonObject failed request
     * @return the errors
     * @throws JSONException if the JSONObject is not valid
     */
    @SuppressWarnings("unused")
    public JSONArray getErrors(JSONObject jsonObject) throws JSONException {

        if (checkValidity(jsonObject) && !getStatus(jsonObject))
            return jsonObject.getJSONArray("errors");

        return new JSONArray();
    }

    /**
     * Returns the error code. <br />
     * The possible error codes are the standard HTTP error codes. Here's a short list of them: <br />
     * http://www.marketcloud.it/documentation/rest-api/introduction
     *
     * @param jsonObject error
     * @return error code
     * @throws JSONException JSONObject not valid
     */
    @SuppressWarnings("unused")
    public int getErrorCode(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("code");
    }

    /**
     * Returns the error message.
     *
     * @param jsonObject error
     * @return error message
     * @throws JSONException JSONObject not valid
     */
    @SuppressWarnings("unused")
    public String getErrorMessage(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("message");
    }

    /**
     * Count the errors. Returns 0 if there are none, even if the status is "true".
     *
     * @param jsonObject response
     * @return number of errors in the response
     */
    @SuppressWarnings("unused")
    public int countErrors(JSONObject jsonObject) {
        try {
            if (checkValidity(jsonObject) && !getStatus(jsonObject))
            {
                return getErrors(jsonObject).length();
            }
        } catch (JSONException ignored) {}

        return 0;
    }
}