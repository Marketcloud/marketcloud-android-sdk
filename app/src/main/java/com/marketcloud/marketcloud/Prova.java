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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Prova extends AppCompatActivity {
    static JSONObject jo;
    static JSONArray ja;
    String publicKey = "86c1b899-b6ba-4dee-9cd9-995e4faa4973";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this;

        Marketcloud marketcloud = new Marketcloud(context, publicKey);

        /*String id = "8877";

        jo = marketcloud.products.getById(id);

        Log.i("id", jo + "");

        HashMap<String, Object> m = new HashMap<>();
        m.put("sku", "23u90");

        ja = marketcloud.products.list(m);

        Log.i("list", ja + "");

        boolean a = marketcloud.users.create("alex12345", "alex3@prova.it", "provaprova");

        Log.i("create", "" + a);

        boolean c = marketcloud.users.authenticate("alex3@prova.it", "provaprova");

        Log.i("auth", c + "");
*/
        //jo = marketcloud.users.update("9430", "antonio", "antonio@prova.it", "provaprova");

        //Log.i("update", jo + "");
/*
        //jo = marketcloud.users.getById("8915");

        //Log.i("users", jo + "");

*/      //boolean b = marketcloud.users.create("luca", "luca@prova.it", "provaprova");

        //Log.i("create", "" + b);

        //boolean f = marketcloud.users.authenticate("luca@prova.it", "provaprova");

        //Log.i("auth", f + "");

        //jo = marketcloud.users.getById("9430");

        //Log.i("users", jo + "");

       /* boolean d = marketcloud.users.delete("8995");

        Log.i("delete", "" + d);

        jo = marketcloud.users.getById("8995");

        Log.i("users", jo + "");*/

        //jo = marketcloud.carts.create("9438", new Object[][] {{8877, 4}});

        //Log.i("cart", jo.toString());

        /*jo = marketcloud.carts.getById("9400");

        if (jo == null) Log.i("getbyid", "null");
        else Log.i("getbyid", jo.toString());
        */

        //jo = marketcloud.carts.add("9434", new Object[][] {{8879, 1}});

        //if (jo == null) Log.i("cart", "null");
        //else Log.i("cart", jo.toString());

        //boolean b = marketcloud.carts.delete("9400");

        //Log.i("delete", b + "");

        //jo = marketcloud.carts.getById("9400");

        //if (jo == null) Log.i("getbyid", "null");
        //else Log.i("getbyid", jo.toString());

        //jo = marketcloud.addresses.create("luca", "luca@prova.it");

        //Log.i("address", jo + "");

        //jo = marketcloud.orders.create("9438", 9482, 9482, new Object[][] {{8878, 2},{8877,1}});

        //Log.i("order", jo + "");

        /*try {
            jo = marketcloud.addresses.create(new JSONObject()
                    .put("full_name", "bah")
                    .put("email", "luca@prova.it")
                    .put("country", "Italy")
                    .put("state", "Emilia-Romagna")
                    .put("city", "Bologna")
                    .put("address1", "via Oberdan, 22")
                    .put("address2", "piazza Nettuno, 1")
                    .put("postal_code", "40057")
                    .put("phone_number", "0123456789")
                    .put("alternate_phone_number", "9876543210"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("order", jo + "");

        ja = marketcloud.addresses.get();

        Log.i("array", ja + "");

        jo = marketcloud.addresses.getById("9482");

        Log.i("get", jo + "");

        boolean b = marketcloud.addresses.delete("9444");

        Log.i("delete", jo + "");

        jo = marketcloud.addresses.getById("9444");

        Log.i("get", jo + "");*/

        //ja = marketcloud.carts.getByUser("9438");

        //Log.i("list", ja + "");

        //ja = marketcloud.carts.get();

        //Log.i("list", ja + "");

        /*ja = marketcloud.orders.get();

        Log.i("list", ja + "");

        jo = marketcloud.orders.getById("9440");

        Log.i("get", jo + "");
*/
        //boolean c = marketcloud.currencies.create("Euro", "€");

        //Log.i("currency", c + "");

        //ja = marketcloud.currencies.get();

        //Log.i("list", ja.toString());
    }
}