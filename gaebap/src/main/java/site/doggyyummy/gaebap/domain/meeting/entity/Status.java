package site.doggyyummy.gaebap.domain.meeting.entity;

public enum Status {
    SCHEDULED, // 미팅이 예정되어 있는 상태(아직 미팅 방에 들어갈 수 없음)
    ATTENDEE_WAIT, // 미팅 참여자 대기 상태(모든 회원 미팅 방 입장 가능)
    IN_PROGRESS // 미팅 진행 중(더 이상 미팅 방에 입장 불가능)
}
