package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {
}
