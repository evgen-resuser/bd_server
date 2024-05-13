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
    @JoinColumn(name = "fst_id", referencedColumnName = "id")
    private Sportsman first;

    @ManyToOne
    @JoinColumn(name = "snd_id", referencedColumnName = "id")
    private Sportsman second;

    @ManyToOne
    @JoinColumn(name = "thd_id", referencedColumnName = "id")
    private Sportsman third;

    private String organize;

    private Date date;

    public PlaceId getPlaceIdInstance() {
        return new PlaceId(placeTypeId, placeId);
    }

}
