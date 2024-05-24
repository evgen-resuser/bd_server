package org.evgen.bd_server.controller;

import org.evgen.bd_server.Consts;
import org.evgen.bd_server.model.SportSportsman;
import org.evgen.bd_server.model.place.PlaceId;
import org.evgen.bd_server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
    private final CompetitionRepository competitionRepository;

    @Autowired
    public ExtraController(AchievementRepository achievementRepository, ClubRepository clubRepository,
                           CoachRepository coachRepository, CoachSportRepository coachSportRepository,
                           PlaceRepository placeRepository, SportRepository sportRepository,
                           SportsmanRepository sportsmanRepository, SportSportsmanRepository repository,
                           CoachSportsmanRepository coachSportsmanRepository, CompetitionRepository competitionRepository) {
        this.achievementRepository = achievementRepository;
        this.clubRepository = clubRepository;
        this.coachRepository = coachRepository;
        this.coachSportRepository = coachSportRepository;
        this.placeRepository = placeRepository;
        this.sportRepository = sportRepository;
        this.sportsmanRepository = sportsmanRepository;
        this.sportSportsmanRepository = repository;
        this.coachSportsmanRepository = coachSportsmanRepository;
        this.competitionRepository = competitionRepository;
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
            @RequestParam(value = "pl_mn", defaultValue = "0") Integer placesCountMin,
            @RequestParam(value = "sq_mn", defaultValue = "0") Float squareMin) {

        return placeRepository.getStadiumsByParams(isCovered, placesCountMin, squareMin);
    }

    @GetMapping("/place/pool")
    public List<Map<PlaceId, Object>> findPoolsWithParams(
            @RequestParam(value = "lane_mn", defaultValue = "0") Integer lanesMin,
            @RequestParam(value = "dep_mn", defaultValue = "0") Integer depthMin,
            @RequestParam(value = "len_mn", defaultValue = "0") Integer lenMin
    ){
        return placeRepository.getPoolsByParams(lanesMin, depthMin, lenMin);
    }

    @GetMapping("/place/gym")
    public List<Map<PlaceId, Object>> findGymsWithParams(
            @RequestParam(value = "sq_mn", defaultValue = "0") Integer squareMin
    ){
        return placeRepository.getGymsByParams(squareMin);
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

    @GetMapping("/sportsman/sports/{id}")
    public List<Object> getAll(@PathVariable Integer id) {
        return sportSportsmanRepository.findSportsOfSportsman(id);
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

    @GetMapping("/sport/byCoach")
    public List<Object> getSportsByCoach(@RequestParam("id") Integer id) {
        if (!coachRepository.existsById(id)) return Collections.emptyList();
        return coachSportRepository.getSportsByCoach(id);
    }

    @GetMapping("/competition/byPlace")
    public List<Object> getCompetitionsByPlace(@RequestParam("id") Integer id,
                                               @RequestParam("type") Integer typeId) {
        if (!placeRepository.existsById(new PlaceId(id, typeId))) return Collections.emptyList();
        return competitionRepository.getCompetitionsFromPlace(id, typeId);
    }

    @GetMapping("/competition/byPlace1")
    public List<Object> getCompetitionsByPlace(@RequestParam("id") Integer id,
                                               @RequestParam("type") Integer typeId,
                                               @RequestParam("sport") Integer sportId) {
        if (!placeRepository.existsById(new PlaceId(id, typeId)) || !sportRepository.existsById(sportId))
            return Collections.emptyList();
        return competitionRepository.getCompetitionsFromPlace(id, typeId, sportId);
    }

    @GetMapping("/competition/bySportsman")
    public List<Object> getCompetitionsBySportsman(@RequestParam("id") Integer id){
        if (!sportsmanRepository.existsById(id)) return Collections.emptyList();
        return competitionRepository.getCompetitionsOfSportsman(id);
    }

    @GetMapping("/competition/org")
    public List<Object> getCompetitionByPeriod(@RequestParam("org") Integer org){
        return competitionRepository.getCompetitionsByPeriod(org);
    }

    @GetMapping("/competition/periodOrg")
    public List<Object> getCompetitionByPeriodAndOrganize(@RequestParam("start") Date start,
                                                          @RequestParam("end") Date end,
                                                          @RequestParam("org") Integer orgId){
        return competitionRepository.getCompetitionsByPeriodAndOrganize(start, end, orgId);
    }

    @GetMapping("/competition/notPartSportsmen")
    public List<Object> getNotParticipatingSportsmen(@RequestParam("start") Date start,
                                                     @RequestParam("end") Date end) {
        return competitionRepository.findSportsmenNotParticipatingInPeriod(start, end);
    }

    @GetMapping("/organize/periodCount")
    public List<Object> getOrganize(@RequestParam("start") Date start,
                                      @RequestParam("end") Date end) {
        return competitionRepository.getOrganizersAndNumberOfCompetitions(start, end);
    }

    @GetMapping("/place/period")
    public List<Object> placesByPeriod(@RequestParam("start") Date start,
                                    @RequestParam("end") Date end) {
        return competitionRepository.getPlacesByPeriod(start, end);
    }

    @GetMapping("/club/sportsmenCount")
    public List<Object> getClubSportsmenCountByPeriod(@RequestParam("start") Date start,
                                                      @RequestParam("end") Date end) {
        return competitionRepository.getClubSportsmenCountByPeriod(start, end);
    }

}
