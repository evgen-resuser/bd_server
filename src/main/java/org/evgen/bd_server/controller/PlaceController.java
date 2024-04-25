package org.evgen.bd_server.controller;

import org.evgen.bd_server.dto.GymRequest;
import org.evgen.bd_server.dto.PoolRequest;
import org.evgen.bd_server.dto.StadiumRequest;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.place.Place;
import org.evgen.bd_server.model.place.PlaceId;
import org.evgen.bd_server.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/data")
public class PlaceController extends ResourceNotFoundHandler{

    private static final int STADIUM_TYPE_ID = 3;
    private static final int POOL_TYPE_ID = 1;
    private static final int GYM_TYPE_ID = 2;

    private final PlaceRepository repository;

    @Autowired
    public PlaceController(PlaceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/place")
    public List<Place> readAll() {
        return repository.findAll();
    }

    @GetMapping("/place/stadium")
    public List<Map<PlaceId, Object>> readAllStadiums() {
        return repository.getStadiums();
    }

    @GetMapping("/place/stadium/{id}")
    public Map<PlaceId, Object> readStadiumById(@PathVariable Integer id) {
        return repository.getStadiumById(id);
    }

    @GetMapping("/place/pool")
    public List<Map<PlaceId, Object>> readAllPools() {
        return repository.getPools();
    }

    @GetMapping("/place/pool/{id}")
    public Map<PlaceId, Object> readPoolById(@PathVariable Integer id) {
        return repository.getPoolById(id);
    }

    @GetMapping("/place/gym")
    public List<Map<PlaceId, Object>> readAllGyms() {
        return repository.getGyms();
    }

    @GetMapping("/place/gym/{id}")
    public Map<PlaceId, Object> readGymById(@PathVariable Integer id) {
        return repository.getGymById(id);
    }

    private boolean deletePlace(Integer id, Integer typeId){

        if (id == null || typeId == null) return false;

        PlaceId place = new PlaceId();
        place.setId(id);
        place.setTypeId(typeId);

        if (!repository.existsById(place)) return false;
        repository.deleteById(place);
        return true;
    }

    @DeleteMapping("/place/stadium/{id}")
    public boolean deleteStadiumById(@PathVariable Integer id) {
        return deletePlace(id, STADIUM_TYPE_ID);
    }

    @DeleteMapping("/place/pool/{id}")
    public boolean deletePoolById(@PathVariable Integer id) {
        return deletePlace(id, POOL_TYPE_ID);
    }

    @DeleteMapping("/place/gym/{id}")
    public boolean deleteGymById(@PathVariable Integer id) {
        return deletePlace(id, GYM_TYPE_ID);
    }

    @PostMapping("place/stadium")
    public void createStadium(@RequestBody StadiumRequest request) {
        Place place = new Place();
        place.setId(new PlaceId(STADIUM_TYPE_ID));
        place.setName(request.getName());
        place.setAddress(request.getAddress());
        place.setStadiumPlacesCount(request.getPlaceCount());
        place.setStadiumIsCovered(request.getIsCovered());
        place.setStadiumSquare(request.getSquare());
        repository.save(place);
    }

    @PostMapping("place/gym")
    public void createGym(@RequestBody GymRequest request) {
        Place place = new Place();
        place.setId(new PlaceId(GYM_TYPE_ID));
        place.setName(request.getName());
        place.setAddress(request.getAddress());
        place.setGymSquare(request.getSquare());
        repository.save(place);
    }

    @PostMapping("/place/pool")
    public void createPool(@RequestBody PoolRequest request) {
        Place place = new Place();
        place.setId(new PlaceId(POOL_TYPE_ID));
        place.setName(request.getName());
        place.setAddress(request.getAddress());
        place.setPoolDepth(request.getDepth());
        place.setPoolLength(request.getLength());
        place.setPoolLanesCount(request.getLanesCount());
        repository.save(place);
    }

    @PutMapping("place/stadium/{id}")
    public ResponseEntity<Place> updateStadiumById(@PathVariable Integer id,
                                                            @RequestBody StadiumRequest request) {
        PlaceId placeId = new PlaceId(id, STADIUM_TYPE_ID);
        Place placeOld = repository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("No such stadium!"));

        placeOld.setName(request.getName());
        placeOld.setAddress(request.getAddress());
        placeOld.setStadiumPlacesCount(request.getPlaceCount());
        placeOld.setStadiumIsCovered(request.getIsCovered());
        placeOld.setStadiumSquare(request.getSquare());

        Place updated = repository.save(placeOld);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("place/pool/{id}")
    public ResponseEntity<Place> updatePoolById(@PathVariable Integer id,
                                                @RequestBody PoolRequest request) {
        PlaceId placeId = new PlaceId(id, POOL_TYPE_ID);
        Place placeOld = repository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("No such pool!"));

        placeOld.setName(request.getName());
        placeOld.setAddress(request.getAddress());
        placeOld.setPoolLanesCount(request.getLanesCount());
        placeOld.setPoolLength(request.getLength());
        placeOld.setPoolDepth(request.getDepth());

        Place updated = repository.save(placeOld);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("place/gym/{id}")
    public ResponseEntity<Place> updateGymById(@PathVariable Integer id,
                                                @RequestBody GymRequest request) {
        PlaceId placeId = new PlaceId(id, POOL_TYPE_ID);
        Place placeOld = repository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("No such pool!"));

        placeOld.setName(request.getName());
        placeOld.setAddress(request.getAddress());
        placeOld.setGymSquare(request.getSquare());

        Place updated = repository.save(placeOld);
        return ResponseEntity.ok(updated);
    }

}
