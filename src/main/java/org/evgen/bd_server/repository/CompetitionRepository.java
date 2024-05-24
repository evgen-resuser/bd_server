package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.Competition;
import org.evgen.bd_server.model.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    @Query("SELECT c FROM Competition c, Place p WHERE " +
            "p.id.id = :plId AND p.id.typeId = :plType AND " +
            "c.placeId = :plId AND c.placeTypeId = :plType")
    List<Object> getCompetitionsFromPlace(@Param("plId") Integer placeId,
                                          @Param("plType") Integer placeType);

    @Query("SELECT c FROM Competition c, Place p WHERE " +
            "p.id.id = :plId AND p.id.typeId = :plType AND " +
            "c.placeId = :plId AND c.placeTypeId = :plType AND c.sport.id = :spId")
    List<Object> getCompetitionsFromPlace(@Param("plId") Integer placeId,
                                          @Param("plType") Integer placeType,
                                          @Param("spId") Integer sportId);

    @Query("SELECT c FROM Competition c, Participation p WHERE p.id.sportsmanId = :id AND p.id.compId = c.id ")
    List<Object> getCompetitionsOfSportsman(@Param("id") Integer id);

    @Query("SELECT c FROM Competition c WHERE c.organize.id = :org")
    List<Object> getCompetitionsByPeriod(@Param("org") Integer org);

    @Query("SELECT c FROM Competition c WHERE c.date <= :end AND c.date >= :start AND c.organize.id = :org")
    List<Object> getCompetitionsByPeriodAndOrganize(@Param("start") Date start,
                                                    @Param("end") Date end,
                                                    @Param("org") Integer orgId);

    @Query("SELECT new map(c.organize as organize, COUNT(c) as count) FROM Competition c WHERE c.date BETWEEN :start AND :end GROUP BY c.organize")
    List<Object> getOrganizersAndNumberOfCompetitions(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT new map(p.name as name, c.date as date, c.id as comp_id) " +
            "FROM Place p, Competition c WHERE c.date BETWEEN :start AND :end " +
            "AND c.placeId = p.id.id AND c.placeTypeId = p.id.typeId")
    List<Object> getPlacesByPeriod(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT s FROM Sportsman s WHERE s.id NOT IN " +
            "(SELECT s2.id FROM Sportsman s2 " +
            "JOIN Participation participation ON s2.id = participation.id.sportsmanId " +
            "JOIN Competition c ON participation.id.compId = c.id " +
            "WHERE c.date BETWEEN :startDate AND :endDate)")
    List<Object> findSportsmenNotParticipatingInPeriod(@Param("startDate") Date startDate,
                                                       @Param("endDate") Date endDate);

    @Query("SELECT new map(club.name as name, COUNT(DISTINCT sportsman.id) AS number_of_sportsmen) " +
            "FROM Club club " +
            "JOIN Sportsman sportsman ON club.id = sportsman.club.id " +
            "JOIN Participation participation ON sportsman.id = participation.id.sportsmanId " +
            "JOIN Competition competition ON participation.id.compId = competition.id " +
            "WHERE competition.date BETWEEN :startDate AND :endDate " +
            "GROUP BY club.name ")
    List<Object> getClubSportsmenCountByPeriod(@Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate);

    @Query("SELECT new map(c.id as id, c.sport as sport, c.organize as organize, c.date as date, p.name  as place) " +
            "FROM Competition c, Place p WHERE c.placeId = p.id.id AND c.placeTypeId = p.id.typeId")
    List<Object> getAllWithPlace();
}
