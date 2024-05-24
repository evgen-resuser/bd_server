package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.model.Organize;
import org.evgen.bd_server.repository.OrganizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Consts.FRONT)
@RestController
@RequestMapping("/data")
public class OrganizeController {

    private final OrganizeRepository repository;

    @Autowired
    public OrganizeController(OrganizeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/organize")
    public List<Organize> getAll() {
        return repository.findAll();
    }

    @PostMapping("/organize")
    public void add(@RequestBody Organize organize) {
        repository.save(organize);
    }

}
