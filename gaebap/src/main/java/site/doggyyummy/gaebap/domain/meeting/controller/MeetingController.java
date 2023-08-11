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
        @ApiResponse(responseCode = "400", description = "미팅 예약 오류: ('제한 인원은 2명 이상 5명 이하 입니다.' or '비밀번호는 6자리 숫자로 이루어진 문자열만 가능합니다.' or '이미 지난 시간으로는 예약이 불가능합니다.')")
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

    @Operation(summary = "modify meeting room", description = "미팅 룸 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ModifyMeetingResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "미팅 리소스 접근 불가: 호스트만 해당 미팅을 수정할 수 있습니다."),
            @ApiResponse(responseCode = "400", description = "미팅 예약 오류: ('제한 인원은 2명 이상 5명 이하 입니다.' or '비밀번호는 6자리 숫자로 이루어진 문자열만 가능합니다.' or '이미 지난 시간으로는 예약이 불가능합니다.'), 미팅 정보 오류: 미팅 정보를 찾을 수 없습니다.")
    })
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

    @Operation(summary = "delete meeting room", description = "미팅 룸 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "미팅 정보 오류: 미팅 정보를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "403", description = "미팅 리소스 접근 불가: 호스트만 해당 미팅을 삭제할 수 있습니다."),
    })
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

    @Operation(summary = "find meeting room list", description = "미팅 룸 리스트 조회 (파라미터로 레시피 id 보낼 시, 레시피 별 조회 가능)")
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

    @Operation(summary = "find meeting room", description = "미팅 룸 디테일 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FindOneMeetingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "미팅 정보 오류: 미팅 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findOne(@PathVariable(name = "id") Long id) {

        try{
            FindOneMeetingResponseDTO findOneMeetingResponseDTO = meetingService.findOne(id);
            return new ResponseEntity<>(findOneMeetingResponseDTO, HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @Operation(summary = "find meeting room list by member", description = "로그인 회원이 생성한 미팅 룸 리스트 조회")
    @GetMapping("/my-meetings")
    public ResponseEntity<List<FindMeetingResponseDTO>> findByMember() {

        Member member = SecurityUtil.getCurrentLoginMember();

        List<FindMeetingResponseDTO> findMeetingResponseDTOS = meetingService.findByMember(member.getId());

        return new ResponseEntity<>(findMeetingResponseDTOS, HttpStatus.OK);
    }

    @Operation(summary = "start meeting", description = "호스트 미팅 시작")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "미팅 정보 오류: 미팅 정보를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "403", description = "미팅 리소스 접근 불가: 호스트만 해당 미팅을 시작할 수 있습니다."),
    })
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

    @Operation(summary = "meeting join request", description = "미팅 참여 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "호스트 입장 가능"),
            @ApiResponse(responseCode = "400", description = "미팅 정보 오류: 미팅 정보를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "403", description = "미팅 입장 불가: ('호스트가 아직 미팅에 입장하지 않았습니다.' or '인원수 초과. 더 이상 미팅에 입장할 수 없습니다.' or '이미 진행 중인 미팅입니다.' or '예약 시간 10분 전부터 입장이 가능합니다.')")
    })
    @PostMapping("/join-request/{id}")
    public ResponseEntity<ResponseDTO> joinRequest(@PathVariable(name = "id") Long id, @RequestBody(required = false) String password) { // 미팅 참여 가능 여부

        Member member = SecurityUtil.getCurrentLoginMember();

        try {
            MessageResponseDTO messageResponseDTO = meetingService.joinRequest(id, member.getId(), password);
            return new ResponseEntity<>(messageResponseDTO, HttpStatus.OK);
        } catch (NotFoundMeetingException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        } catch (MeetingEntryConditionNotMetException e) {
            return new ResponseEntity<>(MessageResponseDTO.toDTO(e.getMessage()), e.getHttpStatus());
        }
    }

    @Operation(summary = "OpenVidu initialize session", description = "미팅 룸 OpenVidu sessionId 발급")
    @PostMapping("/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException { // OpenVidu sessionId 발급
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @Operation(summary = "OpenVidu create connection", description = "미팅 룸 OpenVidu 토큰 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "세션을 찾을 수 없음")
    })
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

    @Operation(summary = "join meeting room", description = "미팅 룸 참가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "미팅 정보 오류: 미팅 정보를 찾을 수 없습니다.")
    })
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

    @Operation(summary = "left meeting room", description = "미팅 나가기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "미팅 정보 오류: 미팅 정보를 찾을 수 없습니다.")
    })
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

    @Operation(summary = "close meeting room", description = "미팅 끝내기")
    @PostMapping("/close/{id}")
    public ResponseEntity<ResponseDTO> close(@PathVariable(name = "id") Long id) {

        meetingService.close(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
