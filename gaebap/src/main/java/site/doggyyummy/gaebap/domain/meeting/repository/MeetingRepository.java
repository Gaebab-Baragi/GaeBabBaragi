package site.doggyyummy.gaebap.domain.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.recipe r " +
            "WHERE m.status != 'IN_PROGRESS' " +
            "ORDER BY m.startTime")
    List<Meeting> findAll();

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.recipe r " +
            "WHERE r.id = :recipeId " +
            "AND m.status != :status " +
            "ORDER BY m.startTime ASC")
    List<Meeting> findMeetingsByRecipeIdAndStatusNotOrderByStartTimeAsc(Long recipeId, Status status);

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.host h " +
            "where m.id = :id")
    Optional<Meeting> findByIdJoinMember(Long id);

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.host h " +
            "where h.id = :memberId")
    List<Meeting> findByMember(Long memberId);
}