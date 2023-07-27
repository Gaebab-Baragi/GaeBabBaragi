package site.doggyyummy.gaebap.domain.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
