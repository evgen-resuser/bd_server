package org.evgen.bd_server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.evgen.bd_server.model.place.PlaceId;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer placeId;
    private Integer placeTypeId;

    @ManyToOne
    @JoinColumn(name = "sport_id", referencedColumnName = "id")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "organize", referencedColumnName = "id")
    private Organize organize;

    private Date date;
}
