package org.evgen.bd_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StadiumRequest {
    private String name;
    private String address;
    private Float square;
    private Integer placeCount;
    private Boolean isCovered;
}
