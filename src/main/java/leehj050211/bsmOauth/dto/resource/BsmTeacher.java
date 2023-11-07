package leehj050211.bsmOauth.dto.resource;

import leehj050211.bsmOauth.dto.raw.RawBsmOAuthResource;
import lombok.Getter;

@Getter
public class BsmTeacher {

    private String name;

    public static BsmTeacher create(RawBsmOAuthResource rawResource) {
        BsmTeacher teacher = new BsmTeacher();
        teacher.name = rawResource.getName();
        return teacher;
    }
}
