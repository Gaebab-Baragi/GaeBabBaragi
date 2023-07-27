package site.doggyyummy.gaebap.global.security.dto;

public enum SocialType {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String asString;
    SocialType(String asString){
       this.asString = asString;
    }

    public String toString(){
        return asString;
    }
}
