package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.dto.SportsmanRequest;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Club;
import org.evgen.bd_server.model.Sportsman;
import org.evgen.bd_server.repository.ClubRepository;
import org.evgen.bd_server.repository.SportsmanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = Consts.FRONT)
@RestController
@RequestMapping("/data")
public class SportsmanController extends ResourceNotFoundHandler {

    private final SportsmanRepository repository;
    private final ClubRepository clubRepository;

    public SportsmanController(SportsmanRepository repository, ClubRepository clubRepository) {
        this.repository = repository;
        this.clubRepository = clubRepository;
    }

    @GetMapping("/sportsman")
    public List<Map<String, Object>> readAll() {
        return repository.findAthleteAndClubNames();
    }

    @GetMapping("/sportsman/{id}")
    public Map<String, Object> read(@PathVariable int id){
        return repository.findSportsman(id);
    }

    @PostMapping("/sportsman")
    public ResponseEntity<Sportsman> create(@RequestBody SportsmanRequest sportsman) {
        Club club;
        if (sportsman.getClubId() == null) {
            club = null;
        } else {
            club = clubRepository.findById(sportsman.getClubId())
                    .orElseThrow( () -> new ResourceNotFoundException("No such club"));
        }

        Sportsman s = new Sportsman();
        s.setName(sportsman.getName());
        s.setClub(club);
        repository.save(s);
        return ResponseEntity.ok(s);
    }

    @PutMapping("/sportsman/{id}")
    public ResponseEntity<SportsmanRequest> update(@PathVariable int id, @RequestBody SportsmanRequest sportsman) {

        Sportsman old = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("no such sportsman: "+id));
        Club club = clubRepository.findById(sportsman.getClubId())
                .orElse(null);

        old.setClub(club);
        old.setName(sportsman.getName());
        repository.save(old);

        return ResponseEntity.ok(sportsman);
    }

    @DeleteMapping("/sportsman/{id}")
    public boolean delete(@PathVariable int id){
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

}
