package com.techupstudio.APIClientTool.SychronousAPIClientTool;


import com.techupstudio.GeneralUtils.collections.JSONData;

public class DatabaseManager extends StoryLaneApiClient {

    private int responseCode;
    private String responseData;
    private String responseMessage;

    public DatabaseManager(String username, String password){
        super("http://127.0.0.1:5000");
        setUserDetails(username,password);
    }

    public boolean requestSuccessful() { return getResponseCode() == 200; }

    public boolean hasResponseData() { return getResponseData() != null; }

    protected String getUrlForRoute(String route){ return super.getUrlForRoute(route); }

    protected JSONData getJsonFromRoute(String route, RestApiClient.RequestMethod method){
        return super.getJsonFromRoute(route, method);
    }

    protected RestApiClient postJsonToRoute(String route, JSONData jsonData){
        return super.postJsonToRoute(route, jsonData);
    }

    protected RestApiClient sendJsonToRoute(String route, JSONData jsonData, RestApiClient.RequestMethod method){
        return super.sendJsonToRoute(route, jsonData, method);
    }

    protected JSONData processJsonAtProcessorRoute(JSONData jsonData){
        return super.processJsonAtProcessorRoute(jsonData);
    }

    protected RestApiClient sendRequestToRoute(String route, RestApiClient.RequestMethod method, JSONData... jsonData) {
        return super.sendRequestToRoute(route, method, jsonData);
    }

}
