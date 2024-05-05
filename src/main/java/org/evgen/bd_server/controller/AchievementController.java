package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.dto.AchievementRequest;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Coach;
import org.evgen.bd_server.model.SportAchievement;
import org.evgen.bd_server.model.Sportsman;
import org.evgen.bd_server.repository.AchievementRepository;
import org.evgen.bd_server.repository.CoachRepository;
import org.evgen.bd_server.repository.SportsmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = Consts.FRONT)
@RequestMapping("/data")
public class AchievementController extends ResourceNotFoundHandler {

    private final AchievementRepository repository;
    private final SportsmanRepository sportsmanRepository;
    private final CoachRepository coachRepository;

    @Autowired
    public AchievementController(AchievementRepository repository, SportsmanRepository sportsmanRepository, CoachRepository coachRepository) {
        this.repository = repository;
        this.sportsmanRepository = sportsmanRepository;
        this.coachRepository = coachRepository;
    }

    @GetMapping("/achievements")
    public List<SportAchievement> readAll() {
        return repository.findAll();
    }

    @GetMapping("/achievements/{id}")
    public ResponseEntity<SportAchievement> read(@PathVariable int id) {
        SportAchievement achievement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such entry: "+id));
        return ResponseEntity.ok(achievement);
    }

    @DeleteMapping("/achievements/{id}")
    public boolean delete(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @PostMapping("/achievements")
    public void create(@RequestBody AchievementRequest achievement) {
        Sportsman sportsman = sportsmanRepository.findById(achievement.getSportsmanId())
                .orElseThrow(() -> new ResourceNotFoundException("No such sportsman: " + achievement.getSportsmanId()));
        Coach coach = coachRepository.findById(achievement.getCoachId())
                .orElseThrow( () -> new ResourceNotFoundException("no such coach: " + achievement.getCoachId()));

        SportAchievement a = new SportAchievement();
        a.setCoach(coach);
        a.setName(achievement.getName());
        a.setSportsman(sportsman);
        a.setDischarge(achievement.getDischarge());

        repository.save(a);
    }

    @PutMapping("/achievements/{id}")
    public ResponseEntity<AchievementRequest> update(@PathVariable int id, @RequestBody AchievementRequest achievement) {
        SportAchievement achievement1 = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such achievement: "+id));

        Sportsman sportsman = sportsmanRepository.findById(achievement.getSportsmanId())
                .orElseThrow(() -> new ResourceNotFoundException("No such sportsman: "+achievement.getSportsmanId()));
        Coach coach = coachRepository.findById(achievement.getCoachId())
                .orElseThrow( () -> new ResourceNotFoundException("No such coach: "+achievement.getCoachId()));

        achievement1.setDischarge(achievement.getDischarge());
        achievement1.setCoach(coach);
        achievement1.setSportsman(sportsman);
        achievement1.setName(achievement.getName());

        repository.save(achievement1);
        return ResponseEntity.ok(achievement);
    }

}
