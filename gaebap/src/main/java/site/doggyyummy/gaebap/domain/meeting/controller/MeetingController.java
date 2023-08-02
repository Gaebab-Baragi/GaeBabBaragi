package site.doggyyummy.gaebap.domain.meeting.controller;

import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.exception.InvalidArgumentMeetingCreateException;
import site.doggyyummy.gaebap.domain.meeting.exception.MeetingEntryConditionNotMetException;
import site.doggyyummy.gaebap.domain.meeting.exception.NotFoundMeetingException;
import site.doggyyummy.gaebap.domain.meeting.service.MeetingService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

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

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody CreateMeetingRequestDTO createMeetingRequestDTO){ // 방 생성

        // 멤버 검증 필요

        try {
            CreateMeetingResponseDTO createMeetingResponseDTO = meetingService.create(createMeetingRequestDTO);
            return new ResponseEntity<>(createMeetingResponseDTO, HttpStatus.CREATED);
        } catch (InvalidArgumentMeetingCreateException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> modify(@RequestBody ModifyMeetingRequestDTO modifyMeetingRequestDTO) { // 방 정보 수정

        // 작성자 검증 필요

        try {
            ModifyMeetingResponseDTO modifyMeetingResponseDTO = meetingService.modify(modifyMeetingRequestDTO);
            return new ResponseEntity<>(modifyMeetingResponseDTO, HttpStatus.OK);
        } catch (InvalidArgumentMeetingCreateException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable(name = "id") Long id) {

        // 작성자 검증 필요

        try {
            meetingService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @GetMapping
    public ResponseEntity<List<FindMeetingResponseDTO>> find(@RequestParam(name = "recipe_id", required = false) Long recipeId) {
        List<FindMeetingResponseDTO> findMeetingResponseDTOS;

        if(recipeId != null) { // 레시피별 미팅 조회
            findMeetingResponseDTOS = meetingService.findByRecipe(recipeId);
        }
        else { // 레시피 전체 조회
            System.out.println("without recipe_id");
            findMeetingResponseDTOS = meetingService.findAll();
        }

        return new ResponseEntity<>(findMeetingResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findOne(@PathVariable(name = "id") Long id) {

        try{
            FindOneMeetingResponseDTO findOneMeetingResponseDTO = meetingService.findOne(id);
            return new ResponseEntity<>(findOneMeetingResponseDTO, HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @PostMapping("/start/{id}")
    public ResponseEntity<ResponseDTO> startMeeting(@PathVariable(name = "id") Long id) { // 호스트 미팅 시작 요청

        // 호스트인지 검증 필요

        try {
            meetingService.startMeeting(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @GetMapping("/join-request/{id}")
    public ResponseEntity<ResponseDTO> joinRequest(@PathVariable(name = "id") Long id) { // 미팅 참여 가능 여부

        // 로그인 멤버 검증 필요

        // 로그인 연동 후 삭제
        Long member_id = 2L;

        try {
            MessageResponseDTO messageResponseDTO = meetingService.joinRequest(id, member_id);
            return new ResponseEntity<>(messageResponseDTO, HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        } catch (MeetingEntryConditionNotMetException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @PostMapping("/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException { // OpenVidu sessionId 발급
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @PostMapping("/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId, @RequestBody(required = false) Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException { // Openvidu 토큰 발급
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<ResponseDTO> join(@PathVariable(name = "id") Long id) { // 미팅 참가

        // 로그인 멤버 검증 필요

        // 로그인 연동 후 삭제
        Long memberId = 2L;

        try {
            meetingService.join(id, memberId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @PostMapping("/left/{id}")
    public ResponseEntity<ResponseDTO> left(@PathVariable(name = "id") Long id) { // 미팅 나가기
        // 로그인 멤버 검증 필요

        // 로그인 연동 후 삭제
        Long memberId = 1L;

        try {
            meetingService.left(id, memberId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }
}
