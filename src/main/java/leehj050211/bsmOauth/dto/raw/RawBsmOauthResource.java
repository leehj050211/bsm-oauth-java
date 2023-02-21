package leehj050211.bsmOauth.dto.raw;

import leehj050211.bsmOauth.type.BsmUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RawBsmOauthResource {

    private Long code;
    private String nickname;
    private Integer enrolledAt;
    private Integer grade;
    private Integer classNo;
    private Integer studentNo;
    private String name;
    private String email;
    private BsmUserRole role;
    private String profileUrl;

}
