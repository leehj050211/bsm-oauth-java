package leehj050211.bsmOauth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BsmStudentResponse {

    private String name;
    private int enrolledAt;
    private int grade;
    private int classNo;
    private int studentNo;

}
