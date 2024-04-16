package org.evgen.bd_server.controller;

import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.CoachSport;
import org.evgen.bd_server.repository.CoachSportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/data")
public class CoachSportController extends ResourceNotFoundHandler{

    private final CoachSportRepository repository;

    @Autowired
    public CoachSportController(CoachSportRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/coach_sport")
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
    public void create(@RequestBody CoachSport cs) {
        repository.save(cs);
    }

    @PutMapping("coach_sport/{id}")
    public ResponseEntity<CoachSport> update(@RequestBody CoachSport cs, @PathVariable Integer id) {
        CoachSport csOld = repository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("No such element: " + id));

        csOld.setId(cs.getId());
        csOld.setSport(cs.getSport());
        csOld.setCoach(cs.getCoach());

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
