package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
