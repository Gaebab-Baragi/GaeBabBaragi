package site.doggyyummy.gaebap.domain.meeting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponseDTO implements ResponseDTO{

    String message;

    public static MessageResponseDTO toDTO(String message) {
        MessageResponseDTO messageResponseDTO = MessageResponseDTO.builder()
                .message(message)
                .build();

        return messageResponseDTO;
    }
}
