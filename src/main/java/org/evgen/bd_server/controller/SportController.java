package org.evgen.bd_server.controller;

import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Sport;
import org.evgen.bd_server.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/data")
public class SportController {

    @Autowired
    private SportRepository repository;

    public void create(Sport sport) {
        repository.save(sport);
    }

    @GetMapping("/sport")
    public   List<Sport> readAll() {
        return repository.findAll();
    }

    @GetMapping("/sport/{id}")
    public ResponseEntity<Sport> read(@PathVariable Integer id) {
        Sport sport = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Employee not exist with id :" + id) );
        return ResponseEntity.ok(sport);
    }

    public boolean update(Sport sport, int id) {
        if (repository.existsById(id)) {
            sport.setId(id);
            repository.save(sport);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
