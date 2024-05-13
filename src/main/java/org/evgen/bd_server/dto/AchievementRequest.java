package org.evgen.bd_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchievementRequest {
    private int sportsmanId;
    private int coachId;
    private String name;
    private int discharge;

}
