package site.doggyyummy.gaebap.domain.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingRequestDTO;
import site.doggyyummy.gaebap.domain.meeting.dto.CreateMeetingResponseDTO;
import site.doggyyummy.gaebap.domain.meeting.service.MeetingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<CreateMeetingResponseDTO> creat(@RequestBody CreateMeetingRequestDTO createMeetingRequestDTO){ // 방 생성

        CreateMeetingResponseDTO createMeetingResponseDTO = meetingService.create(createMeetingRequestDTO);

        return new ResponseEntity<>(createMeetingResponseDTO, HttpStatus.CREATED);
    }

}
