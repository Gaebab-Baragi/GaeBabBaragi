package site.doggyyummy.gaebap.domain.meeting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.meeting.dto.MakeMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.repository.MeetingRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

@Service
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public MeetingServiceImpl(MeetingRepository meetingRepository, MemberRepository memberRepository) {
        this.meetingRepository = meetingRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void makeMeeting(String sessionId, final MakeMeetingRequestDTO makeMeetingRequestDTO) {
        // 로그인 기능 구현 시 로그인 되어 있는 사용자를 가지고 오는 것으로 대체
        Member loginUser = new Member();
        loginUser.setName("승아");
        memberRepository.save(loginUser);

        // 미팅 객체 생성
        Meeting meeting = Meeting.builder()
                .password(makeMeetingRequestDTO.getPassword())
                .maxParticipant(makeMeetingRequestDTO.getMaxParticipant())
                .title(makeMeetingRequestDTO.getTitle())
                .description(makeMeetingRequestDTO.getDescription())
                .host(loginUser)
                .startTime(makeMeetingRequestDTO.getStartTime())
//                .recipe(makeMeetingRequestDTO.getRecipe())
                .sessionId(sessionId)
                .build();

        meetingRepository.save(meeting);
    }
}
