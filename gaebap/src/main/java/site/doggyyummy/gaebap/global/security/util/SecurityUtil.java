package site.doggyyummy.gaebap.global.security.util;

import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.global.security.entity.PrincipalDetails;

@Slf4j
public class SecurityUtil {
    public static Member getCurrentLoginMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication= {}", authentication);
        Object principal = authentication.getPrincipal();
        log.info("principal = {}", principal);
        PrincipalDetails principalDetails = (PrincipalDetails)principal;
        return principalDetails.getMember();

    }
}
