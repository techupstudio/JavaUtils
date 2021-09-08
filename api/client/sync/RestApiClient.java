package com.techupstudio.otc_chingy.mychurch.core.utils.api.client.sync;

import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.format;
import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.range;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.JSONObject;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.KeyValuePair;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.XMLObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RestApiClient {

    private ArrayList<StringKeyValuePair> HEADERS;
    private ArrayList<StringKeyValuePair> PARAMS;
    private ArrayList<String> RAW_DATA;
    private JSONObject RAW_JSON_DATA;
    private XMLObject RAW_XML_DATA;

    private int CONNECTION_TIMEOUT = 15000;
    private int READ_TIMEOUT = 15000;
    private String baseUrlString;
    private int responseCode;
    private String responseMessage;
    private String responseData;

    private APIResponseObject apiResponseObject;

    private OnRequestCompleteListener requestCompleteListener;
    private OnRequestStartListener requestStartListener;


    public RestApiClient() {
        initialize();
    }

    public RestApiClient(String url) {
        setURL(url);
        initialize();
    }

    public RestApiClient(String url, OnExecuteRequestListener listener) {
        setOnExecuteRequestListener(listener);
        setURL(url);
        initialize();
    }

    public static synchronized String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void initialize() {
        PARAMS = new ArrayList<>();
        HEADERS = new ArrayList<>();
        RAW_JSON_DATA = new JSONObject();
        RAW_XML_DATA = new XMLObject();
        RAW_DATA = new ArrayList<>();
    }

    public void setURL(String url) {
        this.baseUrlString = url.trim().replace(" ", "%20");
    }

    private RestApiClient setOnExecuteRequestListener(final OnExecuteRequestListener listener) {
        this.requestCompleteListener = apiResponseObject -> listener.onComplete(apiResponseObject);

        this.requestStartListener = () -> listener.onStart();

        return this;
    }

    public RestApiClient setOnRequestCompleteListener(OnRequestCompleteListener listener) {
        this.requestCompleteListener = listener;
        return this;
    }

    public RestApiClient setOnRequestStartListener(OnRequestStartListener listener) {
        this.requestStartListener = listener;
        return this;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public APIResponseObject getApiResponseObject() {
        return apiResponseObject;
    }

    public String getResponseData() {
        return responseData;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public RestApiClient addParam(String name, String value) {
        PARAMS.add(new StringKeyValuePair(name.trim(), value.trim()));
        return this;
    }

    public RestApiClient addHeader(String name, String value) {
        HEADERS.add(new StringKeyValuePair(name.trim(), value.trim()));
        return this;
    }

    public RestApiClient addJSONData(String name, String value) {
        RAW_JSON_DATA.set(name.trim(), value.trim());
        return this;
    }

    public RestApiClient addRAWData(String data) {
        RAW_DATA.add(data.trim());
        return this;
    }

    public JSONObject getJSONData() {
        return RAW_JSON_DATA;
    }

    public void setJSONData(JSONObject jsonObject) {
        RAW_JSON_DATA = jsonObject;
    }

    public XMLObject getXMLData() {
        return RAW_XML_DATA;
    }

    public void setXMLData(XMLObject xmlData) {
        RAW_XML_DATA = xmlData;
    }

    public RequestXMLDataAdder addXMLData() {
        return new RequestXMLDataAdder();
    }

    public int getConnectTimeout() {
        return CONNECTION_TIMEOUT;
    }

    public void setConnectTimeout(int timeout) {
        CONNECTION_TIMEOUT = timeout;
    }

    public int getReadTimeout() {
        return READ_TIMEOUT;
    }

    public void setReadTimeout(int timeout) {
        READ_TIMEOUT = timeout;
    }

    public boolean hasResponseData() {
        return apiResponseObject.getResponse() != null;
    }

    private boolean argumentsNotEmpty() {
        return (!RAW_XML_DATA.isEmpty() || !RAW_JSON_DATA.isEmpty() || !RAW_DATA.isEmpty());
    }

    protected APIResponseObject doInBackground(RequestMethod... params) {
        return null;
    }

    protected void onPostExecute() {
        if (requestCompleteListener != null) {
            requestCompleteListener.onComplete(getApiResponseObject());
        }
    }

    public synchronized void executeRequestMethod(RequestMethod method) throws Exception {
        URL baseURL = new URL(baseUrlString);

        //put parameters
        if (!PARAMS.isEmpty()) {

            StringBuilder combinedParams = new StringBuilder();

            combinedParams.append("?");

            for (StringKeyValuePair p : PARAMS) {
                String paramString = p.getKey() + "=" + p.getValue();  //|| URLEncoder.encode(p.getValue(), "UTF-8");
                if (combinedParams.length() > 1) {
                    combinedParams.append("&").append(paramString);
                } else {
                    combinedParams.append(paramString);
                }
            }
            combinedParams = new StringBuilder(combinedParams.toString().replace(" ", "%20"));
            baseURL = new URL(baseUrlString + combinedParams);
        }

        HttpURLConnection connection = (HttpURLConnection) baseURL.openConnection();

        switch (method) {
            case GET:
                connection.setRequestMethod(HttpRequestMethod.GET);
                break;
            case POST:
                connection.setRequestMethod(HttpRequestMethod.POST);
                break;
            case DELETE:
                connection.setRequestMethod(HttpRequestMethod.DELETE);
                break;
            case PUT:
                connection.setRequestMethod(HttpRequestMethod.PUT);
                break;
            case HEAD:
                connection.setRequestMethod(HttpRequestMethod.HEAD);
                break;
            case OPTIONS:
                connection.setRequestMethod(HttpRequestMethod.OPTIONS);
                break;
            default:
                connection.setRequestMethod(HttpRequestMethod.GET);
                break;
        }

        connection.setReadTimeout(getConnectTimeout());
        connection.setConnectTimeout(getReadTimeout());
        connection.setDoInput(true);
        connection.setDoOutput(true);

        if (!HEADERS.isEmpty()) {
            for (StringKeyValuePair r : HEADERS) {
                connection.addRequestProperty(r.getKey(), r.getValue());
            }
        }

        if (argumentsNotEmpty()) {

            JSONObject DATA = new JSONObject();
            JSONObject raw_data = new JSONObject();

            //String encodedData = URLEncoder.encode(RAW_JSON_DATA.toString(), "UTF-8");

            if (!RAW_JSON_DATA.isEmpty()) {
                DATA.set("json", RAW_JSON_DATA.toString());
            }

            if (!RAW_XML_DATA.isEmpty()) {
                DATA.set("xml", RAW_XML_DATA.toString());
            }

            if (!RAW_DATA.isEmpty()) {
                for (int i : range(RAW_DATA.size())) {
                    raw_data.set("data" + i, raw_data.get(i));
                }
                DATA.set("data", raw_data.toString());
            }

            OutputStream os = connection.getOutputStream();

            if (!DATA.isEmpty()) {
                if (DATA.size() == 1) {
                    switch (DATA.keys().get(0).toString()) {
                        case "json":
                            os.write(RAW_JSON_DATA.toString().getBytes());
                            break;
                        case "xml":
                            os.write(RAW_XML_DATA.toString().getBytes());
                            break;
                        default:
                            os.write(raw_data.toString().getBytes());
                            break;
                    }
                } else {
                    os.write(DATA.toString().getBytes());
                }
            }

            os.flush();
            os.close();
        }

        executeRequest(connection);
    }

    private synchronized void executeRequest(HttpURLConnection connection) throws IOException {
        String result = null;
        connection.connect();
        responseCode = connection.getResponseCode();
        responseMessage = connection.getResponseMessage();
        if (responseCode == 200) {
            result = convertStreamToString(connection.getInputStream());
        }
        connection.disconnect();
        responseData = result;

        apiResponseObject = new APIResponseObject(getResponseCode(), getResponseMessage(), getResponseData());

        onPostExecute();
    }

    public String toString() {
        return format("RestApiClient(<>)", baseUrlString);
    }

    public enum RequestMethod {
        GET, POST, _UPDATE, DELETE, PUT, _PATCH, _COPY, HEAD, _VIEW,
        OPTIONS, _LINK, _UNLINK, _PURGE, _LOCK, _UNLOCK, _PROPFIND
    }

    public interface OnExecuteRequestListener extends OnRequestCompleteListener, OnRequestStartListener {

    }

    public interface OnRequestCompleteListener {
        void onComplete(APIResponseObject result);
    }

    public interface OnRequestStartListener {
        void onStart();
    }

    public interface HttpRequestMethod {
        String GET = "GET";
        String PUT = "PUT";
        String POST = "POST";
        String _LOCK = "LOCK";
        String _COPY = "COPY";
        String HEAD = "HEAD";
        String _VIEW = "VIEW";
        String _LINK = "LINK";
        String _PATCH = "PATCH";
        String _PURGE = "PURGE";
        String _UPDATE = "UPDATE";
        String DELETE = "DELETE";
        String _UNLINK = "UNLINK";
        String _UNLOCK = "UNLOCK";
        String OPTIONS = "OPTIONS";
        String _PROPFIND = "PROPFIND";
    }

    public interface HttpRequestHeaders {

        interface KEYS {
            String ACCEPT = "Accept";
            String CONTENT_TYPE = "Content-Type";
            String AUTHORIZATION = "Authorization";
            String ACCEPT_ENCODING = "Accept-Encoding";
            String CONTENT_ENCODING = "Content-Encoding";
        }

        interface VALUES {
            String ENCODING_GZIP = "gzip";
            String JSON = "application/json";
            String MIME_TEXT_PLAIN = "text/plain";
            String MIME_FORM_ENCODED = "application/x-ww-form-urlencoded";
        }
    }

    public interface HttpResponseCodes {
        int OK = 200;
        int BAD_REQUEST = 400;
        int UNAUTHORIZED = 401;
        int NOT_FOUND = 404;
        int METHOD_NOT_ALLOWED = 405;
        int INTERNAL_SERVER_ERROR = 500;
    }

    public static class APIResponseObject {
        private final int responseCode;
        private final String response;
        private final String responseMessage;

        APIResponseObject(int responseCode, String responseMessage, String response) {
            this.responseMessage = responseMessage;
            this.responseCode = responseCode;
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public boolean hasResponse() {
            return response != null;
        }

        public String getResponseMessage() {
            return responseMessage;
        }

        public JSONObject getResponseAsJson() throws JSONObject.JSONException {
            return new JSONObject(response);
        }

        public String toString() {
            JSONObject response = new JSONObject();
            response.set("code", getResponseCode());
            response.set("message", getResponseMessage());
            response.set("result", getResponse());
            return response.toString();
        }
    }

    static class StringKeyValuePair extends KeyValuePair<String, String> {
        public StringKeyValuePair(String key, String value) {
            super(key, value);
        }
    }

    public class RequestXMLDataAdder {

        public RequestXMLDataAdder() {
        }

        public RequestXMLDataAdder addXMLChild(XMLObject child) {
            RAW_XML_DATA.addChildren(child);
            return new RequestXMLDataAdder();
        }

        public RequestXMLDataAdder addXMLChildren(XMLObject... child) {
            RAW_XML_DATA.addChildren(child);
            return new RequestXMLDataAdder();
        }
    }

}
