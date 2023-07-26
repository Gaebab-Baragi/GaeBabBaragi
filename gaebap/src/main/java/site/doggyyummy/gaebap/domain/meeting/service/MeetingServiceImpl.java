package site.doggyyummy.gaebap.domain.meeting.service;

import io.openvidu.java.client.OpenVidu;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingResponseDTO;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.repository.MeetingRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

@Service
@PropertySource("classpath:openvidu.properties")
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    // OpenVidu 객체 SDK
    private OpenVidu openvidu;

    // OpenVidu 서버 관련 변수
    @Value("${openvidu.url}")
    private String OPENVIDU_URL;
    @Value("${openvidu.secret}")
    private String SECRET;

    @Autowired
    public MeetingServiceImpl(MeetingRepository meetingRepository, MemberRepository memberRepository) {
        this.meetingRepository = meetingRepository;
        this.memberRepository = memberRepository;

        this.openvidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @PostConstruct // db에 덤프 데이터 넣어놓기
    public void init() {
        Member member = new Member();
        member.setName("yoo");
        memberRepository.save(member);
    }

    @Transactional
    public CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO) {
        Meeting meeting = createMeetingRequestDTO.toEntity();
        Meeting savedMeeting = meetingRepository.save(meeting);
        CreateMeetingResponseDTO createMeetingResponseDTO = CreateMeetingResponseDTO.toDTO(meeting);
        return  createMeetingResponseDTO;
    }
}
