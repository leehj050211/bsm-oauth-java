package leehj050211.bsmOauth.dto.resource;

import leehj050211.bsmOauth.dto.raw.RawBsmOAuthResource;
import lombok.Getter;

@Getter
public class BsmStudent {

    private String name;
    private Boolean isGraduate;
    private Integer enrolledAt;
    private Integer cardinal;
    private Integer grade;
    private Integer classNo;
    private Integer studentNo;

    public static BsmStudent create(RawBsmOAuthResource rawResource) {
        BsmStudent student = new BsmStudent();
        student.name = rawResource.getName();
        student.isGraduate = rawResource.getIsGraduate();
        student.enrolledAt = rawResource.getEnrolledAt();
        student.cardinal = rawResource.getCardinal();
        student.grade = rawResource.getGrade();
        student.classNo = rawResource.getClassNo();
        student.studentNo = rawResource.getStudentNo();
        return student;
    }

}
