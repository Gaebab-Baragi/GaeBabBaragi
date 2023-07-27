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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:openvidu.properties")
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;

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
    @Transactional
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
    public List<FindMeetingResponseDTO> findAll() {
        List<Meeting> meetings = meetingRepository.findAll();

        List<FindMeetingResponseDTO> findMeetingResponseDTOS = meetings.stream()
                .map(FindMeetingResponseDTO::toDTO)
                .collect(Collectors.toList());

        return findMeetingResponseDTOS;
    }

    @Override
    public List<FindMeetingResponseDTO> findByRecipe(Long recipeId) {
        List<Meeting> meetings = meetingRepository.findMeetingsByRecipeIdAndStatusNotOrderByStartTimeAsc(recipeId, Status.IN_PROGRESS);

        List<FindMeetingResponseDTO> findMeetingResponseDTOS = meetings.stream()
                .map(FindMeetingResponseDTO::toDTO)
                .collect(Collectors.toList());

        return findMeetingResponseDTOS;
    }

    @Override
    @Transactional
    public void startMeeting(Long id) {
        Optional<Meeting> findMeetingOptional = meetingRepository.findById(id);

        if(findMeetingOptional != null && findMeetingOptional.isPresent()) {
            findMeetingOptional.get().setStatus(Status.IN_PROGRESS);
        }
        else { // id에 해당하는 meeting이 없을 경우
            // Exception 처리 필요
        }
    }

    @Override
    public FindOneMeetingResponseDTO findOne(Long id) {
        Optional<Meeting> findMeetingOptional = meetingRepository.findById(id);

        if(findMeetingOptional != null && findMeetingOptional.isPresent()) {
            return FindOneMeetingResponseDTO.toDTO(findMeetingOptional.get());
        }
        else { // id에 해당하는 meeting이 없을 경우
            // Exception 처리 필요
            return null;
        }
    }


/*
     미팅 방 참여 요청
     1. 시간 확인
      1-1. 시작 시간 10분 전부터 입장 가능 o
      1-2. 시작 시간보다 10분 전보다 이른 시간이라면 입장 불가 메세지 -> return .
            2. Meeting status 확인
      2-1. SCHEDULED일 경우 -> 호스트만 입장 가능
          2-1-1. 호스트라면 미팅 방 입장 -> return o
          2-1-2. 호스트가 아니라면 아직 입장 불가 메세지 -> return o
      2-2. ATTENDEE_WAIT일 경우 -> 입장 가능 인원 확인
          2-2-1. 아직 입장 가능 인원이 남았다면 -> 입장 가능 메세지 전달 -> return o
          2-2-2. 입장 가능 인원이 남지 않았다면 -> 입장 불가능 메세지 전달 -> return o
      2-3. IN_PROGRESS일 경우 -> 이미 시작된 미팅, 입장 불가능 -> return
*/
    @Override
    public Map<String, String> joinRequest(Long id, Long member_id) {

        Map<String, String> responseMessage = new HashMap<>();

        // Meeting Entity 가져오기
        Optional<Meeting> findMeetingOptional = meetingRepository.findByIdJoinMember(id);

        if(findMeetingOptional != null && findMeetingOptional.isPresent()) {
            Meeting meeting = findMeetingOptional.get();

            // 1. 시간 확인
            if(meeting.getStartTime().minusMinutes(10).isBefore(LocalDateTime.now())) { // 1-1. 시작 시간 10분 전부터 입장 가능

                // 2. Meeting status 확인
                if(meeting.getStatus() == Status.SCHEDULED) { // 2-1. SCHEDULED일 경우 -> 호스트만 입장 가능

                    // 로그인된 사용자로 변경 필요
                    if(member_id == meeting.getHost().getId()) { // 호스트라면 미팅 방 입장
                        responseMessage.put("status", "success");
                        responseMessage.put("message", "호스트 입장 가능");
                        return responseMessage;
                    } else { // 호스트가 아니라면 아직 입장 불가
                        responseMessage.put("status", "fail");
                        responseMessage.put("message", "호스트가 미팅에 입장하지 않음.");
                        return responseMessage;
                    }
                } else if(meeting.getStatus() == Status.ATTENDEE_WAIT){ // 2-2. ATTENDEE_WAIT일 경우 -> 입장 가능 인원 확인

                    // 입장 가능 인원 확인
                    if(meeting.getCurrentParticipants() < meeting.getMaxParticipant()) { // 2-2-1. 아직 입장 가능 인원이 남았다면 입장 가능
                        responseMessage.put("status", "success");
                        responseMessage.put("message", "입장 가능");
                        return responseMessage;
                    } else { // 2-2-2. 입장 가능 인원이 남지 않았다면 입장 불가능
                        responseMessage.put("status", "fail");
                        responseMessage.put("message", "인원 수 초과");
                        return responseMessage;
                    }
                } else { // IN_PROGRESS일 경우 -> 이미 시작된 미팅, 입장 불가능
                    responseMessage.put("status", "fail");
                    responseMessage.put("message", "이미 진행 중인 미팅");
                    return responseMessage;
                }
            } else { // 1-2. 시작 시간보다 10분 전보다 이른 시간이라면 입장 불가 메세지
                responseMessage.put("status", "fail");
                responseMessage.put("message", "예약 시간 10분 전부터 입장이 가능");
                return responseMessage;
            }
        }
        else { // id에 해당하는 meeting이 없을 경우
            // Exception 처리 필요
            return null;
        }
    }

    @Override
    @Transactional
    public void join(Long id, Long memberId) {

        // Meeting Entity 가져오기
        Optional<Meeting> findMeetingOptional = meetingRepository.findByIdJoinMember(id);

        if(findMeetingOptional != null && findMeetingOptional.isPresent()) {
            Meeting meeting = findMeetingOptional.get();

            if(meeting.getHost().getId() == memberId) { // 호스트 입장일 경우
                // 미팅 상태 변경
                meeting.setStatus(Status.ATTENDEE_WAIT);
            } else { // 일반 멤버 입장일 경우
                // 미팅 현재 참여 인원 변경
                meeting.setCurrentParticipants(meeting.getCurrentParticipants() + 1);
            }
        } else { // id에 해당하는 meeting이 없을 경우
            // Exception 처리 필요
        }
    }

    @Override
    @Transactional
    public void left(Long id, Long memberId) {
        // Meeting Entity 가져오기

        Optional<Meeting> findMeetingOptional = meetingRepository.findByIdJoinMember(id);

        if(findMeetingOptional != null && findMeetingOptional.isPresent()) {

            Meeting meeting = findMeetingOptional.get();

            if(meeting.getHost().getId() == memberId) { // 호스트 퇴장일 경우
                // 미팅 삭제
                meetingRepository.delete(meeting);
            } else { // 일반 멤버 퇴장일 경우
                // meeting status가 ATTENDEE_WAIT일 경우 미팅 현재 참여 인원 변경
                if(meeting.getStatus() == Status.ATTENDEE_WAIT) {
                    meeting.setCurrentParticipants(meeting.getCurrentParticipants() - 1);
                }
            }

        } else { // id에 해당하는 meeting이 없을 경우
            // Exception 처리 필요
        }
    }
}
