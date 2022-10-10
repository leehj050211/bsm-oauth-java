package leehj050211.bsmOauth.dto.response;

import leehj050211.bsmOauth.type.BsmAuthUserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BsmResourceResponse {

    private Long userCode;
    private BsmAuthUserRole role;
    private String nickname;
    private String email;
    private BsmStudentResponse student;
    private BsmTeacherResponse teacher;

}
