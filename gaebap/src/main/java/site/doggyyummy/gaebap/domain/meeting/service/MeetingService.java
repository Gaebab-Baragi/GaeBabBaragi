package site.doggyyummy.gaebap.domain.meeting.service;

import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingResponseDTO;

public interface MeetingService {

    public CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO);
}
