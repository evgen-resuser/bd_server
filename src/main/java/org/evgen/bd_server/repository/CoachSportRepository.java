package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.CoachSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CoachSportRepository extends JpaRepository<CoachSport, Integer> {
    @Query("SELECT new map(s.name as sport, c.name as coach) " +
            "FROM Sport s, Coach c, CoachSport cs " +
            "WHERE cs.coach = c AND cs.sport = s")
    List<Map<String, Object>> getFormattedCoachSport();
}
