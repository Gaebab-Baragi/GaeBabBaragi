package site.doggyyummy.gaebap.domain.meeting.service;

import io.openvidu.java.client.OpenVidu;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;
import site.doggyyummy.gaebap.domain.meeting.repository.MeetingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:openvidu.properties")
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;

    // OpenVidu 객체 SDK
    private OpenVidu openvidu;

    // OpenVidu 서버 관련 변수
    @Value("${openvidu.url}")
    private String OPENVIDU_URL;
    @Value("${openvidu.secret}")
    private String SECRET;

    @PostConstruct
    public void init() {
        // OpenVidu 설정
        this.openvidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @Override
    @Transactional
    public CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO) {
        // maxParticipant가 1 이상 4이하인지 확인 필요
        // password가 null이 아닌 경우 length가 6인지 확인 필요
        // startTime이 currentTime보다 나중인지 확인 필요

        Meeting meeting = createMeetingRequestDTO.toEntity();
        Meeting savedMeeting = meetingRepository.save(meeting);
        CreateMeetingResponseDTO createMeetingResponseDTO = CreateMeetingResponseDTO.toDTO(savedMeeting);
        return  createMeetingResponseDTO;
    }

    @Override
    @Transactional
    public ModifyMeetingResponseDTO modify(ModifyMeetingRequestDTO modifyMeetingRequestDTO) {
        // maxParticipant가 1 이상 4이하인지 확인 필요
        // password가 null이 아닌 경우 length가 6인지 확인 필요
        // startTime이 currentTime보다 나중인지 확인 필요

        Meeting meeting = modifyMeetingRequestDTO.toEntity();

        Optional<Meeting> findMeetingOptional = meetingRepository.findById(meeting.getId());

        if(findMeetingOptional != null && findMeetingOptional.isPresent()) {
            Meeting findMeeting = findMeetingOptional.get();
            findMeeting.setPassword(meeting.getPassword());
            findMeeting.setMaxParticipant(meeting.getMaxParticipant());
            findMeeting.setTitle(meeting.getTitle());
            findMeeting.setDescription(meeting.getDescription());
            findMeeting.setStartTime(meeting.getStartTime());

            ModifyMeetingResponseDTO modifyMeetingResponseDTO = ModifyMeetingResponseDTO.toDTO(findMeeting);
            return modifyMeetingResponseDTO;
        }
        else { // id에 해당하는 meeting이 없을 경우
            // Exception 처리 필요
            return null;
        }
    }

    @Override
    public void delete(Long id) {

        Optional<Meeting> findMeetingOptional = meetingRepository.findById(id);

        if(findMeetingOptional != null && findMeetingOptional.isPresent()) {
            meetingRepository.delete(findMeetingOptional.get());
        }
        else { // id에 해당하는 meeting이 없을 경우
            // Exception 처리 필요
        }
    }

    @Override
    public List<SelectByRecipeMeetingResponseDTO> selectByRecipe(Long recipeId) {
        List<Meeting> meetings = meetingRepository.findMeetingsByRecipeIdAndStatusNotOrderByStartTimeAsc(recipeId, Status.IN_PROGRESS);

        List<SelectByRecipeMeetingResponseDTO> selectByRecipeMeetingResponseDTOS = meetings.stream()
                .map(SelectByRecipeMeetingResponseDTO::toDTO)
                .collect(Collectors.toList());

        return selectByRecipeMeetingResponseDTOS;
    }
}
