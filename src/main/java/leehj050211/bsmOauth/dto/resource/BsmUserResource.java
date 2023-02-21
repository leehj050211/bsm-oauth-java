package leehj050211.bsmOauth.dto.resource;

import leehj050211.bsmOauth.dto.raw.RawBsmOauthResource;
import leehj050211.bsmOauth.type.BsmUserRole;
import lombok.Getter;

@Getter
public class BsmUserResource {

    private Long userCode;
    private BsmUserRole role;
    private String nickname;
    private String email;
    private BsmStudent student;
    private BsmTeacher teacher;
    private String profileUrl;

    public static BsmUserResource create(RawBsmOauthResource rawResource) {
        BsmUserResource resource = new BsmUserResource();
        resource.userCode = rawResource.getCode();
        resource.role = rawResource.getRole();
        resource.nickname = rawResource.getNickname();
        resource.email = rawResource.getEmail();
        resource.profileUrl = rawResource.getProfileUrl();
        if (resource.role == BsmUserRole.STUDENT) {
            resource.student = BsmStudent.create(rawResource);
        }
        if (resource.role == BsmUserRole.TEACHER) {
            resource.teacher = BsmTeacher.create(rawResource);
        }
        return resource;
    }

}
