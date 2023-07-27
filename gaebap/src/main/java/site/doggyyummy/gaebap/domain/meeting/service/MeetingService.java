package site.doggyyummy.gaebap.domain.meeting.service;

import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;

import java.util.List;
import java.util.Map;

public interface MeetingService {

    CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO);

    ModifyMeetingResponseDTO modify(ModifyMeetingRequestDTO modifyMeetingRequestDTO);

    void delete(Long id);

    List<FindMeetingResponseDTO> findAll();

    List<FindMeetingResponseDTO> findByRecipe(Long recipeId);

    void startMeeting(Long id);

    FindOneMeetingResponseDTO findOne(Long id);

    Map<String, String> joinRequest(Long id, Long member_id);

    void join(Long id, Long memberId);

    void left(Long id, Long memberId);
}
