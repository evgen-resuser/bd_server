package org.evgen.bd_server.service;

import org.evgen.bd_server.model.Sport;
import org.evgen.bd_server.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportService{

    @Autowired
    private SportRepository repository;

    public void create(Sport sport) {
        repository.save(sport);
    }

    public List<Sport> readAll() {
        return repository.findAll();
    }

    public Sport read(int id) {
        return repository.getOne(id);
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
