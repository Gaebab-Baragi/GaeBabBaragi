package site.doggyyummy.gaebap.domain.meeting.service;

import site.doggyyummy.gaebap.domain.meeting.dto.*;

import java.util.List;

public interface MeetingService {

    CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO);

    ModifyMeetingResponseDTO modify(ModifyMeetingRequestDTO modifyMeetingRequestDTO);

    void delete(Long id);

    List<SelectByRecipeMeetingResponseDTO> selectByRecipe(Long recipeId);
}
