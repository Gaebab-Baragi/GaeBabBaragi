package site.doggyyummy.gaebap.domain.meeting.service;

import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;

import java.util.List;
import java.util.Map;

public interface MeetingService {

    CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO, Long hostId);

    ModifyMeetingResponseDTO modify(ModifyMeetingRequestDTO modifyMeetingRequestDTO, Long hostId);

    void delete(Long id, Long hostId);

    List<FindMeetingResponseDTO> findAll();

    List<FindMeetingResponseDTO> findByRecipe(Long recipeId);

    void startMeeting(Long id, Long hostId);

    FindOneMeetingResponseDTO findOne(Long id);

    List<FindMeetingResponseDTO> findByMember(Long memberId);

    MessageResponseDTO joinRequest(Long id, Long member_id);

    void join(Long id, Long memberId);

    void left(Long id, Long memberId);
}
