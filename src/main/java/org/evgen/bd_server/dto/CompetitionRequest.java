package org.evgen.bd_server.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CompetitionRequest {
    private Date date;
    private Integer sportId;
    private Integer orgId;
    private Integer placeId;
    private Integer placeTypeId;
    private Integer firstId;
    private Integer secondId;
    private Integer thirdId;
}
