package leehj050211.bsmOauth.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BsmOauthTokenDto {

    private String token;

    public String getToken() {
        return token;
    }

}
