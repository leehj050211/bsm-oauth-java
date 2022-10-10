package leehj050211.bsmOauth;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;
import leehj050211.bsmOauth.dto.response.BsmOauthTokenResponse;
import leehj050211.bsmOauth.exceptions.BsmAuthCodeNotFoundException;
import leehj050211.bsmOauth.exceptions.BsmAuthInvalidClientException;

public class BsmOauth {
    private final String BSM_AUTH_CLIENT_ID;
    private final String BSM_AUTH_CLIENT_SECRET;
    private final String BSM_AUTH_TOKEN_URL = "https://auth.bssm.kro.kr/api/oauth/token";
    private final String BSM_AUTH_RESOURCE_URL = "https://auth.bssm.kro.kr/api/oauth/resource";

    public BsmOauth(String BSM_AUTH_CLIENT_ID, String BSM_AUTH_CLIENT_SECRET) {
        this.BSM_AUTH_CLIENT_ID = BSM_AUTH_CLIENT_ID;
        this.BSM_AUTH_CLIENT_SECRET = BSM_AUTH_CLIENT_SECRET;
    }

    public String getToken(String authCode) throws IOException, BsmAuthCodeNotFoundException, BsmAuthInvalidClientException {
        ObjectMapper objectMapper = new ObjectMapper();

        URL url = new URL(BSM_AUTH_TOKEN_URL);
        // Payload
        Map<String, String> payload = new HashMap<>();
        payload.put("clientId", BSM_AUTH_CLIENT_ID);
        payload.put("clientSecret", BSM_AUTH_CLIENT_SECRET);
        payload.put("authCode", authCode);

        String payloadStr = objectMapper.writeValueAsString(payload);

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

        return objectMapper.readValue(Objects.requireNonNull(response.toString()), BsmOauthTokenResponse.class)
                .getToken();
    }
}
