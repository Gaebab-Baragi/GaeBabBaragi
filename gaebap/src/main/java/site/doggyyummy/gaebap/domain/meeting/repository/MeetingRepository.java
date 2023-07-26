package site.doggyyummy.gaebap.domain.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.recipe r " +
            "WHERE r.id = :recipeId " +
            "AND m.status != :status " +
            "ORDER BY m.startTime ASC")
    List<Meeting> findMeetingsByRecipeIdAndStatusNotOrderByStartTimeAsc(Long recipeId, Status status);
}