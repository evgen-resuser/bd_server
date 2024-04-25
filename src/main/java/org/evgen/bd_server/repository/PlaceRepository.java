package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.place.Place;
import org.evgen.bd_server.model.place.PlaceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PlaceRepository extends JpaRepository<Place, PlaceId> {

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.stadiumIsCovered as isCovered, " +
            "p.stadiumPlacesCount as placesCount, p.stadiumSquare as square)" +
            "FROM Place p WHERE p.id.typeId = 3")
    List<Map<PlaceId, Object>> getStadiums();

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.stadiumIsCovered as isCovered, " +
            "p.stadiumPlacesCount as placesCount, p.stadiumSquare as square)" +
            "FROM Place p WHERE p.id.typeId = 3 AND p.id.id = :id")
    Map<PlaceId, Object> getStadiumById(@Param("id") Integer id);

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.poolDepth as depth," +
            "p.poolLanesCount as lanesCount, p.poolLength as length)" +
            "FROM Place p WHERE p.id.typeId = 1")
    List<Map<PlaceId, Object>> getPools();

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.poolDepth as depth," +
            "p.poolLanesCount as lanesCount, p.poolLength as length)" +
            "FROM Place p WHERE p.id.typeId = 1 AND p.id.id = :id")
    Map<PlaceId, Object> getPoolById(@Param("id") Integer id);

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.gymSquare as square)" +
            "FROM Place p WHERE p.id.typeId = 2")
    List<Map<PlaceId, Object>> getGyms();

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.gymSquare as square)" +
            "FROM Place p WHERE p.id.typeId = 2 AND p.id.id = :id")
    Map<PlaceId, Object> getGymById(@Param("id") Integer id);

}
