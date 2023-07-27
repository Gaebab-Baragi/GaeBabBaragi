package site.doggyyummy.gaebap.global.security.dto;

import lombok.Builder;
import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.global.security.entity.oauth2.GoogleUserInfo;
import site.doggyyummy.gaebap.global.security.entity.oauth2.KakaoUserInfo;
import site.doggyyummy.gaebap.global.security.entity.oauth2.NaverUserInfo;
import site.doggyyummy.gaebap.global.security.entity.oauth2.OAuth2UserInfo;

import java.util.Map;

@Getter
public class OAuth2Attributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuth2Attributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo){
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuth2Attributes of(SocialType socialType, String nameAttributeName, Map<String, Object> attributes){
        if (socialType == SocialType.GOOGLE) return ofGoogle(nameAttributeName, attributes);
        if (socialType == SocialType.NAVER) return ofNaver(nameAttributeName, attributes);
        if (socialType == SocialType.KAKAO) return ofKakao(nameAttributeName, attributes);
        return null;
    }

    private static OAuth2Attributes ofGoogle(String nameAttributeKey, Map<String, Object> attributes){
        return OAuth2Attributes.builder()
                .nameAttributeKey(nameAttributeKey)
                .oAuth2UserInfo(new GoogleUserInfo(attributes))
                .build();
    }

    private static OAuth2Attributes ofNaver(String nameAttributeKey, Map<String, Object> attributes){
        return OAuth2Attributes.builder()
                .nameAttributeKey(nameAttributeKey)
                .oAuth2UserInfo(new NaverUserInfo(attributes))
                .build();
    }

    private static OAuth2Attributes ofKakao(String nameAttributeKey, Map<String, Object> attributes){
        return OAuth2Attributes.builder()
                .nameAttributeKey(nameAttributeKey)
                .oAuth2UserInfo(new KakaoUserInfo(attributes))
                .build();
    }

    public Member toEntity(OAuth2UserInfo oAuth2UserInfo){
        return Member.builder()
                .username(oAuth2UserInfo.getEmail())
                .nickname(oAuth2UserInfo.getNickname())
                .profileUrl(oAuth2UserInfo.getProfileUrl())
                .build();
    }
}
