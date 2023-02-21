package leehj050211.bsmOauth.exception;

public class BsmOAuthTokenNotFoundException extends Exception {
    public BsmOAuthTokenNotFoundException() {
        super("BSM OAuth 토큰을 찾을 수 없습니다.");
    }
}
