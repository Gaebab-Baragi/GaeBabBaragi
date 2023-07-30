package site.doggyyummy.gaebap.global.security.entity.oauth2;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickname() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (account == null || profile == null) return null;

        return (String) profile.get("nickname");
    }

    @Override
    public String getProfileUrl() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (account == null || profile == null) return null;

        return (String) profile.get("thumbnail_image_url");

    }

    @Override
    public String getEmail() {//카카오는 이메일 필수로 하려면 검수받아야 돼서 어떻게 될지는 모름
        return String.valueOf(attributes.get("email"));
    }
}
