package org.evgen.bd_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationRequest {
    private Integer sportsmanId;
    private Integer competitionId;
}
