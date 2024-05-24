package org.evgen.bd_server.controller;

import org.evgen.bd_server.dto.CompetitionRequest;
import org.evgen.bd_server.dto.ParticipationRequest;
import org.evgen.bd_server.exceptions.ResourceNotFoundException;
import org.evgen.bd_server.model.Competition;
import org.evgen.bd_server.model.Organize;
import org.evgen.bd_server.model.Sport;
import org.evgen.bd_server.model.Sportsman;
import org.evgen.bd_server.model.participation.Participation;
import org.evgen.bd_server.model.participation.ParticipationId;
import org.evgen.bd_server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class CompetitionController {

    private final CompetitionRepository repository;
    private final ParticipationRepository participationRepository;
    private final SportRepository sportRepository;
    private final SportsmanRepository sportsmanRepository;
    private final OrganizeRepository organizeRepository;

    @Autowired
    public CompetitionController(CompetitionRepository repository, ParticipationRepository participationRepository,
                                 SportRepository sportRepository, SportsmanRepository sportsmanRepository,
                                 OrganizeRepository organizeRepository) {
        this.repository = repository;
        this.participationRepository = participationRepository;
        this.sportRepository = sportRepository;
        this.sportsmanRepository = sportsmanRepository;
        this.organizeRepository = organizeRepository;
    }

    @GetMapping("/competition")
    public List<Object> readAll() {
        return repository.getAllWithPlace();
    }

    @GetMapping("/winners/{id}")
    public List<Object> getWinners(@PathVariable Integer id){
        return participationRepository.getWinners(id);
    }

    @PostMapping("competition")
    public void create(@RequestBody CompetitionRequest request) {

        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new ResourceNotFoundException("No such sport"));
        Organize organize = organizeRepository.findById(request.getOrgId())
                .orElseThrow(() -> new ResourceNotFoundException("No such organize"));

        Sportsman fst = null;
        Sportsman snd = null;
        Sportsman thd = null;
        if (request.getFirstId() != null) {
            fst = sportsmanRepository.findById(request.getFirstId())
                    .orElseThrow(() -> new ResourceNotFoundException("no such sportsman"));
        }
        if (request.getSecondId() != null) {
            snd = sportsmanRepository.findById(request.getSecondId())
                    .orElseThrow(() -> new ResourceNotFoundException("no such sportsman"));
        }
        if (request.getThirdId() != null) {
            thd = sportsmanRepository.findById(request.getThirdId())
                    .orElseThrow(() -> new ResourceNotFoundException("no such sportsman"));
        }

        Competition competition = new Competition();
        competition.setDate(request.getDate());
        competition.setOrganize(organize);
        competition.setSport(sport);
        competition.setPlaceId(request.getPlaceId());
        competition.setPlaceTypeId(request.getPlaceTypeId());
        int id = repository.save(competition).getId();

        addParticipation(id, fst, 1);
        addParticipation(id, snd, 2);
        addParticipation(id, thd, 3);

    }

    private void addParticipation(int id, Sportsman sportsman, Integer place) {
        if (sportsman == null) return;
        ParticipationId participationId = new ParticipationId();
        participationId.setCompId(id);
        participationId.setSportsmanId(sportsman.getId());
        Participation participation = new Participation();
        participation.setId(participationId);
        participation.setPlace(place);
        participationRepository.save(participation);
    }

    @PostMapping("/participation")
    public void addParticipation(@RequestBody ParticipationRequest request) {
        Sportsman s = sportsmanRepository.findById(request.getSportsmanId())
                .orElseThrow(()-> new ResourceNotFoundException("No such sportsman"));
        if (!repository.existsById(request.getCompetitionId())) {
            throw new ResourceNotFoundException("No such competition");
        }
        System.out.println(request.getCompetitionId() + " " + request.getSportsmanId());
        addParticipation(request.getCompetitionId(), s, 0);
    }

}
