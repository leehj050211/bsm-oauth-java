package leehj050211.bsmOauth.dto.resource;

import leehj050211.bsmOauth.dto.raw.RawBsmOauthResource;
import lombok.Getter;

@Getter
public class BsmStudent {

    private String name;
    private int enrolledAt;
    private int grade;
    private int classNo;
    private int studentNo;

    public static BsmStudent create(RawBsmOauthResource rawResource) {
        BsmStudent student = new BsmStudent();
        student.name = rawResource.getName();
        student.enrolledAt = rawResource.getEnrolledAt();
        student.grade = rawResource.getGrade();
        student.classNo = rawResource.getClassNo();
        student.studentNo = rawResource.getStudentNo();
        return student;
    }

}
