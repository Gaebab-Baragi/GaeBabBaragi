package site.doggyyummy.gaebap.global.security.service.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.entity.Role;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.dto.SocialType;
import site.doggyyummy.gaebap.global.security.dto.OAuth2Attributes;
import site.doggyyummy.gaebap.global.security.entity.oauth2.CustomOAuth2User;
import site.doggyyummy.gaebap.global.security.util.PasswordUtil;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultUserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultUserService.loadUser(userRequest);

        SocialType socialType = SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId());
        String nameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, nameAttributeName, attributes);

        Member member = getMember(oAuth2Attributes);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
                attributes,
                oAuth2Attributes.getNameAttributeKey(),
                member
        );
    }

    private Member getMember(OAuth2Attributes attributes){
        String email = attributes.getOAuth2UserInfo().getEmail();
        log.info("email : {}", email);

        Member findMember = memberRepository.findByUsername(email).orElse(null);
        if (findMember == null){
            return createUser(attributes);
        }
        return findMember;
    }

    private Member createUser(OAuth2Attributes attributes){
       Member member = attributes.toEntity(attributes.getOAuth2UserInfo());
       Member saved;
       if (member.getPassword() == null){
           member.setPassword(PasswordUtil.generateRandomPassword());
       }
       if (!memberRepository.existsByNickname(member.getNickname())){
           member.setRole(Role.USER);
           saved = memberRepository.save(member);
       }
       else {
           member.setNickname(UUID.randomUUID().toString());
           saved = memberRepository.save(member);
       }
       return saved;
    }
}
