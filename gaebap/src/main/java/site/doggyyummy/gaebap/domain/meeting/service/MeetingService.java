package site.doggyyummy.gaebap.domain.meeting.service;

import site.doggyyummy.gaebap.domain.meeting.dto.MakeMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;

public interface MeetingService {

    public void makeMeeting(String sessionId, final MakeMeetingRequestDTO makeMeetingRequestDTO);
}
