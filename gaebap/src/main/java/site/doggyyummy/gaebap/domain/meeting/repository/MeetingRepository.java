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
            "JOIN FETCH m.host h " +
            "ORDER BY m.startTime")
    List<Meeting> findAll();

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.recipe r " +
            "JOIN FETCH m.host h " +
            "WHERE r.id = :recipeId " +
            "ORDER BY m.startTime ASC")
    List<Meeting> findMeetingsByRecipeIdOrderByStartTimeAsc(Long recipeId);

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.host h " +
            "where m.id = :id")
    Optional<Meeting> findByIdJoinMember(Long id);

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.host h " +
            "where h.id = :memberId")
    List<Meeting> findByMember(Long memberId);
}