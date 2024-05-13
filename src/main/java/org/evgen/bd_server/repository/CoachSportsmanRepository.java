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

    @Query("SELECT new map (s.id as id, s.name as name) " +
            "FROM CoachSportsman cs, Sportsman s " +
            "WHERE cs.coach.id = :id AND cs.sport = s")
    List<Object> getSportsmenById(@Param("id") Integer param);

    @Query("SELECT new map (s.id as id, s.name as name, sa.discharge as discharge) " +
            "FROM CoachSportsman cs, Sportsman s, SportAchievement sa " +
            "WHERE cs.coach.id = :id AND cs.sport = s AND sa.discharge >= :disch AND sa.sportsman = s")
    List<Object> getSportsmenByIdDischarge(@Param("id") Integer param, @Param("disch") String discharge);

    @Query("SELECT cs " +
            "FROM CoachSportsman cs " +
            "WHERE cs.coach.id = :cId AND cs.sport.id = :sId")
    CoachSportsman findBySportsmanIdAndCoachId(@Param("cId") Integer cId, @Param("sId") Integer sId);

}
