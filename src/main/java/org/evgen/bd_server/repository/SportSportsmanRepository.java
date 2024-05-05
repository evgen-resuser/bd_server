package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.SportSportsman;
import org.evgen.bd_server.model.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SportSportsmanRepository extends JpaRepository<SportSportsman, Integer> {

    @Query("SELECT new map(a.id as sportsmanId, a.name as sportsmanName) " +
            "FROM SportSportsman ss, Sportsman a " +
            "WHERE ss.sportId = :sId AND a.id = ss.sportsmanId ")
    List<Map<String, Object>> findBySport(@Param("sId") Integer sportId);

    @Query("SELECT new map(s.id as sportsmanId, s.name as sportsmanName, a.discharge as discharge) " +
            "FROM SportSportsman ss, Sportsman s, SportAchievement a " +
            "WHERE ss.sportId = :sId AND s.id = ss.sportsmanId AND " +
            "a.sportsman.id = ss.sportsmanId AND a.discharge >= :charge")
    List<Map<String, Object>> findBySportAndCharge(@Param("sId") Integer sportId,
                                                   @Param("charge") String charge);

//    @Query("SELECT new map(ss.sportsmanId as sportsman) " +
//            "FROM SportSportsman ss " +
//            "GROUP BY ss.sportsmanId " +
//            "HAVING COUNT(DISTINCT ss.sportId) >= 2")
//    List<Object> findMoreThanOneSport();

}
