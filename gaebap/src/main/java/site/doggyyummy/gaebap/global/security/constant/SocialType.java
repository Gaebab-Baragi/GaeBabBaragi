package site.doggyyummy.gaebap.global.security.constant;

public enum SocialType {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kako");

    private final String asString;
    SocialType(String asString){
       this.asString = asString;
    }

    public String toString(){
        return asString;
    }
}
