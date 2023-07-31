package site.doggyyummy.gaebap.global.security.entity.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User extends DefaultOAuth2User {
    private final Member member;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, Member member) {
        super(authorities, attributes, nameAttributeKey);
        this.member = member;
    }

    @Override
    public String getName() {
        return member.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return super.getAttributes();
    }

    @Override
    public <A> A getAttribute(String name) {
        return super.getAttribute(name);
    }


}
