package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.CoachSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CoachSportRepository extends JpaRepository<CoachSport, Integer> {
    @Query("SELECT new map(s.name as sport, c.name as coach) " +
            "FROM Sport s, Coach c, CoachSport cs " +
            "WHERE cs.coach = c AND cs.sport = s")
    List<Map<String, Object>> getFormattedCoachSport();

    @Query("SELECT cs.coach " +
            "FROM CoachSport cs " +
            "WHERE cs.sport.id = :sport  ")
    List<Object> getCoachesBySport(@Param("sport") Integer sportId);

    @Query("SELECT cs.sport " +
            "FROM CoachSport cs " +
            "WHERE cs.coach.id = :coach")
    List<Object> getSportsByCoach(@Param("coach") Integer coachId);

    @Query("SELECT cs.id " +
            "FROM CoachSport cs " +
            "WHERE cs.sport.id = :sId AND cs.coach.id = :cId")
    Integer getIdBySportCoach(@Param("cId") Integer cId, @Param("sId") Integer sId);

}
