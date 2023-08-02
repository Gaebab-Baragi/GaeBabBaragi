package site.doggyyummy.gaebap.domain.meeting.controller;

import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
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
    public ResponseEntity<CreateMeetingResponseDTO> create(@RequestBody CreateMeetingRequestDTO createMeetingRequestDTO){ // 방 생성

        // 멤버 검증 필요

        CreateMeetingResponseDTO createMeetingResponseDTO = meetingService.create(createMeetingRequestDTO);

        return new ResponseEntity<>(createMeetingResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ModifyMeetingResponseDTO> modify(@RequestBody ModifyMeetingRequestDTO modifyMeetingRequestDTO) { // 방 정보 수정

        // 작성자 검증 필요

        ModifyMeetingResponseDTO modifyMeetingResponseDTO = meetingService.modify(modifyMeetingRequestDTO);

        return new ResponseEntity<>(modifyMeetingResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable(name = "id") Long id) {

        // 작성자 검증 필요

        meetingService.delete(id);

        return HttpStatus.OK;
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
    public ResponseEntity<FindOneMeetingResponseDTO> findOne(@PathVariable(name = "id") Long id) {

        FindOneMeetingResponseDTO findOneMeetingResponseDTO = meetingService.findOne(id);

        return new ResponseEntity<>(findOneMeetingResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/start/{id}")
    public HttpStatus startMeeting(@PathVariable(name = "id") Long id) { // 호스트 미팅 시작 요청

        // 호스트인지 검증 필요

        meetingService.startMeeting(id);

        return HttpStatus.OK;
    }

    @GetMapping("/join-request/{id}")
    public ResponseEntity<Map> joinRequest(@PathVariable(name = "id") Long id) { // 미팅 참여 가능 여부

        // 로그인 멤버 검증 필요

        // 로그인 연동 후 삭제
        Long member_id = 2L;

        Map<String, String> responseMessage =  meetingService.joinRequest(id, member_id);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
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
    public HttpStatus join(@PathVariable(name = "id") Long id) { // 미팅 참가

        // 로그인 멤버 검증 필요

        // 로그인 연동 후 삭제
        Long memberId = 2L;

        meetingService.join(id, memberId);

        return HttpStatus.OK;
    }

    @PostMapping("/left/{id}")
    public HttpStatus left(@PathVariable(name = "id") Long id) { // 미팅 나가기
        // 로그인 멤버 검증 필요

        // 로그인 연동 후 삭제
        Long memberId = 1L;

        meetingService.left(id, memberId);

        return HttpStatus.OK;
    }
}
