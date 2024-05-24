package org.evgen.bd_server.model.participation;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ParticipationId implements Serializable {
    private Integer compId;
    private Integer sportsmanId;

    @Override
    public int hashCode() {
        return Integer.hashCode(compId+sportsmanId);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
