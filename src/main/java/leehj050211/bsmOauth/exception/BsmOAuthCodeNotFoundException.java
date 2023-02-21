package leehj050211.bsmOauth.exception;

public class BsmOAuthCodeNotFoundException extends Exception {
    public BsmOAuthCodeNotFoundException() {
        super("BSM OAuth 인증 코드를 찾을 수 없습니다.");
    }
}
