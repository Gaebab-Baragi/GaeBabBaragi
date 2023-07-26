package site.doggyyummy.gaebap.domain.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.meeting.dto.*;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.service.MeetingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<CreateMeetingResponseDTO> create(@RequestBody CreateMeetingRequestDTO createMeetingRequestDTO){ // 방 생성

        CreateMeetingResponseDTO createMeetingResponseDTO = meetingService.create(createMeetingRequestDTO);

        return new ResponseEntity<>(createMeetingResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ModifyMeetingResponseDTO> modify(@RequestBody ModifyMeetingRequestDTO modifyMeetingRequestDTO) { // 방 정보 수정

        ModifyMeetingResponseDTO modifyMeetingResponseDTO = meetingService.modify(modifyMeetingRequestDTO);

        return new ResponseEntity<>(modifyMeetingResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable(name = "id") Long id) {

        meetingService.delete(id);

        return HttpStatus.OK;
    }

    @GetMapping
    public ResponseEntity<List<SelectByRecipeMeetingResponseDTO>> selectByRecipe(@RequestParam(name = "recipe_id") Long recipeId) {
        List<SelectByRecipeMeetingResponseDTO> selectByRecipeMeetingResponseDTOS = meetingService.selectByRecipe(recipeId);

        return new ResponseEntity<>(selectByRecipeMeetingResponseDTOS, HttpStatus.OK);
    }
}
