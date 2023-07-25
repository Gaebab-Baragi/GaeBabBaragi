package site.doggyyummy.gaebap.global.security.entity.oauth2;

import java.util.Map;

public abstract class OAuth2UserInfo {

    Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getNickname();
    public abstract String getProfileUrl();
    public abstract String getEmail();
}
