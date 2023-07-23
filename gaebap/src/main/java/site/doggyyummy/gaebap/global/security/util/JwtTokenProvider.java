package site.doggyyummy.gaebap.global.security.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenProvider {

    @Value("{jwt.secretKey}")
    private String secretKey;

    @Value("{jwt.access.expiration}")
    private Long accessExpiration;

    @PostConstruct
    protected void init(){

    }

    public JwtTokenProvider(@Value("${jwt.secretKey") String secretKey){
       byte[] secretByteKey = Datatyp


    }
}
