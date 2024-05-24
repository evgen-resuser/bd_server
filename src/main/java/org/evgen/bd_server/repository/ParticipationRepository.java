package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, ParticipationRepository> {

    @Query("SELECT new map(s.name as name, s.id as id, p.place as place) FROM Sportsman s, Participation p " +
            "WHERE p.id.sportsmanId = s.id AND p.id.compId = :comp AND p.place <> 0 ")
    List<Object> getWinners(@Param("comp") Integer competitionId);

}
