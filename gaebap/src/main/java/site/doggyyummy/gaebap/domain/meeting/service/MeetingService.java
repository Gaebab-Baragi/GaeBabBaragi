package site.doggyyummy.gaebap.domain.meeting.service;

import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingResponseDTO;
import site.doggyyummy.gaebap.domain.meeting.dto.ModifyMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.dto.ModifyMeetingResponseDTO;

public interface MeetingService {

    CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO);

    ModifyMeetingResponseDTO modify(ModifyMeetingRequestDTO modifyMeetingRequestDTO);

    void delete(Long id);
}
