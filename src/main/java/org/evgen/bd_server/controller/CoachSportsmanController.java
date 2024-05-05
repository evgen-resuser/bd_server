package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.dto.CSmRequest;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Coach;
import org.evgen.bd_server.model.CoachSportsman;
import org.evgen.bd_server.model.Sportsman;
import org.evgen.bd_server.repository.CoachRepository;
import org.evgen.bd_server.repository.CoachSportsmanRepository;
import org.evgen.bd_server.repository.SportsmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Consts.FRONT)
@RestController
@RequestMapping("/data")
public class CoachSportsmanController {

    private final CoachSportsmanRepository repository;
    private final CoachRepository coachRepository;
    private final SportsmanRepository sportsmanRepository;

    @Autowired
    public CoachSportsmanController(CoachSportsmanRepository repository,
                                    CoachRepository coachRepository,
                                    SportsmanRepository sportsmanRepository) {
        this.repository = repository;
        this.coachRepository = coachRepository;
        this.sportsmanRepository = sportsmanRepository;
    }

    @GetMapping("/coach_sportsman")
    public List<CoachSportsman> readAll() {
        return repository.findAll();
    }

    @GetMapping("/coach_sportsman/{id}")
    public ResponseEntity<CoachSportsman> getById(@PathVariable Integer id) {
        CoachSportsman cs = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such entry"));
        return ResponseEntity.ok(cs);
    }

    @DeleteMapping("/coach_sportsman/{id}")
    public boolean deleteById(@PathVariable Integer id){
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @PostMapping("/coach_sportsman")
    public void create(@RequestBody CSmRequest request) {
        Coach coach = coachRepository.findById(request.getCoachId())
                .orElseThrow(() -> new ResourceNotFoundException("No such coach"));
        Sportsman sportsman = sportsmanRepository.findById(request.getSportsmanId())
                .orElseThrow(() -> new ResourceNotFoundException("No such sportsman"));

        CoachSportsman cs = new CoachSportsman();
        cs.setCoach(coach);
        cs.setSport(sportsman);
        repository.save(cs);
    }

    @PutMapping("/coach_sportsman/{id}")
    public ResponseEntity<CoachSportsman> updateById(@PathVariable Integer id,
                                                     @RequestBody CSmRequest request) {
        CoachSportsman csOld = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such entry!"));
        Coach coach = coachRepository.findById(request.getCoachId())
                .orElseThrow(() -> new ResourceNotFoundException("No such coach"));
        Sportsman sportsman = sportsmanRepository.findById(request.getSportsmanId())
                .orElseThrow(() -> new ResourceNotFoundException("No such sportsman"));

        csOld.setSport(sportsman);
        csOld.setCoach(coach);

        CoachSportsman updated = repository.save(csOld);
        return ResponseEntity.ok(updated);
    }

}
