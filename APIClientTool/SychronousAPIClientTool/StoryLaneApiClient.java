package com.techupstudio.APIClientTool.SychronousAPIClientTool;

import static com.techupstudio.GeneralUtils.Funcs.println;

import com.techupstudio.GeneralUtils.collections.JSONData;

public class StoryLaneApiClient {

    private String HOMEURL;

    private String USERNAME, PASSWORD;

    private int responseCode;
    private String responseData;
    private String responseMessage;

    public StoryLaneApiClient(String homeURL){ setHomeUrl(homeURL); }

    public String getResponseMessage() { return responseMessage; }

    public String getResponseData() { return responseData; }

    public int getResponseCode() { return responseCode; }

    public JSONData getResponseDataAsJSONObject() throws JSONData.JSONException {
        return new JSONData(getResponseData());
    }

    public JSONData getResponseInfo() {
        JSONData response = new JSONData();
        response.add("code", getResponseCode());
        response.add("message",getResponseMessage());
        response.add("result", getResponseData());
        return response;
    }

    private void prepareResult(RestApiClient client){
        if (client != null) {
            responseCode = client.getResponseCode();
            responseData = client.getResponseData();
            responseMessage = client.getResponseMessage();
        }
    }

    protected String getUrlForRoute(String route){ return HOMEURL+route; }

    private void setHomeUrl(String homeUrl){ HOMEURL = homeUrl; }

    protected void setUserDetails(String username, String password){ USERNAME = username;PASSWORD = password; }


    private String authenticateUser() throws Exception {

        RestApiClient client = new RestApiClient(getUrlForRoute("/auth"));
        client.addHeader(RestApiClient.HttpRequestHeaders.ACCEPT, RestApiClient.HttpRequestHeaders.JSON);
        client.addHeader(RestApiClient.HttpRequestHeaders.CONTENT_TYPE, RestApiClient.HttpRequestHeaders.JSON);

        client.addJSONData("username", USERNAME);
        client.addJSONData("password", PASSWORD);

        client.executeRequestMethod(RestApiClient.RequestMethod.POST);
        prepareResult(client);

        if (client.getResponseCode() == 200) {
            JSONData auth_token = client.getApiResponseObject().getResponseAsJson();
            return auth_token.getString("access_token");
        }
        return null;
    }

    private RestApiClient getAuthenticatedClient(){

        RestApiClient client = new RestApiClient();
        try {
            String access_token = authenticateUser();
            if (access_token != null) {
                client.addHeader(RestApiClient.HttpRequestHeaders.AUTHORIZATION, "JWT " + access_token);
                return client;
            }
        } catch (Exception e) {
            System.out.println("Connection Error ::  Failed to Connect!!!");
            e.printStackTrace();
        }
        return null;
    }

    private RestApiClient getAuthenticatedClientForRoute(String route){
        RestApiClient client = getAuthenticatedClient();
        if (client != null) { client.setURL(getUrlForRoute(route)); }
        return client;
    }

    protected RestApiClient sendRequestToRoute(String route, RestApiClient.RequestMethod method, JSONData... jsonData){
        RestApiClient client = getAuthenticatedClientForRoute(route);
        if (client != null) {
            if (jsonData.length == 1) {
                client.addHeader(RestApiClient.HttpRequestHeaders.ACCEPT, RestApiClient.HttpRequestHeaders.JSON);
                client.addHeader(RestApiClient.HttpRequestHeaders.CONTENT_TYPE, RestApiClient.HttpRequestHeaders.JSON);
                client.setJSONData(jsonData[0]);
            }
            try {
                client.executeRequestMethod(method);
                prepareResult(client);
            } catch (Exception e) {
                println(client.getApiResponseObject());
                e.printStackTrace();
            }
        }
        return client;
    }

    protected RestApiClient postJsonToRoute(String route, JSONData object){
        RestApiClient client = getAuthenticatedClientForRoute(route);
        if (client != null) {
            client.addHeader(RestApiClient.HttpRequestHeaders.ACCEPT, RestApiClient.HttpRequestHeaders.JSON);
            client.addHeader(RestApiClient.HttpRequestHeaders.CONTENT_TYPE, RestApiClient.HttpRequestHeaders.JSON);
            client.setJSONData(object);
            try {
                client.executeRequestMethod(RestApiClient.RequestMethod.POST);
                prepareResult(client);
            } catch (Exception e) {
                println(client.getApiResponseObject());
                e.printStackTrace();
            }
        }
        return client;
    }

    protected RestApiClient sendJsonToRoute(String route, JSONData object, RestApiClient.RequestMethod method){
        RestApiClient client = getAuthenticatedClientForRoute(route);
        if (client != null) {
            client.addHeader(RestApiClient.HttpRequestHeaders.ACCEPT, RestApiClient.HttpRequestHeaders.JSON);
            client.addHeader(RestApiClient.HttpRequestHeaders.CONTENT_TYPE, RestApiClient.HttpRequestHeaders.JSON);
            client.setJSONData(object);
            try {
                client.executeRequestMethod(method);
                prepareResult(client);
            } catch (Exception e) {
                println(client.getApiResponseObject());
                e.printStackTrace();
            }
        }
        return client;
    }

    protected JSONData getJsonFromRoute(String route, RestApiClient.RequestMethod method){
        RestApiClient client = getAuthenticatedClientForRoute(route);
        if (client != null) {
            try {
                client.executeRequestMethod(method);
                prepareResult(client);
                if (client.getResponseCode() == 200) {
                    if (client.hasResponseData()) {
                        return client.getApiResponseObject().getResponseAsJson();
                    }
                }
            } catch (Exception e) {
                println(client.getApiResponseObject());
                e.printStackTrace();
            }
        }
        return null;
    }


    protected JSONData processJsonAtProcessorRoute(JSONData object){

        RestApiClient client = postJsonToRoute("/process",object);
        if (client != null) {
            prepareResult(client);
            try {
                if (client.getResponseCode() == 200) {
                    if (client.hasResponseData()) {
                        return client.getApiResponseObject().getResponseAsJson();
                    }
                }
            } catch (Exception e) {
                System.out.println(client.getApiResponseObject());
                e.printStackTrace();
            }
        }
        return null;
    }

}
