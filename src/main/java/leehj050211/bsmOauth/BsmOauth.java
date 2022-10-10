package leehj050211.bsmOauth;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import leehj050211.bsmOauth.dto.BsmOauthResourceDto;
import leehj050211.bsmOauth.dto.BsmOauthTokenDto;
import leehj050211.bsmOauth.dto.response.BsmResourceResponse;
import leehj050211.bsmOauth.exceptions.BsmAuthCodeNotFoundException;
import leehj050211.bsmOauth.exceptions.BsmAuthInvalidClientException;
import leehj050211.bsmOauth.exceptions.BsmAuthTokenNotFoundException;

public class BsmOauth {

    private final String BSM_AUTH_CLIENT_ID;
    private final String BSM_AUTH_CLIENT_SECRET;
    private final String BSM_AUTH_TOKEN_URL = "https://auth.bssm.kro.kr/api/oauth/token";
    private final String BSM_AUTH_RESOURCE_URL = "https://auth.bssm.kro.kr/api/oauth/resource";
    private final Gson gson;

    public BsmOauth(String BSM_AUTH_CLIENT_ID, String BSM_AUTH_CLIENT_SECRET) {
        this.gson = new Gson();
        this.BSM_AUTH_CLIENT_ID = BSM_AUTH_CLIENT_ID;
        this.BSM_AUTH_CLIENT_SECRET = BSM_AUTH_CLIENT_SECRET;
    }

    public String getToken(String authCode) throws IOException, BsmAuthCodeNotFoundException, BsmAuthInvalidClientException {
        URL url = new URL(BSM_AUTH_TOKEN_URL);
        // Payload
        JsonObject payload = new JsonObject();
        payload.addProperty("clientId", BSM_AUTH_CLIENT_ID);
        payload.addProperty("clientSecret", BSM_AUTH_CLIENT_SECRET);
        payload.addProperty("authCode", authCode);
        String payloadStr = gson.toJson(payload);

        // Request
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
            throw new BsmAuthInvalidClientException();
        }
        if (conn.getResponseCode() == 404) {
            conn.disconnect();
            throw new BsmAuthCodeNotFoundException();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        conn.disconnect();
        reader.close();

        return gson.fromJson(Objects.requireNonNull(response.toString()), BsmOauthTokenDto.class)
                .getToken();
    }

    public BsmResourceResponse getResource(String token) throws IOException, BsmAuthTokenNotFoundException, BsmAuthInvalidClientException {
        URL url = new URL(BSM_AUTH_RESOURCE_URL);
        // Payload
        JsonObject payload = new JsonObject();
        payload.addProperty("clientId", BSM_AUTH_CLIENT_ID);
        payload.addProperty("clientSecret", BSM_AUTH_CLIENT_SECRET);
        payload.addProperty("token", token);
        String payloadStr = gson.toJson(payload);

        // Request
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
            throw new BsmAuthInvalidClientException();
        }
        if (conn.getResponseCode() == 404) {
            conn.disconnect();
            throw new BsmAuthTokenNotFoundException();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        conn.disconnect();
        reader.close();

        JsonElement element = JsonParser.parseString(response.toString())
                .getAsJsonObject().get("user");
        BsmOauthResourceDto rawResource = new Gson().fromJson(element, BsmOauthResourceDto.class);

        return rawResource.toResource();
    }
}
