package leehj050211.bsmOauth.exception;

public class BsmOAuthInvalidClientException extends Exception {
    public BsmOAuthInvalidClientException() {
        super("BSM OAuth 클라이언트 정보가 잘못되었습니다.");
    }
}
