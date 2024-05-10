package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.dto.CSRequest;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Coach;
import org.evgen.bd_server.model.CoachSport;
import org.evgen.bd_server.model.Sport;
import org.evgen.bd_server.repository.CoachRepository;
import org.evgen.bd_server.repository.CoachSportRepository;
import org.evgen.bd_server.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = Consts.FRONT)
@RestController
@RequestMapping("/data")
public class CoachSportController extends ResourceNotFoundHandler{

    private final CoachSportRepository repository;
    private final CoachRepository coachRepository;
    private final SportRepository sportRepository;

    @Autowired
    public CoachSportController(CoachSportRepository repository, CoachRepository coachRepository, SportRepository sportRepository) {
        this.repository = repository;
        this.coachRepository = coachRepository;
        this.sportRepository = sportRepository;
    }

    @GetMapping("/coach_sport")
    public List<Map<String, Object>> readAllFormatted() {
        return repository.getFormattedCoachSport();
    }

    @GetMapping("/coach_sport1")
    public List<CoachSport> readAll() {
        return repository.findAll();
    }

    @GetMapping("/coach_sport/{id}")
    public ResponseEntity<CoachSport> read(@PathVariable Integer id) {
        CoachSport coachSport = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("No such pair with id:" + id));
        return ResponseEntity.ok(coachSport);
    }

    @PostMapping("/coach_sport")
    public void create(@RequestBody CSRequest cs) {
        Coach coach = coachRepository.findById(cs.getCoachId())
                .orElseThrow( () -> new ResourceNotFoundException("No such coach: " + cs.getCoachId()));
        Sport sport = sportRepository.findById(cs.getSportId())
                .orElseThrow( () -> new ResourceNotFoundException("No such sport: " + cs.getSportId()));

        CoachSport coachSport = new CoachSport();
        coachSport.setCoach(coach);
        coachSport.setSport(sport);

        repository.save(coachSport);
    }

    @PutMapping("coach_sport/{id}")
    public ResponseEntity<CoachSport> update(@RequestBody CSRequest cs, @PathVariable Integer id) {
        CoachSport csOld = repository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("No such element: " + id));
        Coach coach = coachRepository.findById(cs.getCoachId())
                .orElseThrow( () -> new ResourceNotFoundException("No such coach: " + cs.getCoachId()));
        Sport sport = sportRepository.findById(cs.getSportId())
                .orElseThrow( () -> new ResourceNotFoundException("No such sport: " + cs.getSportId()));

        csOld.setSport(sport);
        csOld.setCoach(coach);

        CoachSport updated = repository.save(csOld);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("coach_sport/{id}")
    public boolean delete(@PathVariable Integer id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
