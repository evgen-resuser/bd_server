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

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.stadiumIsCovered as isCovered, " +
            "p.stadiumPlacesCount as placesCount, p.stadiumSquare as square)" +
            "FROM Place p WHERE (p.id.typeId = 3 " +
            "AND p.stadiumPlacesCount <= :pl_mx AND p.stadiumPlacesCount >= :pl_mn " +
            "AND p.stadiumSquare <= :sq_mx AND p.stadiumSquare >= :sq_mn " +
            "AND p.stadiumIsCovered = :covered)")
    List<Map<PlaceId, Object>> getStadiumsByParams(@Param("covered") Boolean isCovered,
                                                   @Param("pl_mx") Integer placesCountMax,
                                                   @Param("pl_mn") Integer placesCountMin,
                                                   @Param("sq_mx") Float squareMax,
                                                   @Param("sq_mn") Float squareMin);

    @Query("SELECT new map(p.id as id, p.name as name, p.address as address, p.poolLength as length, " +
            "p.poolLanesCount as lanesCount, p.poolDepth as depth)" +
            "FROM Place p WHERE (p.id.typeId = 1 " +
            "AND p.poolLanesCount <= :lane_mx AND p.poolLanesCount >= :lane_mn " +
            "AND p.poolDepth <= :dep_mx AND p.poolDepth >= :dep_mn " +
            "AND p.poolLength <= :len_mx AND p.poolLength >= :len_mn)")
    List<Map<PlaceId, Object>> getPoolsByParams(@Param("lane_mx") Integer lanesMax,
                                                @Param("lane_mn") Integer lanesMin,
                                                @Param("dep_mx") Integer depthMax,
                                                @Param("dep_mn") Integer depthMin,
                                                @Param("len_mx") Integer lengthMax,
                                                @Param("len_mn") Integer lengthMin);

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
