package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.CoachSportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CoachSportsmanRepository extends JpaRepository<CoachSportsman, Integer> {

    @Query("SELECT new map (c as coach) " +
            "FROM CoachSportsman cs, Coach c " +
            "WHERE cs.sport.id = :id AND cs.coach = c")
    List<Map<String, Object>> getCoachesById(@Param("id") Integer param);



}
