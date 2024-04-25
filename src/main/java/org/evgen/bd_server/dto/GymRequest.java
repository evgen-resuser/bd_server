package org.evgen.bd_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymRequest {
    private String name;
    private String address;
    private Float square;
}
