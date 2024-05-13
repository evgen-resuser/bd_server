package org.evgen.bd_server.controller;

import org.evgen.bd_server.model.Competition;
import org.evgen.bd_server.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class CompetitionController {

    private final CompetitionRepository repository;

    @Autowired
    public CompetitionController(CompetitionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/competition")
    public List<Competition> readAll() {
        return repository.findAll();
    }

}
