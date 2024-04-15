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

    @PostMapping("/sport")
    public void create(@RequestBody Sport sport) {
        repository.save(sport);
    }

    @GetMapping("/sport")
    public   List<Sport> readAll() {
        return repository.findAll();
    }

    @GetMapping("/sport/{id}")
    public ResponseEntity<Sport> read(@PathVariable Integer id) {
        Sport sport = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Sport not exist with id :" + id) );
        return ResponseEntity.ok(sport);
    }

    @PutMapping("/sport/{id}")
    public ResponseEntity<Sport> update(@RequestBody Sport sport, @PathVariable Integer id) {
        Sport sportOld = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not exist with id :" + id));

        sportOld.setId(sport.getId());
        sportOld.setName(sport.getName());

        Sport updated = repository.save(sportOld);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("sport/{id}")
    public boolean delete(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
