package site.doggyyummy.gaebap.domain.meeting.controller;

import io.openvidu.java.client.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.exception.InvalidArgumentMeetingCreateException;
import site.doggyyummy.gaebap.domain.meeting.exception.MeetingEntryConditionNotMetException;
import site.doggyyummy.gaebap.domain.meeting.exception.MeetingForbiddenException;
import site.doggyyummy.gaebap.domain.meeting.exception.NotFoundMeetingException;
import site.doggyyummy.gaebap.domain.meeting.service.MeetingService;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
@Tag(name = "Meeting Controller", description = "미팅 API")
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

    @Operation(summary = "create meeting room", description = "미팅 룸 생성")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = CreateMeetingResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "미팅 예약 오류")
    })
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody CreateMeetingRequestDTO createMeetingRequestDTO){ // 방 생성

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            CreateMeetingResponseDTO createMeetingResponseDTO = meetingService.create(createMeetingRequestDTO, member.getId());
            return new ResponseEntity<>(createMeetingResponseDTO, HttpStatus.CREATED);
        } catch (InvalidArgumentMeetingCreateException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> modify(@RequestBody ModifyMeetingRequestDTO modifyMeetingRequestDTO) { // 방 정보 수정

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            ModifyMeetingResponseDTO modifyMeetingResponseDTO = meetingService.modify(modifyMeetingRequestDTO, member.getId());
            return new ResponseEntity<>(modifyMeetingResponseDTO, HttpStatus.OK);
        } catch (MeetingForbiddenException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        } catch (InvalidArgumentMeetingCreateException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable(name = "id") Long id) {

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            meetingService.delete(id, member.getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        } catch (MeetingForbiddenException e) {
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

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            meetingService.startMeeting(id, member.getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        } catch (MeetingForbiddenException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @GetMapping("/join-request/{id}")
    public ResponseEntity<ResponseDTO> joinRequest(@PathVariable(name = "id") Long id) { // 미팅 참여 가능 여부

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            MessageResponseDTO messageResponseDTO = meetingService.joinRequest(id, member.getId());
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

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            meetingService.join(id, member.getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @PostMapping("/left/{id}")
    public ResponseEntity<ResponseDTO> left(@PathVariable(name = "id") Long id) { // 미팅 나가기

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            meetingService.left(id, member.getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }
}
