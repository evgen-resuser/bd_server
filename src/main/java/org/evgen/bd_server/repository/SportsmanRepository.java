package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SportsmanRepository extends JpaRepository<Sportsman, Integer> {
    @Query("SELECT new map(a.id as id, a.name as name, c as club) FROM Sportsman a LEFT JOIN a.club c")
    List<Map<String, Object>> findAthleteAndClubNames();

    @Query("SELECT new map(a.id as id, a.name as name, c as club) FROM Sportsman a LEFT JOIN a.club c WHERE a.id = :id")
    Map<String, Object> findSportsman(@Param("id") Integer id);
}
