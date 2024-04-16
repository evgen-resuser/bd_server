package org.evgen.bd_server.controller;

import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Coach;
import org.evgen.bd_server.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/data")
public class CoachController extends ResourceNotFoundHandler {

    private final CoachRepository repository;

    @Autowired
    public CoachController(CoachRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/coach")
    public void create(@RequestBody Coach coach) {
        repository.save(coach);
    }

    @GetMapping("/coach")
    public List<Coach> readAll() {
        return repository.findAll();
    }

    @GetMapping("/coach/{id}")
    public ResponseEntity<Coach> read(@PathVariable Integer id) {
        Coach coach = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("No such coach: " + id));
        return ResponseEntity.ok(coach);
    }

    @PutMapping("/coach/{id}")
    public ResponseEntity<Coach> update(@RequestBody Coach coach, @PathVariable Integer id) {
        Coach coachOld = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not exist with id :" + id));

        coachOld.setId(coach.getId());
        coachOld.setName(coach.getName());

        Coach updated = repository.save(coachOld);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("coach/{id}")
    public boolean delete(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
