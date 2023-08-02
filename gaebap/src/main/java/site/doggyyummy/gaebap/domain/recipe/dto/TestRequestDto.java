package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TestRequestDto {
    private String name;
    private int age;
    private List<AddressDto> addresses;

    @Getter
    public static class AddressDto{
        private String si;
        private String gu;
    }
}
