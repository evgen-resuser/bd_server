package org.evgen.bd_server.model.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "place")
public class Place {
    @EmbeddedId
    private PlaceId id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private Boolean stadiumIsCovered;

    @Column
    private Integer stadiumPlacesCount;

    @Column
    private Float stadiumSquare;

    @Column
    private Integer poolLength;

    @Column
    private Integer poolDepth;

    @Column
    private Integer poolLanesCount;

    @Column
    private Float gymSquare;
}
