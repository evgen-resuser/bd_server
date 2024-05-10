package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.SportSportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportSportsmanRepository extends JpaRepository<SportSportsman, Integer> {

    @Query("SELECT new map(s as sportsman, STRING_AGG(sp.name, ', ') as sports) " +
            "FROM SportSportsman ss " +
            "JOIN ss.sportsmanId s " +
            "JOIN ss.sportId sp " +
            "GROUP BY s " +
            "HAVING COUNT(DISTINCT sp) > 1")
    List<Object> findMoreThanOneSport();

    @Query("SELECT s FROM SportSportsman ss, Sportsman s WHERE ss.sportId.id = :sId AND s = ss.sportsmanId ")
    List<Object> findBySport(@Param("sId") Integer sportId);

    @Query("SELECT new map(s as sportsman, a.discharge as discharge) FROM Sportsman s, SportSportsman ss, SportAchievement a " +
            "WHERE ss.sportId.id = :sport AND s.id = ss.sportsmanId.id AND " +
            "a.sportsman.id = ss.sportsmanId.id AND a.discharge >= :disch")
    List<Object> findBySportAndDischarge(@Param("sport") Integer sportId, @Param("disch") String discharge);
}
