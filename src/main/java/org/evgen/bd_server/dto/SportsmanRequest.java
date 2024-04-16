package org.evgen.bd_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SportsmanRequest {
    private String name;
    private Integer clubId;
}
