package leehj050211.bsmOauth.dto.response;

public class BsmOauthTokenResponse {

    private String token;

    public BsmOauthTokenResponse() {}

    public BsmOauthTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
