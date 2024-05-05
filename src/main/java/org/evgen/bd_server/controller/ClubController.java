package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Club;
import org.evgen.bd_server.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Consts.FRONT)
@RestController
@RequestMapping("/data")
public class ClubController extends ResourceNotFoundHandler{

    private final ClubRepository repository;

    @Autowired
    public ClubController(ClubRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/club")
    public List<Club> readAll() {
        return repository.findAll();
    }

    @GetMapping("/club/{id}")
    public ResponseEntity<Club> readById(@PathVariable Integer id) {
        Club club = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("No such club: " + id));
        return ResponseEntity.ok(club);
    }

    @PostMapping("/club")
    public void create(@RequestBody Club club) {
        repository.save(club);
    }

    @PutMapping("/club/{id}")
    public ResponseEntity<Club> update(@RequestBody Club club, @PathVariable Integer id) {
        Club old = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("no such club: " + id));

        old.setId(club.getId());
        old.setName(club.getName());
        repository.save(old);
        return ResponseEntity.ok(club);
    }

    @DeleteMapping("/club/{id}")
    public boolean delete(@PathVariable Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

}
