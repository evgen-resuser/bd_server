package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.model.SportSportsman;
import org.evgen.bd_server.model.place.PlaceId;
import org.evgen.bd_server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = Consts.FRONT)
@RestController
@RequestMapping("/find")
public class ExtraController {

    private final AchievementRepository achievementRepository;
    private final ClubRepository clubRepository;
    private final CoachRepository coachRepository;
    private final CoachSportRepository coachSportRepository;
    private final PlaceRepository placeRepository;
    private final SportRepository sportRepository;
    private final SportsmanRepository sportsmanRepository;
    private final SportSportsmanRepository sportSportsmanRepository;
    private final CoachSportsmanRepository coachSportsmanRepository;

    @Autowired
    public ExtraController(AchievementRepository achievementRepository, ClubRepository clubRepository,
                           CoachRepository coachRepository, CoachSportRepository coachSportRepository,
                           PlaceRepository placeRepository, SportRepository sportRepository,
                           SportsmanRepository sportsmanRepository, SportSportsmanRepository repository,
                           CoachSportsmanRepository coachSportsmanRepository) {
        this.achievementRepository = achievementRepository;
        this.clubRepository = clubRepository;
        this.coachRepository = coachRepository;
        this.coachSportRepository = coachSportRepository;
        this.placeRepository = placeRepository;
        this.sportRepository = sportRepository;
        this.sportsmanRepository = sportsmanRepository;
        this.sportSportsmanRepository = repository;
        this.coachSportsmanRepository = coachSportsmanRepository;
    }

    // #1

    @GetMapping("/place/stadium/")
    public List<Map<PlaceId, Object>> readAllStadiums() {
        return placeRepository.getStadiums();
    }

    @GetMapping("/place/pool/")
    public List<Map<PlaceId, Object>> readAllPools() {
        return placeRepository.getPools();
    }

    @GetMapping("/place/gym/")
    public List<Map<PlaceId, Object>> readAllGyms() {
        return placeRepository.getGyms();
    }

    @GetMapping("/place/stadium")
    public List<Map<PlaceId, Object>> findStadiumWithParams(
            @RequestParam(value = "covered", defaultValue = "false") Boolean isCovered,
            @RequestParam(value = "pl_mx", defaultValue = "1000000") Integer placesCountMax,
            @RequestParam(value = "pl_mn", defaultValue = "0") Integer placesCountMin,
            @RequestParam(value = "sq_mx", defaultValue = "1000000") Float squareMax,
            @RequestParam(value = "sq_mn", defaultValue = "0") Float squareMin) {

        return placeRepository.getStadiumsByParams(isCovered, placesCountMax, placesCountMin, squareMax, squareMin);
    }

    @GetMapping("/place/pool")
    public List<Map<PlaceId, Object>> findPoolsWithParams(
            @RequestParam(value = "lane_mx", defaultValue = "100000") Integer lanesMax,
            @RequestParam(value = "lane_mn", defaultValue = "0") Integer lanesMin,
            @RequestParam(value = "dep_mx", defaultValue = "100000") Integer depthMax,
            @RequestParam(value = "dep_mn", defaultValue = "0") Integer depthMin,
            @RequestParam(value = "len_mx", defaultValue = "100000") Integer lenMax,
            @RequestParam(value = "len_mn", defaultValue = "0") Integer lenMin
    ){
        return placeRepository.getPoolsByParams(lanesMax, lanesMin, depthMax, depthMin, lenMax, lenMin);
    }

    @GetMapping("/sportsman/bySport")
    public List<Object> findSportsmanBySport(
            @RequestParam("sport") Integer sportId,
            @RequestParam(value = "discharge", defaultValue = "") String charge
    ) {
        if (sportId == null) return Collections.emptyList();
        if (charge.isEmpty()) {
            System.out.println(sportId.getClass());
            return sportSportsmanRepository.findBySport(sportId);
        }
        return sportSportsmanRepository.findBySportAndDischarge(sportId, charge);
    }

    @GetMapping("/sportsman/twoAndMore")
    public List<Object> findTwoAndMoreSports() {
        return sportSportsmanRepository.findMoreThanOneSport();
    }

    @GetMapping("/sportsman/sports")
    public List<SportSportsman> getAll() {
        return sportSportsmanRepository.findAll();
    }

    @GetMapping("/sportsman/coaches")
    public List<Map<String, Object>> findAllCoachesById(@RequestParam("id") Integer id){
        return coachSportsmanRepository.getCoachesById(id);
    }

    @GetMapping("/coach/bySport")
    public List<Object> getCoachesBySport(@RequestParam("id") Integer id){
        if (!sportRepository.existsById(id)) return Collections.emptyList();
        return coachSportRepository.getCoachesBySport(id);
    }

    @GetMapping("/coach/sportsmen")
    public List<Object> findAllSportsmenById(@RequestParam("id") Integer id,
                                                @RequestParam(value = "disch", defaultValue = "") String discharge){
        if (!discharge.isEmpty())
            return coachSportsmanRepository.getSportsmenByIdDischarge(id, discharge);
        return coachSportsmanRepository.getSportsmenById(id);
    }

}
