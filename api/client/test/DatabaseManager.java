package com.techupstudio.otc_chingy.mychurch.utils.api.client.test;


import com.techupstudio.otc_chingy.mychurch.utils.api.client.sync.RestApiClient;
import com.techupstudio.otc_chingy.mychurch.utils.general.collections.JSONObject;

public class DatabaseManager extends TechupApiClient {

    private int responseCode;
    private String responseData;
    private String responseMessage;

    public DatabaseManager(String username, String password) {
        super("http://127.0.0.1:5000");
        setUserDetails(username, password);
    }

    public boolean requestSuccessful() {
        return getResponseCode() == 200;
    }

    public boolean hasResponseData() {
        return getResponseData() != null;
    }

    protected String getUrlForRoute(String route) {
        return super.getUrlForRoute(route);
    }

    protected JSONObject getJsonFromRoute(String route, RestApiClient.RequestMethod method) {
        return super.getJsonFromRoute(route, method);
    }

    protected RestApiClient postJsonToRoute(String route, JSONObject jsonObject) {
        return super.postJsonToRoute(route, jsonObject);
    }

    protected RestApiClient sendJsonToRoute(String route, JSONObject jsonObject, RestApiClient.RequestMethod method) {
        return super.sendJsonToRoute(route, jsonObject, method);
    }

    protected JSONObject processJsonAtProcessorRoute(JSONObject jsonObject) {
        return super.processJsonAtProcessorRoute(jsonObject);
    }

    protected RestApiClient sendRequestToRoute(String route, RestApiClient.RequestMethod method, JSONObject... jsonData) {
        return super.sendRequestToRoute(route, method, jsonData);
    }

}
