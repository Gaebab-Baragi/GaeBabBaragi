package site.doggyyummy.gaebap.global.security.entity.oauth2;

import java.util.Map;

public class NaverUserInfo extends OAuth2UserInfo{
    public NaverUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) return null;
        return String.valueOf(response.get("id"));
    }

    @Override
    public String getNickname() {
         Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) return null;
        return String.valueOf(response.get("nickname"));
    }

    @Override
    public String getProfileUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) return null;
        return String.valueOf(response.get("profile_image"));
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) return null;
        return String.valueOf(response.get("email"));
    }
}
