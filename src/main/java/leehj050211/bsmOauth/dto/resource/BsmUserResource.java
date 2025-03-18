package leehj050211.bsmOauth.dto.resource;

import leehj050211.bsmOauth.dto.raw.RawBsmOAuthResource;
import leehj050211.bsmOauth.type.BsmUserRole;
import lombok.Getter;

@Getter
public class BsmUserResource {

    private Long id;
    private BsmUserRole role;
    private String nickname;
    private String email;
    private BsmStudent student;
    private BsmTeacher teacher;
    private String profileUrl;

    public static BsmUserResource create(RawBsmOAuthResource rawResource) {
        BsmUserResource resource = new BsmUserResource();
        resource.id = rawResource.getId();
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

    /**
     * @deprecated 유저 식별자가 code에서 id로 변경되었습니다
     */
    @Deprecated
    public Long getUserCode() {
        return this.id;
    }

}
