package leehj050211.bsmOauth.dto;

import leehj050211.bsmOauth.dto.response.BsmResourceResponse;
import leehj050211.bsmOauth.dto.response.BsmStudentResponse;
import leehj050211.bsmOauth.dto.response.BsmTeacherResponse;
import leehj050211.bsmOauth.type.BsmAuthUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BsmOauthResourceDto {

    private long code;
    private String nickname;
    private int enrolledAt;
    private int grade;
    private int classNo;
    private int studentNo;
    private String name;
    private String email;
    private BsmAuthUserRole role;

    public BsmResourceResponse toResource() {
        BsmResourceResponse.BsmResourceResponseBuilder builder = BsmResourceResponse.builder()
                .userCode(code)
                .role(role)
                .nickname(nickname)
                .email(email);

        switch (role) {
            case STUDENT:
                builder = builder.student(
                        BsmStudentResponse.builder()
                                .name(name)
                                .enrolledAt(enrolledAt)
                                .grade(grade)
                                .classNo(classNo)
                                .studentNo(studentNo)
                                .build()
                );
                break;
            case TEACHER:
                builder = builder.teacher(
                        BsmTeacherResponse.builder()
                                .name(name)
                                .build()
                );
                break;
        }
        return builder.build();
    }

}
