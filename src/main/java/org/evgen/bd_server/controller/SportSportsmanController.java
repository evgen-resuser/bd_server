package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.dto.SSRequest;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Sport;
import org.evgen.bd_server.model.SportSportsman;
import org.evgen.bd_server.model.Sportsman;
import org.evgen.bd_server.repository.SportRepository;
import org.evgen.bd_server.repository.SportSportsmanRepository;
import org.evgen.bd_server.repository.SportsmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Consts.FRONT)
@RestController
@RequestMapping("/data")
public class SportSportsmanController {

    private SportSportsmanRepository repository;
    private SportsmanRepository sportsmanRepository;
    private SportRepository sportRepository;

    @Autowired
    public SportSportsmanController(SportSportsmanRepository repository, SportsmanRepository sportsmanRepository, SportRepository sportRepository) {
        this.repository = repository;
        this.sportsmanRepository = sportsmanRepository;
        this.sportRepository = sportRepository;
    }

    @PostMapping("/sportSportsman")
    public ResponseEntity<SportSportsman> create(@RequestBody SSRequest request) {
        Sport s = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new ResourceNotFoundException("No such sport"));
        Sportsman sportsman = sportsmanRepository.findById(request.getSportsmanId())
                .orElseThrow(() -> new ResourceNotFoundException("No such sportsman"));
        SportSportsman ss = new SportSportsman();
        ss.setSportsmanId(sportsman);
        ss.setSportId(s);
        repository.save(ss);
        return ResponseEntity.ok(ss);
    }

}
