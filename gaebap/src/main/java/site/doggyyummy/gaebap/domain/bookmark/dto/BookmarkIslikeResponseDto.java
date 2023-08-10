package site.doggyyummy.gaebap.domain.bookmark.dto;

import lombok.Getter;

@Getter
public class BookmarkIslikeResponseDto {
    int flag;
    public BookmarkIslikeResponseDto(int flag){
        this.flag=flag;
    }
}
