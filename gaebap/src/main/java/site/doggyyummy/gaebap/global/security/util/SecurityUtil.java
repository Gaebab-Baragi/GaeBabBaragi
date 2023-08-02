package site.doggyyummy.gaebap.global.security.util;

import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.NonLoginAuthenticator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.exception.custom.NoSuchUserException;
import site.doggyyummy.gaebap.global.security.entity.PrincipalDetails;

@Slf4j
public class SecurityUtil {
    public static Member getCurrentLoginMember() throws NoSuchUserException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            PrincipalDetails principalDetails = (PrincipalDetails) principal;
            return principalDetails.getMember();
        }
        catch (Exception e) {
            throw new NoSuchUserException("인증되지 않은 사용자");
        }
    }
}
