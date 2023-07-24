package site.doggyyummy.gaebap.domain.meeting.controller;

import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.meeting.dto.MakeMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.service.MeetingService;

import java.util.Map;

@RestController
@RequestMapping("/meetings")
@PropertySource("classpath:openvidu.properties")
public class MeetingController {

    private final int LIMIT = 5;
    private final MeetingService meetingService;

    // OpenVidu 객체 SDK
    private OpenVidu openvidu;

    // OpenVidu 서버 관련 변수
    private String OPENVIDU_URL;
    private String SECRET;

    @Autowired
    public MeetingController(MeetingService meetingService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.meetingService = meetingService;

        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openvidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @PostMapping
    public ResponseEntity<String> makeMeeting(@RequestBody MakeMeetingRequestDTO makeMeetingRequestDTO, @RequestBody(required = false) Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);

        meetingService.makeMeeting(session.getSessionId(), makeMeetingRequestDTO);

        return  new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }


    @GetMapping
    public String test(@Value("${openvidu.secret}") String secret) {
        return secret;
    }
}
