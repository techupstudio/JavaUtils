package com.techupstudio.otc_chingy.mychurch.utils.api;

//takes am address, and post data, and returns the html as a string

import com.techupstudio.otc_chingy.mychurch.utils.api.client.sync.RestApiClient;
import com.techupstudio.otc_chingy.mychurch.utils.general.collections.JSONObject;

import java.net.HttpURLConnection;

import static com.techupstudio.otc_chingy.mychurch.utils.general.Funcs.println;

public class Main {

    public static void main(String[] args) throws Exception {

        String USERNAME = "Otc Chingy";
        String REMEMBERED_PASSWORD = "Adgjmptw111";
        final String[] ACCESSTOKEN = {""};

        Thread runner = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
            }
        });

        RestApiClient restApiClient = new RestApiClient("http://192.168.137.1:80/auth")
                .addHeader(RestApiClient.HttpRequestHeaders.KEYS.ACCEPT, RestApiClient.HttpRequestHeaders.VALUES.JSON)
                .addHeader(RestApiClient.HttpRequestHeaders.KEYS.CONTENT_TYPE, RestApiClient.HttpRequestHeaders.VALUES.JSON)
                .addJSONData("username", USERNAME).addJSONData("password", REMEMBERED_PASSWORD)
                .setOnRequestStartListener(() -> {
                    runner.run();
                })
                .setOnRequestCompleteListener(result -> {
                    runner.interrupt();
                    if (result.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        if (result.getResponse() != null) {
                            try {
                                ACCESSTOKEN[0] = result.getResponseAsJson().getString("access_token");
                            } catch (JSONObject.JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        restApiClient.executeRequestMethod(RestApiClient.RequestMethod.POST);

        restApiClient = new RestApiClient("http://192.168.137.1:80/login")
                .addHeader(RestApiClient.HttpRequestHeaders.KEYS.AUTHORIZATION, "JWT " + ACCESSTOKEN[0])
                .setOnRequestCompleteListener(new RestApiClient.OnRequestCompleteListener() {
                    @Override
                    public void onComplete(RestApiClient.APIResponseObject result) {
                        System.out.println(result);
                        try {
                            System.out.println(result.getResponseAsJson().getString("username"));
                        } catch (JSONObject.JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        restApiClient.executeRequestMethod(RestApiClient.RequestMethod.GET);

        //do deleting directory

        String a = "[1, 45, \"bernard\", \"{age, name}\", 100, [21,15]]";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set(1, 1);
        jsonObject.set(12, a);
        jsonObject.set(13, 3);

        println(jsonObject.getArray(12));


    }

}