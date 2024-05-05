package org.evgen.bd_server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sport_sportsman")
public class SportSportsman {

    @Column(name = "sportsman_id")
    private Integer sportsmanId;

    @Column(name = "sport_id")
    private Integer sportId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
