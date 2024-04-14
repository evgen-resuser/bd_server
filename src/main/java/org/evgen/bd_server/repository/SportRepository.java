package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Integer> {
}
