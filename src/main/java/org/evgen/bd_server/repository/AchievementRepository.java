package org.evgen.bd_server.repository;

import org.evgen.bd_server.model.SportAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<SportAchievement, Integer> {
}
