package site.doggyyummy.gaebap.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;
import site.doggyyummy.gaebap.domain.meeting.exception.InvalidArgumentMeetingCreateException;
import site.doggyyummy.gaebap.domain.meeting.exception.MeetingEntryConditionNotMetException;
import site.doggyyummy.gaebap.domain.meeting.exception.MeetingForbiddenException;
import site.doggyyummy.gaebap.domain.meeting.exception.NotFoundMeetingException;
import site.doggyyummy.gaebap.domain.meeting.repository.MeetingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;

    @Override
    @Transactional
    public CreateMeetingResponseDTO create(CreateMeetingRequestDTO createMeetingRequestDTO, Long hostId) {

        // 예외 처리
        if(createMeetingRequestDTO.getMaxParticipant() < 2 || createMeetingRequestDTO.getMaxParticipant() > 5) { //  제한 인원이 2 이상 5 이하가 아니라면
            throw new InvalidArgumentMeetingCreateException("제한 인원은 2명 이상 5명 이하 입니다.");
        }

        String password = createMeetingRequestDTO.getPassword();
        if (password != null && (password.length() != 6 || !password.matches("\\d+"))) { // private 룸일 때 비밀번호가 6자리 숫자가 아닐 경우
                throw new InvalidArgumentMeetingCreateException("비밀번호는 6자리 숫자로 이루어진 문자열만 가능합니다.");
        }

        if (createMeetingRequestDTO.getStartTime().isBefore(LocalDateTime.now())) { // 미팅 시작 시간이 현재 시간보다 전이라면
                throw new InvalidArgumentMeetingCreateException("이미 지난 시간으로는 예약이 불가능합니다.");
        }

        Meeting meeting = createMeetingRequestDTO.toEntity(hostId);
        Meeting savedMeeting = meetingRepository.save(meeting);
        CreateMeetingResponseDTO createMeetingResponseDTO = CreateMeetingResponseDTO.toDTO(savedMeeting);
        return  createMeetingResponseDTO;
    }

    @Override
    @Transactional
    public ModifyMeetingResponseDTO modify(ModifyMeetingRequestDTO modifyMeetingRequestDTO, Long hostId) {

        Meeting findMeeting = meetingRepository.findByIdJoinMember(modifyMeetingRequestDTO.getId()).orElseThrow(() -> new NotFoundMeetingException());

        // 예외 처리
        if(findMeeting.getHost().getId() != hostId) { // 로그인된 멤버가 미팅의 호스트가 아니면 미팅 수정 불가
            throw new MeetingForbiddenException("호스트만 해당 미팅을 수정할 수 있습니다.");
        }

        if(modifyMeetingRequestDTO.getMaxParticipant() < 2 || modifyMeetingRequestDTO.getMaxParticipant() > 5) { //  제한 인원이 2 이상 5 이하가 아니라면
            throw new InvalidArgumentMeetingCreateException("제한 인원은 2명 이상 5명 이하 입니다.");
        }

        String password = modifyMeetingRequestDTO.getPassword();
        if (password != null && (password.length() != 6 || !password.matches("\\d+"))) { // private 룸일 때 비밀번호가 6자리 숫자가 아닐 경우
            throw new InvalidArgumentMeetingCreateException("비밀번호는 6자리 숫자로 이루어진 문자열만 가능합니다.");
        }

        if (modifyMeetingRequestDTO.getStartTime().isBefore(LocalDateTime.now())) { // 미팅 시작 시간이 현재 시간보다 전이라면
            throw new InvalidArgumentMeetingCreateException("이미 지난 시간으로는 예약이 불가능합니다.");
        }

        findMeeting.setPassword(modifyMeetingRequestDTO.getPassword());
        findMeeting.setMaxParticipant(modifyMeetingRequestDTO.getMaxParticipant());
        findMeeting.setTitle(modifyMeetingRequestDTO.getTitle());
        findMeeting.setDescription(modifyMeetingRequestDTO.getDescription());
        findMeeting.setStartTime(modifyMeetingRequestDTO.getStartTime());

        ModifyMeetingResponseDTO modifyMeetingResponseDTO = ModifyMeetingResponseDTO.toDTO(findMeeting);
        return modifyMeetingResponseDTO;

    }

    @Override
    @Transactional
    public void delete(Long id, Long hostId) {

        Meeting findMeeting = meetingRepository.findByIdJoinMember(id).orElseThrow(() -> new NotFoundMeetingException());

        if(findMeeting.getHost().getId() != hostId) { // 로그인된 멤버가 미팅의 호스트가 아니면 미팅 삭제 불가
            throw new MeetingForbiddenException("호스트만 해당 미팅을 삭제할 수 있습니다.");
        }

        meetingRepository.delete(findMeeting);

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
    public FindOneMeetingResponseDTO findOne(Long id) {
        Meeting findMeeting = meetingRepository.findById(id).orElseThrow(() -> new NotFoundMeetingException());

        return FindOneMeetingResponseDTO.toDTO(findMeeting);
    }

    @Override
    public List<FindMeetingResponseDTO> findByMember(Long memberId) {
        List<Meeting> meetings = meetingRepository.findByMember(memberId);

        List<FindMeetingResponseDTO> findMeetingResponseDTOS = meetings.stream()
                .map(FindMeetingResponseDTO::toDTO)
                .collect(Collectors.toList());

        return findMeetingResponseDTOS;
    }

    @Override
    @Transactional
    public void startMeeting(Long id, Long hostId) {
        Meeting findMeeting = meetingRepository.findByIdJoinMember(id).orElseThrow(() -> new NotFoundMeetingException());

        if(findMeeting.getHost().getId() != hostId) { // 로그인된 멤버가 미팅의 호스트가 아니면 미팅 시작 불가
            throw new MeetingForbiddenException("호스트만 해당 미팅을 시작할 수 있습니다.");
        }

        findMeeting.setStatus(Status.IN_PROGRESS);
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
    public MessageResponseDTO joinRequest(Long id, Long member_id) {

        // Meeting Entity 가져오기
        Meeting findMeeting = meetingRepository.findByIdJoinMember(id).orElseThrow(() -> new NotFoundMeetingException());

        // 1. 시간 확인
        if(findMeeting.getStartTime().minusMinutes(10).isBefore(LocalDateTime.now())) { // 1-1. 시작 시간 10분 전부터 입장 가능

            // 2. Meeting status 확인
            if(findMeeting.getStatus() == Status.SCHEDULED) { // 2-1. SCHEDULED일 경우 -> 호스트만 입장 가능

                // 로그인된 사용자로 변경 필요
                if(member_id == findMeeting.getHost().getId()) { // 호스트라면 미팅 방 입장
                    return MessageResponseDTO.toDTO("호스트 입장 가능");
                } else { // 호스트가 아니라면 아직 입장 불가
                    throw new MeetingEntryConditionNotMetException("호스트가 아직 미팅에 입장하지 않았습니다.");
                }
            } else if(findMeeting.getStatus() == Status.ATTENDEE_WAIT){ // 2-2. ATTENDEE_WAIT일 경우 -> 입장 가능 인원 확인
                // 입장 가능 인원 확인
                if(findMeeting.getCurrentParticipants() < findMeeting.getMaxParticipant()) { // 2-2-1. 아직 입장 가능 인원이 남았다면 입장 가능
                    return MessageResponseDTO.toDTO("일반 멤버 입장 가능");
                } else { // 2-2-2. 입장 가능 인원이 남지 않았다면 입장 불가능
                    throw new MeetingEntryConditionNotMetException("인원수 초과. 더 이상 미팅에 입장할 수 없습니다.");
                }
            } else { // IN_PROGRESS일 경우 -> 이미 시작된 미팅, 입장 불가능
                throw new MeetingEntryConditionNotMetException("이미 진행 중인 미팅입니다.");
            }
        } else { // 1-2. 시작 시간보다 10분 전보다 이른 시간이라면 입장 불가 메세지
            throw new MeetingEntryConditionNotMetException("예약 시간 10분 전부터 입장이 가능합니다.");
        }
    }

    @Override
    @Transactional
    public void join(Long id, Long memberId) {

        // Meeting Entity 가져오기
        Meeting findMeeting = meetingRepository.findByIdJoinMember(id).orElseThrow(() -> new NotFoundMeetingException());

        if(findMeeting.getHost().getId() == memberId) { // 호스트 입장일 경우
            // 미팅 상태 변경
            findMeeting.setStatus(Status.ATTENDEE_WAIT);
        }

        // 미팅 현재 참여 인원 변경
        findMeeting.setCurrentParticipants(findMeeting.getCurrentParticipants() + 1);
    }

    @Override
    @Transactional
    public void left(Long id, Long memberId) {

        // Meeting Entity 가져오기
        Meeting findMeeting = meetingRepository.findByIdJoinMember(id).orElseThrow(() -> new NotFoundMeetingException());

        if(findMeeting.getHost().getId() == memberId) { // 호스트 퇴장일 경우
            // 미팅 삭제
            meetingRepository.delete(findMeeting);
        } else { // 일반 멤버 퇴장일 경우
            // meeting status가 ATTENDEE_WAIT일 경우 미팅 현재 참여 인원 변경
            if(findMeeting.getStatus() == Status.ATTENDEE_WAIT) {
                findMeeting.setCurrentParticipants(findMeeting.getCurrentParticipants() - 1);
            }
        }
    }
}
