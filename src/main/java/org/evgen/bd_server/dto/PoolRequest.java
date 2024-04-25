package org.evgen.bd_server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PoolRequest {
    private String name;
    private String address;
    private Integer length;
    private Integer depth;
    private Integer lanesCount;
}
