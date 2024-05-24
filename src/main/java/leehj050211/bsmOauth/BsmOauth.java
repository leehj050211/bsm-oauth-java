package leehj050211.bsmOauth;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import leehj050211.bsmOauth.dto.raw.RawBsmOAuthResource;
import leehj050211.bsmOauth.dto.raw.RawBsmOAuthToken;
import leehj050211.bsmOauth.dto.resource.BsmUserResource;
import leehj050211.bsmOauth.exception.BsmOAuthCodeNotFoundException;
import leehj050211.bsmOauth.exception.BsmOAuthInvalidClientException;
import leehj050211.bsmOauth.exception.BsmOAuthTokenNotFoundException;

public class BsmOauth {

    private final String BSM_AUTH_CLIENT_ID;
    private final String BSM_AUTH_CLIENT_SECRET;
    private final String BSM_AUTH_TOKEN_URL = "https://api-auth.bssm.app/api/oauth/token";
    private final String BSM_AUTH_RESOURCE_URL = "https://api-auth.bssm.app/api/oauth/resource";
    private final Gson gson;

    public BsmOauth(String BSM_AUTH_CLIENT_ID, String BSM_AUTH_CLIENT_SECRET) {
        this.gson = new Gson();
        this.BSM_AUTH_CLIENT_ID = BSM_AUTH_CLIENT_ID;
        this.BSM_AUTH_CLIENT_SECRET = BSM_AUTH_CLIENT_SECRET;
    }

    public String getToken(String authCode) throws IOException, BsmOAuthCodeNotFoundException, BsmOAuthInvalidClientException {
        // Payload
        JsonObject payload = new JsonObject();
        payload.addProperty("clientId", BSM_AUTH_CLIENT_ID);
        payload.addProperty("clientSecret", BSM_AUTH_CLIENT_SECRET);
        payload.addProperty("authCode", authCode);

        // Request
        HttpURLConnection conn = httpRequest(BSM_AUTH_TOKEN_URL, payload);
        if (conn.getResponseCode() == 404) {
            conn.disconnect();
            throw new BsmOAuthCodeNotFoundException();
        }
        String response = toHttpResponse(conn);

        return gson.fromJson(response, RawBsmOAuthToken.class)
                .getToken();
    }

    public BsmUserResource getResource(String token) throws IOException, BsmOAuthTokenNotFoundException, BsmOAuthInvalidClientException {
        RawBsmOAuthResource rawResource = this.getRawResource(token);
        return BsmUserResource.create(rawResource);
    }

    public RawBsmOAuthResource getRawResource(String token) throws IOException, BsmOAuthTokenNotFoundException, BsmOAuthInvalidClientException {
        // Payload
        JsonObject payload = new JsonObject();
        payload.addProperty("clientId", BSM_AUTH_CLIENT_ID);
        payload.addProperty("clientSecret", BSM_AUTH_CLIENT_SECRET);
        payload.addProperty("token", token);

        // Request
        HttpURLConnection conn = httpRequest(BSM_AUTH_RESOURCE_URL, payload);
        if (conn.getResponseCode() == 404) {
            conn.disconnect();
            throw new BsmOAuthTokenNotFoundException();
        }
        String response = toHttpResponse(conn);

        JsonElement element = JsonParser.parseString(response)
                .getAsJsonObject().get("user");
        return gson.fromJson(element, RawBsmOAuthResource.class);
    }

    private HttpURLConnection httpRequest(String urlStr, JsonObject payload) throws IOException, BsmOAuthInvalidClientException {
        URL url = new URL(urlStr);
        String payloadStr = gson.toJson(payload);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", Integer.toString(payloadStr.length()));
        conn.setUseCaches(false);

        DataOutputStream stream = new DataOutputStream(conn.getOutputStream());
        stream.writeBytes(payloadStr);

        // Error check
        if (conn.getResponseCode() == 400) {
            conn.disconnect();
            throw new BsmOAuthInvalidClientException();
        }
        return conn;
    }

    private String toHttpResponse(HttpURLConnection conn) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        conn.disconnect();
        reader.close();

        return response.toString();
    }
}
