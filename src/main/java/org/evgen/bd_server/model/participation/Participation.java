package org.evgen.bd_server.model.participation;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "competitions_participation")
public class Participation {

    @EmbeddedId
    private ParticipationId id;

    private Integer place;
}
